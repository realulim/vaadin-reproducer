package de.mayring.reproducer.authorization;

import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.NotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.ilay.Access;
import org.ilay.AccessEvaluator;
import de.mayring.base.auth.SecurityUtils;
import de.mayring.base.ui.view.LoginView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class AccessControlEvaluator implements AccessEvaluator<Protected> {

    @Override
    public Access evaluate(Location location, Class navigationTarget, Protected annotation) {
        if (isAccessToViewGranted(navigationTarget, annotation)) {
            return Access.granted();
        }
        else if (SecurityUtils.isUserLoggedIn()) {
            return Access.restricted(NotFoundException.class);
        }
        else {
            return Access.restricted(LoginView.ROUTE);
        }
    }

	/**
	 * Checks if access is granted for the current user to the given secured view defined by the view class.
	 *
	 * @param securedClass view class
	 * @param annotation annotation on view class
	 * @return true if access is granted, false otherwise.
	 */
	private boolean isAccessToViewGranted(Class<?> securedClass, Protected annotation) {
        if (securedClass == LoginView.class) return true;
        final String requiredRole = annotation.value().name();
		final Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
		return userAuthentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.anyMatch(requiredRole::equals);
	}

}
