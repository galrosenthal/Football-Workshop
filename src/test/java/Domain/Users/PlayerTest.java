package Domain.Users;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Game.Team;
import Domain.Game.TeamStub;
import Service.UIController;
import org.junit.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    Team teamStub;
    TeamOwner ownerStub;

    Player playerToTest;
    SystemUser testUser;

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.startTest();
        DBManagerForTest.startConnection();    }

    @Before
    public void setUp() throws Exception {
        teamStub = new TeamStub(0);
        testUser = new SystemUserStub("test","testUser",4);
        ownerStub = new TeamOwnerStub(testUser);
    }

    @Test
    public void addTeamUTest() throws Exception{
        Date bday = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993");
        Player p1 = new Player(testUser,bday, true);

        //Selector is 0
        assertFalse(p1.addTeam(teamStub,ownerStub));
        ((TeamStub)teamStub).setSelector(1);
        //Null Team returns false
        assertFalse(p1.addTeam(null,ownerStub));

        assertTrue(p1.addTeam(teamStub,ownerStub));
        ((TeamStub)teamStub).setSelector(2);
        assertFalse(p1.addTeam(teamStub,ownerStub));
    }


    @Test
    public void addAllPropertiesUTest() throws Exception{
        UIController.setIsTest(true);
        UIController.setSelector(61120);
        Date bday = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993");
        Player p1 = new Player(testUser,bday, true);

        TeamStub ts = new TeamStub(61120);
        BelongToTeamStub bgStub = new BelongToTeamStub(ts, p1);
        p1.addTeamConnection(bgStub);


        assertTrue(p1.addAllProperties(ts));

    }

    @Test
    public void addPropertyUTest() throws Exception{
        UIController.setIsTest(true);
        UIController.setSelector(61120);
        Date bday = new SimpleDateFormat("dd/MM/yyyy").parse("01/11/1993");
        Player p1 = new Player(testUser,bday, true);

        TeamStub ts = new TeamStub(61120);
        BelongToTeamStub bgStub = new BelongToTeamStub(ts, p1);
        p1.addTeamConnection(bgStub);

        assertFalse(p1.addProperty(ts, "NotAValidString"));
        assertTrue(p1.addProperty(ts, p1.fieldJobString));

    }

    @Test
    public void getPropertiesUTest() {
        Player player = new Player(new SystemUserStub("playerTest" , "gal" , 6131) , new Date(), true);
        List<String> allProperties = player.getProperties();
        assertTrue(allProperties.size() == 1);
        assertTrue(allProperties.contains(player.fieldJobString));
    }

    @Test
    public void changePropertyUTest() {
        Player player = new Player(new SystemUserStub("playerTest" , "gal" , 6131) , new Date(), true);
        TeamStub ts = new TeamStub(61120);
        BelongToTeamStub bgStub = new BelongToTeamStub(ts,player);
        player.addTeamConnection(bgStub);
        Assert.assertTrue(player.changeProperty(ts,player.fieldJobString , PlayerFieldJobs.DEFENSE.toString()));
        Assert.assertFalse(player.changeProperty(ts,"test" , CoachQualification.MAIN_COACH.toString()));

    }

    @Test
    public void isListPropertyUTest() {
        Player player = new Player(new SystemUserStub("playerTest" , "gal" , 6131) , new Date(), true);
        Assert.assertFalse(player.isListProperty(player.fieldJobString));
    }

    @Test
    public void isStringPropertyUTest() {
        Player player = new Player(new SystemUserStub("playerTest" , "gal" , 6131) , new Date(), true);
        Assert.assertFalse(player.isStringProperty(player.fieldJobString));
        Assert.assertFalse(player.isStringProperty("Test"));
    }

    @Test
    public void isEnumPropertyUTest() {
        Player player = new Player(new SystemUserStub("playerTest" , "gal" , 6131) , new Date(), true);
        Assert.assertTrue(player.isEnumProperty(player.fieldJobString));
        Assert.assertFalse(player.isEnumProperty("Test"));
    }

    @Test
    public void getAllValuesUTest() {
        Player player = new Player(new SystemUserStub("playerTest" , "gal" , 6131) , new Date(), true);
        List<Enum> enumList = player.getAllValues(player.fieldJobString);
        Assert.assertTrue(enumList.size() == PlayerFieldJobs.values().length);
        Assert.assertTrue(enumList.contains(PlayerFieldJobs.DEFENSE));
        Assert.assertTrue(enumList.contains(PlayerFieldJobs.FRONT));
        Assert.assertTrue(enumList.contains(PlayerFieldJobs.GOAL_KEEPER));
        enumList = player.getAllValues("Test");
        Assert.assertNull(enumList);
    }

    @Test
    public void getAllPropertyListUTest() {
        Player player = new Player(new SystemUserStub("playerTest" , "gal" , 6131) , new Date(), true);
        Assert.assertNull(player.getAllPropertyList(new TeamStub(6131) , player.fieldJobString));
    }

    @Test
    public void addPropertyEnumFalseUTest() {
        Player player = new Player(new SystemUserStub("playerTest" , "gal" , 6131) , new Date(), true);
        Assert.assertFalse(player.addProperty(player.fieldJobString ,PlayerFieldJobs.FRONT ,new TeamStub(6131)));
    }

    @Test
    public void removePropertyUTest() {
        Player player = new Player(new SystemUserStub("playerTest" , "gal" , 6131) , new Date(), true);
        Assert.assertFalse(player.removeProperty(player.fieldJobString ,PlayerFieldJobs.FRONT ,new TeamStub(6131)));
    }


    @Test
    public void getPropertiesITest() {
        Player player = new Player(new SystemUser("playerTest" , "gal" ) , new Date(), true);
        List<String> allProperties = player.getProperties();
        Assert.assertTrue(allProperties.size() == 1);
        Assert.assertTrue(allProperties.contains(player.fieldJobString));
    }



    @Test
    public void changePropertyITest() {
        Player player = new Player(new SystemUser("playerTest" , "gal" ) , new Date(), true);
        TeamStub ts = new TeamStub(61120);
        BelongToTeamStub bgStub = new BelongToTeamStub(ts,player);
        player.addTeamConnection(bgStub);
        Assert.assertTrue(player.changeProperty(ts,player.fieldJobString , PlayerFieldJobs.DEFENSE.toString()));
        Assert.assertFalse(player.changeProperty(ts,"test" , CoachQualification.MAIN_COACH.toString()));

    }

    @Test
    public void isListPropertyITest() {
        Player player = new Player(new SystemUser("playerTest" , "gal" ) , new Date(), true);
        Assert.assertFalse(player.isListProperty(player.fieldJobString));
    }

    @Test
    public void isStringPropertyITest() {
        Player player = new Player(new SystemUser("playerTest" , "gal" ) , new Date(), true);
        Assert.assertFalse(player.isStringProperty(player.fieldJobString));
        Assert.assertFalse(player.isStringProperty("Test"));
    }

    @Test
    public void isEnumPropertyITest() {
        Player player = new Player(new SystemUser("playerTest" , "gal" ) , new Date(), true);
        Assert.assertTrue(player.isEnumProperty(player.fieldJobString));
        Assert.assertFalse(player.isEnumProperty("Test"));
    }

    @Test
    public void getAllValuesITest() {
        Player player = new Player(new SystemUser("playerTest" , "gal" ) , new Date(), true);
        List<Enum> enumList = player.getAllValues(player.fieldJobString);
        Assert.assertTrue(enumList.size() == PlayerFieldJobs.values().length);
        Assert.assertTrue(enumList.contains(PlayerFieldJobs.DEFENSE));
        Assert.assertTrue(enumList.contains(PlayerFieldJobs.FRONT));
        Assert.assertTrue(enumList.contains(PlayerFieldJobs.GOAL_KEEPER));
        enumList = player.getAllValues("Test");
        Assert.assertNull(enumList);
    }

    @Test
    public void getAllPropertyListITest() {
        Player player = new Player(new SystemUser("playerTest" , "gal" ) , new Date(), true);
        Assert.assertNull(player.getAllPropertyList(new TeamStub(6131) , player.fieldJobString));
    }

    @Test
    public void addPropertyITest() {
        Player player = new Player(new SystemUser("playerTest" , "gal" ) , new Date(), true);
        Assert.assertFalse(player.addProperty(player.fieldJobString ,PlayerFieldJobs.FRONT ,new TeamStub(6131)));
    }

    @Test
    public void removePropertyITest() {
        Player player = new Player(new SystemUser("playerTest" , "gal" ) , new Date(), true);
        Assert.assertFalse(player.removeProperty(player.fieldJobString ,PlayerFieldJobs.FRONT ,new TeamStub(6131)));
    }

    @After
    public void tearDown() {
        EntityManager.getInstance().clearAll();
    }

    @AfterClass
    public static void afterClass() {
        DBManager.getInstance().closeConnection();
    }
}