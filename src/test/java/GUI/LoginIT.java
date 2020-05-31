package GUI;

import com.vaadin.flow.component.applayout.testbench.AppLayoutElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;

import com.vaadin.flow.component.html.testbench.H2Element;

import com.vaadin.flow.component.html.testbench.SpanElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class LoginIT extends TestBenchTestCase {
    //private ChromeDriver driver;
    @Before
    public void setup(){
        WebDriver driver = null;
        //WebDriverManager.chromedriver().version("83.0.4103.39").setup();
        //System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\ChromeDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");
        options.addArguments("--ignore-certificate-errors");

        //driver = new ChromeDriver(options);
        setDriver(driver = new ChromeDriver(options));
        driver.get("https://localhost:8443");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try{
            if($(H2Element.class).id("bootH2").isDisplayed()) {
                BootUpSystem();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void BootUpSystem() throws InterruptedException {
        ButtonElement bootTheSystemBtn = $(ButtonElement.class).first();
        //Assert.assertEquals("Boot the System", bootTheSystemBtn.getText());

        bootTheSystemBtn.click();
        //Assert.assertEquals("Please enter a system administrator username:", $(LabelElement.class).first().getText());
        Thread.sleep(500);
        $(TextFieldElement.class).id("1").setValue("Administrator");
        $(PasswordFieldElement.class).id("2").setValue("Aa123456");
        $(ButtonElement.class).id("submit").click();

        $(ButtonElement.class).id("yes").click();

        //Assert.assertEquals("Welcome to the Football System. if you wish to go to the about page press the button below", $(SpanElement.class).id("welcomeSpan").getText());
    }

    @Test
    public void loginSuccessTest(){
        $(ButtonElement.class).id("login").click();
        Assert.assertEquals("Log in as \"admin\" to have full access. Log in with any other username to have read-only access. For all users, the password is same as the username.", $(SpanElement.class).id("loginInfoText").getText());
        $(TextFieldElement.class).id("vaadinLoginUsername").setValue("Administrator");
        $(PasswordFieldElement.class).id("vaadinLoginPassword").setValue("Aa123456");
        $(ButtonElement.class).first().click();

        Assert.assertTrue($(AppLayoutElement.class).id("footbalMain").isDisplayed());
        //Assert.assertEquals("Log out", $(SpanElement.class).id("about").getText());
        logout();
    }

    @Test
    public void loginFailureTest(){
        $(ButtonElement.class).id("login").click();
        Assert.assertEquals("Log in as \"admin\" to have full access. Log in with any other username to have read-only access. For all users, the password is same as the username.", $(SpanElement.class).id("loginInfoText").getText());
        $(TextFieldElement.class).id("vaadinLoginUsername").setValue("oran");
        $(PasswordFieldElement.class).id("vaadinLoginPassword").setValue("Aa123456");
        $(ButtonElement.class).first().click();

        Assert.assertEquals("Check that you have entered the correct username and password and try again.", $(LoginFormElement.class).id("loginForm").getErrorMessage());
    }

    @After
    public void tearDown(){
        driver.close();
    }

//    @AfterClass
    public void logout(){
        $(ButtonElement.class).id("logoutBtn").click();
        $(ButtonElement.class).last().click();
    }
}
