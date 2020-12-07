package common;

import com.aventstack.extentreports.Status;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public abstract class Macros extends UiTestBase {

	@Override
	public void setUp() {
		super.setUp();
	}

	protected void loginAsDefaultUser() {
		goToHomepage();
		if ($(".logout").exists()) {
			logout();
		}
		$("input[name='username']").should(appear).sendKeys("test");
		$("input[name='password']").should(appear).sendKeys("test");
		$("vaadin-button[part='vaadin-login-submit']").click();
		$(".reproducer-logo").should(appear);
		$("[slot='application-content']").should(exist);
	}

	protected void logout() {
		$(".logout").shouldBe(visible).click();
		$(".reproducer-logo").should(disappear);
		$("input[name='username']").should(appear);
		$("input[name='password']").should(appear);
		$("vaadin-button[part='vaadin-login-submit']").should(appear);
	}

	protected void goToHomepage() {
		String STARTURL = System.getProperty(BASEURL) + "/";
		open(STARTURL);
		$(".base-logo").should(appear);
		report(Status.INFO, "Homepage loaded: " + STARTURL, false);
	}

}
