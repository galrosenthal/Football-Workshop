package GUI;

import Domain.EntityManager;
import Service.MainController;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.dialog.testbench.DialogElement;
import com.vaadin.flow.component.html.testbench.H2Element;
import com.vaadin.flow.component.html.testbench.LabelElement;
import com.vaadin.flow.component.html.testbench.SpanElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.orderedlayout.testbench.VerticalLayoutElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;

import javax.management.Notification;
import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginTest extends TestBenchTestCase {
    //private ChromeDriver driver;
    @BeforeClass
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
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        try{
            if($(H2Element.class).id("bootH2").isDisplayed()) {
                BootUpSystem();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void BootUpSystem(){
        ButtonElement bootTheSystemBtn = $(ButtonElement.class).first();
        //Assert.assertEquals("Boot the System", bootTheSystemBtn.getText());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        bootTheSystemBtn.click();
        //Assert.assertEquals("Please enter a system administrator username:", $(LabelElement.class).first().getText());
        $(TextFieldElement.class).id("1").setValue("Administrator");
        $(PasswordFieldElement.class).id("2").setValue("Aa123456");
        $(ButtonElement.class).id("submit").click();
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        $(ButtonElement.class).id("yes").click();
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //Assert.assertEquals("Welcome to the Football System. if you wish to go to the about page press the button below", $(SpanElement.class).id("welcomeSpan").getText());
    }

    @Test
    public void loginSuccessTest(){
        $(ButtonElement.class).id("login").click();
        Assert.assertEquals("Log in as \"admin\" to have full access. Log in with any other username to have read-only access. For all users, the password is same as the username.", $(SpanElement.class).id("loginInfoText").getText());
        $(TextFieldElement.class).id("vaadinLoginUsername").setValue("Administrator");
        $(PasswordFieldElement.class).id("vaadinLoginPassword").setValue("Aa123456");
        $(ButtonElement.class).first().click();
        Assert.assertEquals("About", $(SpanElement.class).id("about").getText());
        //Assert.assertEquals("Log out", $(SpanElement.class).id("about").getText());
    }

    @Test
    public void loginFailureTest(){

    }

    @After
    public void tearDown(){

        driver.close();
    }
}
