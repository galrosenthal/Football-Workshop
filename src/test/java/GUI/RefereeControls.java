package GUI;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.html.testbench.H2Element;
import com.vaadin.flow.component.html.testbench.LabelElement;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RefereeControls extends TestBenchTestCase {
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
        $(SpanElement.class).id("referee controls").click();
    }


    private void BootUpSystem() throws InterruptedException {
        ButtonElement bootTheSystemBtn = $(ButtonElement.class).first();
        bootTheSystemBtn.click();
        Thread.sleep(500);
        $(TextFieldElement.class).id("1").setValue("Administrator");
        $(PasswordFieldElement.class).id("2").setValue("Aa123456");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ButtonElement.class).id("yes").click();
        Thread.sleep(500);
    }

    @Test
    public void displayScheduledGames() throws InterruptedException {
        $(ButtonElement.class).id("viewGameSched").click();
        Thread.sleep(500);
        Assert.assertTrue($(LabelElement.class).last().isDisplayed());
        $(ButtonElement.class).first().click();
    }

    @Test
    public void addEventGame() throws InterruptedException {
        $(ButtonElement.class).id("updateGame").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("Game{stadium=staName, homeTeam=Hapoel Beit Shan, awayTeam=Hapoel Beer Sheva, gameDate=Sun Feb 01 00:00:00 IST 3920}");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("Red Card");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).id("0").selectByText("AviCohen");
        Thread.sleep(1000);
        $(ComboBoxElement.class).id("1").selectByText("10");
        Thread.sleep(500);
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);

        Assert.assertEquals("The new Red Card has been added successfully",$(NotificationElement.class).first().getText());

    }

    @Test
    public void produceReport() throws InterruptedException {
        finishGame();
        $(ButtonElement.class).id("createReport").click();
        Thread.sleep(500);
        List<String> choices  = $(ComboBoxElement.class).first().getOptions();
        $(ComboBoxElement.class).first().selectByText(choices.get(0));
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);

        $(ButtonElement.class).id("ok").click();
        Thread.sleep(500);
    }

    private void finishGame() throws InterruptedException {
        $(ButtonElement.class).id("updateGame").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("Game{stadium=staName, homeTeam=Hapoel Beit Shan, awayTeam=Hapoel Beer Sheva, gameDate=Sun Feb 01 00:00:00 IST 3920}");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("Game End");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ButtonElement.class).id("yes").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("10");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
    }

}
