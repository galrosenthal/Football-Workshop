package Domain.Game;

import Domain.Users.CoachQualification;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class StadiumTest {


    @Test
    public void getPropertiesUTest() {
        Stadium stadium = new Stadium("New York");
        List<String> allProperties = stadium.getProperties();
        Assert.assertTrue(allProperties.size() == 1);
        Assert.assertTrue(allProperties.contains(stadium.namePropertyString));
    }

    @Test
    public void changeProperty1UTest() {
        Stadium stadium = new Stadium("New York");
        Assert.assertTrue(stadium.changeProperty(stadium.namePropertyString , "AESSEAL"));
        Assert.assertFalse(stadium.changeProperty("Test" , "Test"));
        Assert.assertTrue(stadium.getName().equals("AESSEAL"));

    }

    @Test
    public void changeProperty2UTest() {
        Stadium stadium = new Stadium("New York" , "AESEAL");
        Assert.assertTrue(stadium.changeProperty(stadium.namePropertyString , "AESSEAL"));
        Assert.assertFalse(stadium.changeProperty("Test" , "Test"));
        Assert.assertTrue(stadium.getName().equals("AESSEAL"));

    }

    @Test
    public void isListPropertyUTest() {
        Stadium stadium = new Stadium("New York");
        Assert.assertFalse(stadium.isListProperty(stadium.namePropertyString));
        Assert.assertFalse(stadium.isListProperty("Test"));
    }

    @Test
    public void isStringPropertyUTest() {
        Stadium stadium = new Stadium("New York");
        Assert.assertTrue(stadium.isStringProperty(stadium.namePropertyString));
        Assert.assertFalse(stadium.isStringProperty("Test"));
    }

    @Test
    public void isEnumPropertyUTest() {
        Stadium stadium = new Stadium("New York");
        Assert.assertFalse(stadium.isEnumProperty(stadium.namePropertyString));
    }

    @Test
    public void getAllValuesUTest() {
        Stadium stadium = new Stadium("New York");
        List<Enum> enumList = stadium.getAllValues(stadium.namePropertyString);
        Assert.assertNull(enumList);

    }

    @Test
    public void getAllPropertyListUTest() {
        Stadium stadium = new Stadium("New York");
        Assert.assertNull(stadium.getAllPropertyList(new TeamStub(6131) , stadium.namePropertyString));
    }

    @Test
    public void addPropertyUTest() {
        Stadium stadium = new Stadium("New York");
        Assert.assertFalse(stadium.addProperty(stadium.namePropertyString ,CoachQualification.MAIN_COACH ,new TeamStub(6131)));
    }

    @Test
    public void removePropertyUTest() {
        Stadium stadium = new Stadium("New York");
        Assert.assertFalse(stadium.removeProperty(stadium.namePropertyString ,CoachQualification.MAIN_COACH ,new TeamStub(6131)));
    }







}