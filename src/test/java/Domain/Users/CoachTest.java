package Domain.Users;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Game.Team;
import Domain.Game.TeamStub;
import org.junit.*;

import java.util.List;


public class CoachTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.startTest();
        DBManagerForTest.startConnection();    }

    @Test
    public void getPropertiesUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131));
        List<String> allProperties = coach.getProperties();
        Assert.assertTrue(allProperties.size() == 2);
        Assert.assertTrue(allProperties.contains(coach.teamJobString));
        Assert.assertTrue(allProperties.contains(coach.qualificationString));
    }

    @Test
    public void changePropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131));
        TeamStub ts = new TeamStub(6131);
        BelongToTeamStub bgStub = new BelongToTeamStub(ts, coach);
        Assert.assertTrue(coach.addTeamConnection(bgStub));
        Assert.assertTrue(coach.changeProperty(ts, coach.teamJobString, "Test"));
        Assert.assertTrue(coach.changeProperty(ts, coach.qualificationString, CoachQualification.MAIN_COACH.toString()));
        Assert.assertFalse(coach.changeProperty(ts, "test", CoachQualification.MAIN_COACH.toString()));

    }

    @Test
    public void isListPropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131));
        Assert.assertFalse(coach.isListProperty(coach.teamJobString));
        Assert.assertFalse(coach.isListProperty(coach.qualificationString));
    }

    @Test
    public void isStringPropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131));
        Assert.assertTrue(coach.isStringProperty(coach.teamJobString));
        Assert.assertFalse(coach.isStringProperty(coach.qualificationString));
    }

    @Test
    public void isEnumPropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131));
        Assert.assertFalse(coach.isEnumProperty(coach.teamJobString));
        Assert.assertTrue(coach.isEnumProperty(coach.qualificationString));
    }

    @Test
    public void getAllValuesUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131));
        List<Enum> enumList = coach.getAllValues(coach.qualificationString);
        Assert.assertTrue(enumList.size() == CoachQualification.values().length);
        Assert.assertTrue(enumList.contains(CoachQualification.MAIN_COACH));
        Assert.assertTrue(enumList.contains(CoachQualification.JUNIOR_COACH));
        Assert.assertTrue(enumList.contains(CoachQualification.SECOND_COACH));
        enumList = coach.getAllValues("Test");
        Assert.assertNull(enumList);
    }

    @Test
    public void getAllPropertyListUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131));
        Assert.assertNull(coach.getAllPropertyList(new TeamStub(6131), coach.teamJobString));
        Assert.assertNull(coach.getAllPropertyList(new TeamStub(6131), coach.qualificationString));
    }

    @Test
    public void addPropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131));
        Assert.assertFalse(coach.addProperty(coach.teamJobString, CoachQualification.MAIN_COACH, new TeamStub(6131)));
    }

    @Test
    public void removePropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131));
        Assert.assertFalse(coach.removeProperty(coach.teamJobString, CoachQualification.MAIN_COACH, new TeamStub(6131)));
    }


    @Test
    public void getPropertiesITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal"));
        List<String> allProperties = coach.getProperties();
        Assert.assertTrue(allProperties.size() == 2);
        Assert.assertTrue(allProperties.contains(coach.teamJobString));
        Assert.assertTrue(allProperties.contains(coach.qualificationString));
    }

    @Test
    public void changePropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal"));
        Team team = new Team();
        BelongToTeam bg = new BelongToTeam(team, coach);
        Assert.assertTrue(coach.addTeamConnection(bg));
        Assert.assertTrue(coach.changeProperty(team, coach.teamJobString, "Test"));
        Assert.assertTrue(coach.changeProperty(team, coach.qualificationString, CoachQualification.MAIN_COACH.toString()));
        Assert.assertFalse(coach.changeProperty(team, "test", CoachQualification.MAIN_COACH.toString()));

    }

    @Test
    public void isListPropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal"));
        Assert.assertFalse(coach.isListProperty(coach.teamJobString));
        Assert.assertFalse(coach.isListProperty(coach.qualificationString));
    }

    @Test
    public void isStringPropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal"));
        Assert.assertTrue(coach.isStringProperty(coach.teamJobString));
        Assert.assertFalse(coach.isStringProperty(coach.qualificationString));
    }

    @Test
    public void isEnumPropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal"));
        Assert.assertFalse(coach.isEnumProperty(coach.teamJobString));
        Assert.assertTrue(coach.isEnumProperty(coach.qualificationString));
    }

    @Test
    public void getAllValuesITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal"));
        List<Enum> enumList = coach.getAllValues(coach.qualificationString);
        Assert.assertTrue(enumList.size() == CoachQualification.values().length);
        Assert.assertTrue(enumList.contains(CoachQualification.MAIN_COACH));
        Assert.assertTrue(enumList.contains(CoachQualification.JUNIOR_COACH));
        Assert.assertTrue(enumList.contains(CoachQualification.SECOND_COACH));
    }

    @Test
    public void getAllPropertyListITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal"));
        Assert.assertNull(coach.getAllPropertyList(new TeamStub(6131), coach.teamJobString));
        Assert.assertNull(coach.getAllPropertyList(new TeamStub(6131), coach.qualificationString));
    }

    @Test
    public void addPropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal"));
        Assert.assertFalse(coach.addProperty(coach.teamJobString, CoachQualification.MAIN_COACH, new TeamStub(6131)));
    }

    @Test
    public void removePropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal"));
        Assert.assertFalse(coach.removeProperty(coach.teamJobString, CoachQualification.MAIN_COACH, new TeamStub(6131)));
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