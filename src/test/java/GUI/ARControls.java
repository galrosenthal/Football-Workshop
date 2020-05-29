package GUI;

import Domain.EntityManager;
import Domain.Users.SystemUser;
import Domain.Users.TeamOwner;
import com.vaadin.flow.component.applayout.testbench.AppLayoutElement;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.datepicker.testbench.DatePickerElement;
import com.vaadin.flow.component.html.testbench.H2Element;
import com.vaadin.flow.component.html.testbench.SpanElement;
import com.vaadin.flow.component.listbox.testbench.ListBoxElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.select.testbench.SelectElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class ARControls extends TestBenchTestCase {

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
        $(SpanElement.class).id("ar controls").click();
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
    public void addNewTeamTest() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("regNewTeam")));
        $(ButtonElement.class).id("regNewTeam").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("Hapoel Beer Sheva4");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("Administrator");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The team Hapoel Beer Sheva4 has been created successfully",$(NotificationElement.class).id("notification").getText());
        createLeagueAndSeason();
        $(ButtonElement.class).id("addTeamsToSeason").click();
        $(ComboBoxElement.class).first().selectByText("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ListBoxElement.class).first().selectByText("Hapoel Beer Sheva4");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The teams have been successfully assigned to the league's latest season",$(NotificationElement.class).id("notification").getText());
    }

    private void createLeagueAndSeason() throws InterruptedException {
        $(ButtonElement.class).id("addNewLeague").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(SpanElement.class).id("ar controls").click();
        $(ButtonElement.class).id("addSeasonToLeague").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("2019/20");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        if($(NotificationElement.class).id("notification").getText().equals("This season already exists in the chosen league. Please enter different years")){
            $(ButtonElement.class).id("cancel").click();
            Thread.sleep(1000);
        }
//        $(ButtonElement.class).id("addTeamsToSeason").click();
//        Thread.sleep(500);
//        $(ComboBoxElement.class).first().selectByText("league1");
//        $(ButtonElement.class).id("submit").click();
//        Thread.sleep(500);

    }

    @Test
    public void createNewPointPolicyTest() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("definePointPolicy")));
        $(ButtonElement.class).id("definePointPolicy").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).id("0").selectByText("4");
        $(ComboBoxElement.class).id("1").selectByText("-1");
        $(ComboBoxElement.class).id("2").selectByText("2");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The new points policy has been added successfully",$(NotificationElement.class).id("notification").getText());
    }

    @Test
    public void setPointsPolicy() throws InterruptedException {
        createLeagueAndSeason();
        createNewPointPolicy();
        $(ButtonElement.class).id("setPointsPolicy").click();
        $(ComboBoxElement.class).first().selectByText("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("victoryPoints=3, lossPoints=-2, tiePoints=1");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The chosen points policy was set successfully",$(NotificationElement.class).id("notification").getText());
    }

    @Test
    public void createNewGamePolicyTest() throws InterruptedException {
        $(ButtonElement.class).id("defineGamePolicy").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).id("0").selectByText("5");
        $(ComboBoxElement.class).id("1").selectByText("5");
        $(ComboBoxElement.class).id("2").selectByText("5");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The new scheduling policy has been added successfully",$(NotificationElement.class).id("notification").getText());
    }

    @Test
    public void ActivateSchedulingPolicy() throws InterruptedException {
        createLeagueAndSeason();
        createGamePolicy();
        //addTeamToSeason();
        createTeams();
        $(ButtonElement.class).id("activeSchedulingPolicy").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(DatePickerElement.class).id("picker").setDate(LocalDate.now());
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("Games Per Day = 3, Games Per Season = 2, Minimum rest days = 4");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The chosen points policy was set successfully",$(NotificationElement.class).id("notification").getText());
    }

