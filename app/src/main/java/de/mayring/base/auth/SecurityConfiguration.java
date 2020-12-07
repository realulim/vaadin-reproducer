package de.mayring.base.auth;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import de.mayring.base.da.BaseUser;
import de.mayring.reproducer.authorization.AccessControl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Configures spring security, doing the following:
 * <li>Bypass security checks for static resources,</li>
 * <li>Restrict access to the application, allowing only logged in users,</li>
 * <li>Set up the login form</li>
 */
@EnableWebSecurity
@Configuration
@Order(1)
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_PAGE = "/login";
	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";

    /**
	 * Require login to access internal pages and configure login form.
	 * 
	 * @param http
	 * @throws java.lang.Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Vaadin has built-in Cross-Site Request Forgery already.
		// Not using Spring CSRF here to be able to use plain HTML for the login page.
		http.csrf().disable()
			// Register our CustomRequestCache, that saves unauthorized access attempts, so
			// the user is redirected after login.
			.requestCache().requestCache(new CustomRequestCache())

			// Restrict access to our application.
			.and().authorizeRequests()

			// Allow all flow internal requests.
			.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

			// Allow all requests by logged in users.
			.anyRequest().authenticated()

			// Configure the login page.
			.and().formLogin()
				.loginPage(LOGIN_PAGE).permitAll()
				.loginProcessingUrl(LOGIN_PROCESSING_URL)
				.failureUrl(LOGIN_FAILURE_URL)

			// Configure logout.
			.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
	}

	/**
	 * Allows access to static resources, bypassing Spring security.
	 * 
	 * @param web
	 * @throws java.lang.Exception
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
			// Vaadin Flow static resources
			"/VAADIN/**",
			// the standard favicon URI
			"/favicon.ico",
			// the robots exclusion standard
			"/robots.txt",
			// web application manifest
			"/manifest.webmanifest",
			"/sw.js",
			"/offline-page.html",
			// icons and images
			"/icons/**",
			"/images/**",
			// (development mode) static resources
			"/frontend/**",
			// (development mode) webjars
			"/webjars/**",
			// (development mode) H2 debugging console
			"/h2-console/**",
			// (production mode) static resources
			"/frontend-es5/**", "/frontend-es6/**");
	}

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(hardcodedUsersAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider hardcodedUsersAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(inMemoryUserDetailsManager());
        authProvider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        return authProvider;
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager() {
            @Override
            public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
                if (StringUtils.isBlank(userName)) throw new UsernameNotFoundException("Emtpy Username!");
                else switch (userName) {
                    case "test":
                        BaseUser reproducerUser = new BaseUser("test", "{noop}test", "Thomas Testuser", "Sales", "frontend/img/user.png");
                        reproducerUser.addAccess(AccessControl.READ, AccessControl.EDIT);
                        return new UserPrincipal(reproducerUser, true, true, true, true, reproducerUser.getAuthorities());
                    default:
                        throw new UsernameNotFoundException("Not found: " + userName);
                }
            }
        };
    }

}
