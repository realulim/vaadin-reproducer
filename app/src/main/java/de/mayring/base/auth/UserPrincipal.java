package de.mayring.base.auth;

import lombok.Getter;

import de.mayring.base.da.BaseUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

@Getter
public class UserPrincipal extends User {

	private final BaseUser user;

	public UserPrincipal(BaseUser user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUserName(), user.getPassword(), authorities);
		this.user = user;
	}

	public UserPrincipal(BaseUser user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
						 Collection<? extends GrantedAuthority> authorities) {
		super(user.getUserName(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 37 * hash + Objects.hashCode(this.user);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UserPrincipal other = (UserPrincipal) obj;
		return Objects.equals(this.user, other.user);
	}

}
