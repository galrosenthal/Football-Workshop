package Domain.Users;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Game.Team;
import Domain.Game.TeamStub;
import org.junit.*;

import java.util.List;


public class CoachTest {


    @Test
    public void getPropertiesUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131, true), true);
        List<String> allProperties = coach.getProperties();
        Assert.assertTrue(allProperties.size() == 2);
        Assert.assertTrue(allProperties.contains(coach.teamJobString));
        Assert.assertTrue(allProperties.contains(coach.qualificationString));
    }

    @Test
    public void changePropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131, true), true);
        TeamStub ts = new TeamStub(6131, true);
        BelongToTeamStub bgStub = new BelongToTeamStub(ts, coach);
        Assert.assertTrue(coach.addTeamConnection(bgStub));
        Assert.assertTrue(coach.changeProperty(ts, coach.teamJobString, "Test"));
        Assert.assertTrue(coach.changeProperty(ts, coach.qualificationString, CoachQualification.MAIN_COACH.toString()));
        Assert.assertFalse(coach.changeProperty(ts, "test", CoachQualification.MAIN_COACH.toString()));

    }

    @Test
    public void isListPropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131, true), true);
        Assert.assertFalse(coach.isListProperty(coach.teamJobString));
        Assert.assertFalse(coach.isListProperty(coach.qualificationString));
    }

    @Test
    public void isStringPropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131, true), true);
        Assert.assertTrue(coach.isStringProperty(coach.teamJobString));
        Assert.assertFalse(coach.isStringProperty(coach.qualificationString));
    }

    @Test
    public void isEnumPropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131, true), true);
        Assert.assertFalse(coach.isEnumProperty(coach.teamJobString));
        Assert.assertTrue(coach.isEnumProperty(coach.qualificationString));
    }

    @Test
    public void getAllValuesUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131, true), true);
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
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131, true), true);
        Assert.assertNull(coach.getAllPropertyList(new TeamStub(6131, true), coach.teamJobString));
        Assert.assertNull(coach.getAllPropertyList(new TeamStub(6131, true), coach.qualificationString));
    }

    @Test
    public void addPropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131, true), true);
        Assert.assertFalse(coach.addProperty(coach.teamJobString, CoachQualification.MAIN_COACH, new TeamStub(6131, true)));
    }

    @Test
    public void removePropertyUTest() {
        Coach coach = new Coach(new SystemUserStub("coachTest", "gal", 6131, true), true);
        Assert.assertFalse(coach.removeProperty(coach.teamJobString, CoachQualification.MAIN_COACH, new TeamStub(6131, true)));
    }


    @Test
    public void getPropertiesITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal", true), true);
        List<String> allProperties = coach.getProperties();
        Assert.assertTrue(allProperties.size() == 2);
        Assert.assertTrue(allProperties.contains(coach.teamJobString));
        Assert.assertTrue(allProperties.contains(coach.qualificationString));
    }

    @Test
    public void changePropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal", true), true);
        Team team = new Team("Test", true);
        BelongToTeam bg = new BelongToTeam(team, coach);
        Assert.assertTrue(coach.addTeamConnection(bg));
        Assert.assertTrue(coach.changeProperty(team, coach.teamJobString, "Test"));
        Assert.assertTrue(coach.changeProperty(team, coach.qualificationString, CoachQualification.MAIN_COACH.toString()));
        Assert.assertFalse(coach.changeProperty(team, "test", CoachQualification.MAIN_COACH.toString()));

    }

    @Test
    public void isListPropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal", true), true);
        Assert.assertFalse(coach.isListProperty(coach.teamJobString));
        Assert.assertFalse(coach.isListProperty(coach.qualificationString));
    }

    @Test
    public void isStringPropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal", true), true);
        Assert.assertTrue(coach.isStringProperty(coach.teamJobString));
        Assert.assertFalse(coach.isStringProperty(coach.qualificationString));
    }

    @Test
    public void isEnumPropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal", true), true);
        Assert.assertFalse(coach.isEnumProperty(coach.teamJobString));
        Assert.assertTrue(coach.isEnumProperty(coach.qualificationString));
    }

    @Test
    public void getAllValuesITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal", true), true);
        List<Enum> enumList = coach.getAllValues(coach.qualificationString);
        Assert.assertTrue(enumList.size() == CoachQualification.values().length);
        Assert.assertTrue(enumList.contains(CoachQualification.MAIN_COACH));
        Assert.assertTrue(enumList.contains(CoachQualification.JUNIOR_COACH));
        Assert.assertTrue(enumList.contains(CoachQualification.SECOND_COACH));
    }

    @Test
    public void getAllPropertyListITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal", true), true);
        Assert.assertNull(coach.getAllPropertyList(new TeamStub(6131, true), coach.teamJobString));
        Assert.assertNull(coach.getAllPropertyList(new TeamStub(6131, true), coach.qualificationString));
    }

    @Test
    public void addPropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal", true), true);
        Assert.assertFalse(coach.addProperty(coach.teamJobString, CoachQualification.MAIN_COACH, new TeamStub(6131, true)));
    }

    @Test
    public void removePropertyITest() {
        Coach coach = new Coach(new SystemUser("coachTest", "gal", true), true);
        Assert.assertFalse(coach.removeProperty(coach.teamJobString, CoachQualification.MAIN_COACH, new TeamStub(6131, true)));
    }

    @After
    public void tearDown() {
        EntityManager.getInstance().clearAll();
    }



}