//    private void addTeamToSeason() throws InterruptedException {
//        $(ButtonElement.class).id("add").click();
//        Thread.sleep(500);
//    }

    @Test
    public void addNewLeague() throws InterruptedException {
        $(ButtonElement.class).id("addNewLeague").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("liga");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertTrue($(AppLayoutElement.class).id("footbalMain").isDisplayed());
    }

    @Test
    public void addSeasonToLeague() throws InterruptedException {
        createLeague();
        $(ButtonElement.class).id("addSeasonToLeague").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("halufot");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("2019/20");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        if($(NotificationElement.class).id("notification").getText().equals("This season already exists in the chosen league. Please enter different years")){
            $(ButtonElement.class).id("cancel").click();
            Thread.sleep(1000);
        }
        else{
            Assert.assertEquals("The season was created successfully",$(NotificationElement.class).id("notification").getText());
        }
    }

    @Test
    public void addNewRefereeTest() throws InterruptedException {
        $(ButtonElement.class).id("addReferee").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("referee");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("VAR_REFEREE");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The referee has been added successfully",$(NotificationElement.class).id("notification").getText());
    }

    @Test
    public void removeRefereeTest() throws InterruptedException {
        addReferee();
        $(ButtonElement.class).id("removeReferee").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("Oran");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The referee has been removed successfully",$(NotificationElement.class).id("notification").getText());
    }

    @Test
    public void assignRefereeTest() throws InterruptedException {
        createLeagueAndSeason();
        addReferee();
        $(ButtonElement.class).id("assignReferee").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("2019/20");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("Oran");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The referee has been assigned to the season successfully",$(NotificationElement.class).id("notification").getText());
    }

    @Test
    public void addTeamsToSeasonTest() throws InterruptedException {
        createLeagueAndSeason();
        $(ButtonElement.class).id("addTeamsToSeason").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ListBoxElement.class).first().selectByText("team1");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The teams have been successfully assigned to the league's latest season",$(NotificationElement.class).id("notification").getText());
    }

    @Test
    public void removeTeamsFromSeason() throws InterruptedException {
        createLeagueAndSeason();
        addTeamsToSeason();
        $(ButtonElement.class).id("removeTeamsFromSeason").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ListBoxElement.class).first().selectByText("team1");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        Assert.assertEquals("The teams have been successfully removed from the league's latest season",$(NotificationElement.class).id("notification").getText());

    }

    private void addTeamsToSeason() throws InterruptedException {
        $(ButtonElement.class).id("addTeamsToSeason").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        try {
            if ($(NotificationElement.class).all().size() == 0) {
                $(ListBoxElement.class).first().selectByText("team1");
                $(ListBoxElement.class).first().selectByText("team2");
                $(ButtonElement.class).id("submit").click();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("no element");
            $(ListBoxElement.class).first().selectByText("team1");
            $(ListBoxElement.class).first().selectByText("team2");
            $(ButtonElement.class).id("submit").click();
            Thread.sleep(1000);
        }
    }

    private void addReferee() throws InterruptedException {
        $(ButtonElement.class).id("addReferee").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("user1");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("VAR_REFEREE");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
    }

    private void createLeague() throws InterruptedException {
        $(ButtonElement.class).id("addNewLeague").click();
        Thread.sleep(500);
        $(TextFieldElement.class).first().setValue("halufot");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        $(SpanElement.class).id("ar controls").click();
    }

    private void createTeams() throws InterruptedException {

        $(ButtonElement.class).id("addTeamsToSeason").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ListBoxElement.class).first().selectByText("team1");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ButtonElement.class).id("addTeamsToSeason").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).first().selectByText("league3");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        $(ListBoxElement.class).first().selectByText("team2");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(500);
        driver.navigate().refresh();
        Thread.sleep(2000);
//        $(SpanElement.class).id("team owner controls").click();
//        Thread.sleep(500);
//        $(ButtonElement.class).id("addTeamAsset").click();
//        Thread.sleep(500);
//        $(ComboBoxElement.class).first().selectByText("team1");
//        $(ButtonElement.class).id("submit").click();
//        Thread.sleep(500);
//        $(ComboBoxElement.class).first().selectByText("STADIUM");
//        $(ButtonElement.class).id("submit").click();
//        Thread.sleep(500);
//        $(TextFieldElement.class).first().setValue("stadium1");
//        $(ButtonElement.class).id("submit").click();
//        Thread.sleep(500);
//        $(TextFieldElement.class).first().setValue("stadium1");
//        $(ButtonElement.class).id("submit").click();
//        Thread.sleep(500);
//
//        $(ButtonElement.class).id("addTeamAsset").click();
//        Thread.sleep(500);
//        $(ComboBoxElement.class).first().selectByText("team2");
//        $(ButtonElement.class).id("submit").click();
//        Thread.sleep(500);
//        $(ComboBoxElement.class).first().selectByText("STADIUM");
//        $(ButtonElement.class).id("submit").click();
//        Thread.sleep(500);
//        $(TextFieldElement.class).first().setValue("stadium2");
//        $(ButtonElement.class).id("submit").click();
//        Thread.sleep(500);
//        $(TextFieldElement.class).first().setValue("stadium2");
//        $(ButtonElement.class).id("submit").click();
//        Thread.sleep(500);
        $(SpanElement.class).id("ar controls").click();
    }

    private void createNewPointPolicy() throws InterruptedException {
        $(ButtonElement.class).id("definePointPolicy").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).id("0").selectByText("3");
        $(ComboBoxElement.class).id("1").selectByText("-2");
        $(ComboBoxElement.class).id("2").selectByText("1");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        if($(NotificationElement.class).id("notification").getText().equals("This points policy already exists")){
            $(ButtonElement.class).id("cancel").click();
            Thread.sleep(500);
        }
    }

    private void createGamePolicy() throws InterruptedException {
        $(ButtonElement.class).id("defineGamePolicy").click();
        Thread.sleep(500);
        $(ComboBoxElement.class).id("0").selectByText("2");
        $(ComboBoxElement.class).id("1").selectByText("3");
        $(ComboBoxElement.class).id("2").selectByText("4");
        $(ButtonElement.class).id("submit").click();
        Thread.sleep(1000);
        if($(NotificationElement.class).id("notification").getText().equals("This scheduling policy already exists")){
            Thread.sleep(500);
        }
    }
//    @Rule
//    public TestWatcher watchman = new TestWatcher() {
//        @Override
//        protected void failed(Throwable e, Description description) {
//            $(ButtonElement.class).id("cancel").click();
//            $(ButtonElement.class).id("logoutBtn").click();
//            $(ButtonElement.class).last().click();
//        }
//    };

    @After
    public void logout(){
        $(ButtonElement.class).id("logoutBtn").click();
        $(ButtonElement.class).last().click();
    }
}
