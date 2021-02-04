package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.views.LoginView;
import com.udacity.jwdnd.course1.cloudstorage.views.NoteView;
import com.udacity.jwdnd.course1.cloudstorage.views.SignupView;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationTests {

	@LocalServerPort
	private int port; // RANDOM_PORT, port of server

	// fields:
	private static WebDriver driver;

	private SignupView signupView;
	private LoginView loginView;
	private NoteView noteView;

	String baseURL;

	// before all test, initialize Driver as Chrome browser:
	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	// after all tests, quit driver:
	@AfterAll
	public static void afterAll() {
		driver.quit();
	}

	// before each test, navigate to url to simulate test:
	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
	}


	/** TEST 1.1:
	 * */
	@Test
	public void testUnauthorizedUser() {
		/*// navigate to /signup:
		driver.get(baseURL + "/signup");
		// initialize object for SignupPage:
		signupView = new SignupView(driver);
		signupView.signup("Ivan", "Kabardin", "kebard1985", "12345");*/

		/*// check the title of the current page is Signup
		assertEquals("Sign Up", driver.getTitle());*/

		// click "Back to login" to go back to /login:
		driver.get(baseURL + "/login");

		// currently in Login page
		// simulate unauthorized user to click Login but fail:
		loginView = new LoginView(driver);
		loginView.login("kebard1985", "password");

		// check if the title of the current page is Login:
		assertEquals("Login", driver.getTitle());

		// check if invalid user message displayed:
		// expect to see "Invalid user" error message for unauthorized user login:
		assertTrue(loginView.isInvalid());
	}

	/** TEST 1.2:
	 * */
	@Test
	public void testLoginLogout() {

		// navigate to /signup:
		driver.get(baseURL + "/signup");

		// initialize object of SignupPage
		// call .signup() to simulate user's signup:
		signupView = new SignupView(driver);
		signupView.signup("Ivan", "Kabardin", "kebard1985", "12345");

		// check if the current page title is Signup:
		assertEquals("Sign Up", driver.getTitle());

		// after signup, navigate to /login:
		driver.get(baseURL + "/login");

		// initialize object of LoginPage
		loginView = new LoginView(driver);
		// call .login() to simulate user's login:
		loginView.login("kebard1985", "12345");

		// check if this page title is Home after successfully logging in:
		assertEquals("Home", driver.getTitle());

		// after successfully login, auto navigate to /home:
		// initialize object for HomePage
		noteView = new NoteView(driver);

		// simulate user to click logout to be logged out:
		noteView.logout();

		// check if the "You have been logged out" message displayed after logout:
		assertTrue(loginView.isLoggedOut());

		// after logout, navigate back to "/home":
		driver.get(baseURL + "/home");
		// verify that homepage is no longer accessible:
		assertEquals("Login", driver.getTitle());
	}

}
