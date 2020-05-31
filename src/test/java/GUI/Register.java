package GUI;

import com.vaadin.flow.component.applayout.testbench.AppLayoutElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;

import com.vaadin.flow.component.html.testbench.H2Element;

import com.vaadin.flow.component.html.testbench.SpanElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.testbench.EmailFieldElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
public class Register extends TestBenchTestCase{
    WebDriver driver = null;
    WebDriverWait wait;

    @Before
    public void setup(){

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
        wait = new WebDriverWait(driver, 5);
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
    public void registerTest(){
        $(ButtonElement.class).id("signup").click();
        $(TextFieldElement.class).id("username").setValue("oransh");
        $(TextFieldElement.class).id("firstName").setValue("oran");
        $(TextFieldElement.class).id("lastName").setValue("shichman");
        $(PasswordFieldElement.class).id("password").setValue("Oran12345");
        $(PasswordFieldElement.class).id("confPass").setValue("Oran12345");
        $(EmailFieldElement.class).id("email").setValue("oran@gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        $(ButtonElement.class).id("save").click();
        Assert.assertTrue($(AppLayoutElement.class).id("footbalMain").isDisplayed());
        Assert.assertTrue($(ButtonElement.class).id("login").isDisplayed());
        $(ButtonElement.class).id("login").click();
        $(TextFieldElement.class).id("vaadinLoginUsername").setValue("oransh");
        $(PasswordFieldElement.class).id("vaadinLoginPassword").setValue("Oran12345");
        $(ButtonElement.class).first().click();

        Assert.assertTrue($(AppLayoutElement.class).id("footbalMain").isDisplayed());
        logout();
    }

    public void logout(){
        $(ButtonElement.class).id("logoutBtn").click();
        $(ButtonElement.class).last().click();
    }
}
