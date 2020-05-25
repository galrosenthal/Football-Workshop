package Domain.Game;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.Users.CoachQualification;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class StadiumTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.startTest();
        DBManagerForTest.startConnection();
    }

    @Test
    public void getPropertiesUTest() {
        Stadium stadium = new Stadium("AESSEAL", "New York");
        List<String> allProperties = stadium.getProperties();
        Assert.assertTrue(allProperties.size() == 1);
        Assert.assertTrue(allProperties.contains(stadium.namePropertyString));
    }

    @Test
    public void changeProperty1UTest() {
        Stadium stadium = new Stadium("AESSEAL", "New York");
        Assert.assertTrue(stadium.changeProperty(new TeamStub(0), stadium.namePropertyString, "AESSEAL"));
        Assert.assertFalse(stadium.changeProperty(new TeamStub(0), "Test", "Test"));
        Assert.assertTrue(stadium.getName().equals("AESSEAL"));

    }

    @Test
    public void changeProperty2UTest() {
        Stadium stadium = new Stadium("New York", "AESEAL");
        Assert.assertTrue(stadium.changeProperty(new TeamStub(0), stadium.namePropertyString, "AESSEAL"));
        Assert.assertFalse(stadium.changeProperty(new TeamStub(0), "Test", "Test"));
        Assert.assertTrue(stadium.getName().equals("AESSEAL"));

    }

    @Test
    public void isListPropertyUTest() {
        Stadium stadium = new Stadium("AESSEAL", "New York");
        Assert.assertFalse(stadium.isListProperty(stadium.namePropertyString));
        Assert.assertFalse(stadium.isListProperty("Test"));
    }

    @Test
    public void isStringPropertyUTest() {
        Stadium stadium = new Stadium("AESSEAL", "New York");
        Assert.assertTrue(stadium.isStringProperty(stadium.namePropertyString));
        Assert.assertFalse(stadium.isStringProperty("Test"));
    }

    @Test
    public void isEnumPropertyUTest() {
        Stadium stadium = new Stadium("AESSEAL", "New York");
        Assert.assertFalse(stadium.isEnumProperty(stadium.namePropertyString));
    }

    @Test
    public void getAllValuesUTest() {
        Stadium stadium = new Stadium("AESSEAL", "New York");
        List<Enum> enumList = stadium.getAllValues(stadium.namePropertyString);
        Assert.assertNull(enumList);

    }

    @Test
    public void getAllPropertyListUTest() {
        Stadium stadium = new Stadium("AESSEAL", "New York");
        Assert.assertNull(stadium.getAllPropertyList(new TeamStub(6131), stadium.namePropertyString));
    }

    @Test
    public void addPropertyUTest() {
        Stadium stadium = new Stadium("AESSEAL", "New York");
        Assert.assertFalse(stadium.addProperty(stadium.namePropertyString, CoachQualification.MAIN_COACH, new TeamStub(6131)));
    }

    @Test
    public void removePropertyUTest() {
        Stadium stadium = new Stadium("AESSEAL", "New York");
        Assert.assertFalse(stadium.removeProperty(stadium.namePropertyString, CoachQualification.MAIN_COACH, new TeamStub(6131)));
    }

    @AfterClass
    public static void afterClass() {
        DBManager.getInstance().closeConnection();
    }
}