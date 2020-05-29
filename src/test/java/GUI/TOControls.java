package GUI;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.html.testbench.H2Element;
import com.vaadin.flow.component.html.testbench.SpanElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TOControls extends TestBenchTestCase {
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

        login();
    }

    private void login() {
        $(ButtonElement.class).id("login").click();
        $(TextFieldElement.class).id("vaadinLoginUsername").setValue("Administrator");
        $(PasswordFieldElement.class).id("vaadinLoginPassword").setValue("Aa123456");
        $(ButtonElement.class).first().click();
        $(SpanElement.class).id("team owner controls").click();
    }


    private void BootUpSystem() throws InterruptedException {
        ButtonElement bootTheSystemBtn = $(ButtonElement.class).first();
        bootTheSystemBtn.click();
        Thread.sleep(500);
        $(TextFieldElement.class).id("1").setValue("Administrator");
        $(PasswordFieldElement.class).id("2").setValue("Aa123456");
        $(ButtonElement.class).id("submit").click();
        $(ButtonElement.class).id("yes").click();
    }

    @Test
    public void closeTeamTest() throws InterruptedException {
        addTeam("4");
        $(ButtonElement.class).id("closetTeam").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("team4");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ButtonElement.class).id("yes").click();
        Thread.sleep(1000);
        Assert.assertEquals("Team \"team4\" closeed successfully",$(NotificationElement.class).id("notification").getText());
        $(ButtonElement.class).id("close").click();
        Thread.sleep(500);
        $(ButtonElement.class).id("close").click();
    }

    private void addTeam(String num) throws InterruptedException {
        $(SpanElement.class).id("ar controls").click();
        $(ButtonElement.class).id("regNewTeam").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("team"+num);
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("Administrator");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        $(SpanElement.class).id("team owner controls").click();
    }

    @Test
    public void reOpenTeamTest() throws InterruptedException {
        addAndCloseTeam();
        $(ButtonElement.class).id("reopenTeam").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("team5 (closed)");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ButtonElement.class).id("yes").click();
        Thread.sleep(1000);
        Assert.assertEquals("Team \"team5\" reopened successfully",$(NotificationElement.class).id("notification").getText());
    }

    @Test
    public void addAnotherTeamOwnerTest() throws InterruptedException {
        addTeam("7");
        $(ButtonElement.class).id("addAnotherTeamOwner").click();
        Thread.sleep(500);
    }

    @Test
    public void addTeamAssetTest() throws InterruptedException {
        $(ButtonElement.class).id("addTeamAsset").click();
        Thread.sleep(500);
    }

    private void addAndCloseTeam() throws InterruptedException {
        $(SpanElement.class).id("ar controls").click();
        $(ButtonElement.class).id("regNewTeam").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("team5");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("Administrator");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        $(SpanElement.class).id("team owner controls").click();
        Thread.sleep(500);
        $(ButtonElement.class).id("closetTeam").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("team5");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ButtonElement.class).id("yes").click();
        Thread.sleep(1000);
        $(ButtonElement.class).id("close").click();
        Thread.sleep(500);
        $(ButtonElement.class).id("close").click();
        Thread.sleep(1000);
        $(SpanElement.class).id("team owner controls").click();
        Thread.sleep(1000);
    }
}
