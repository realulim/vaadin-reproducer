package de.mayring.base.ui.view;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Tag("login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
@CssImport(value="./styles/custom-login-styles.css", themeFor = "vaadin-login-overlay-wrapper")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	public static final String ROUTE = "login";

	private final LoginOverlay loginBox;

	public LoginView() {
		this.loginBox = new LoginOverlay();
		Image baseLogo = new Image("frontend/img/base_logo_white.png", "Base Logo");
		baseLogo.getElement().getClassList().add("base-logo");
		baseLogo.setHeight("48px");
		baseLogo.setWidth("48px");
		loginBox.setTitle(baseLogo);
		loginBox.setDescription("Welcome to Base");
		loginBox.setAction("login");
		loginBox.setOpened(true);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// inform the user about an authentication error
		if(!event.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList()).isEmpty()) {
			loginBox.setError(true);
		}
	}

}
