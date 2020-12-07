package de.mayring.base.da;

import lombok.Getter;
import lombok.ToString;

import de.mayring.reproducer.authorization.AccessControl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString(exclude = "password")
public class BaseUser {

	private final String userName, password;
	private String fullName, department, imagePath;
	private final Set<AccessControl> access = new HashSet<>();

	public BaseUser(String userName, String password, String fullName, String department, String imagePath) {
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;
		this.department = department;
		this.imagePath = imagePath;
	}

	public void addAccess(AccessControl... toAdd) {
		this.access.addAll(Arrays.asList(toAdd));
	}

    public Collection<GrantedAuthority> getAuthorities() {
        return access.stream()
                .map(accessControl -> accessControl.name())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

}
