package tests;

import com.aventstack.extentreports.Status;
import common.Macros;

import org.junit.Test;

public class LoginLogoutTest extends Macros {

	@Test
	public void loginLogout() {
        int i = 0;
		try {
            while (true) {
                startReport("Login/Logout #" + ++i, "checks Login and Logout of Application");
                loginAsDefaultUser();
                report(Status.INFO, "Login successful");
                logout();
                report(Status.PASS, "Login and Logout works as expected");
                flushReport();
            }
		}
		catch(Throwable t) {
			failOnException(t);
		}
	}

}
