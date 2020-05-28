package Domain.Game;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.SystemLogger.SystemLoggerManager;
import Domain.Users.*;
import org.junit.*;

import java.time.Year;

import static org.junit.Assert.*;

public class SeasonTest {

    private Season season;

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.startTest();
        DBManagerForTest.startConnection();
        SystemLoggerManager.disableLoggers(); // disable loggers in tests

    }

    @Before
    public void setUp() throws Exception {
        season = new Season(new League("noName"),"2020/21");
    }

    @Test
    public void getYearUTest() {
        assertTrue(season.getYear().equals(Year.parse("2021")));
    }

    @Test
    public void getYearsUTest() {
        assertTrue(season.getYears().equals("2020/21"));
    }

    @Test
    public void isGoodYearsFormatUTest() {
        assertTrue(Season.isGoodYearsFormat("2020/21"));
        assertFalse(Season.isGoodYearsFormat("2020/211"));
        assertFalse(Season.isGoodYearsFormat("2022/21"));
        assertFalse(Season.isGoodYearsFormat("20das1"));
    }

    @Test
    public void assignAndUnAssignRefereeUTest() {
        Referee referee = new RefereeStub(new SystemUserStub("stubUsername", "stub", 93121), RefereeQualification.VAR_REFEREE);
        assertTrue(season.refereesSize()==0);
        season.assignReferee(referee);
        assertTrue(season.refereesSize()==1);
        assertTrue(season.getReferees().contains(referee));

        season.unAssignReferee(referee);
        assertTrue(season.refereesSize()==0);
        assertFalse(season.getReferees().contains(referee));
    }
    //null test

    @Test
    public void unAssignRefereeUTest() {
        season.unAssignReferee(null);
        assertTrue(season.refereesSize()==0);
    }
    @Test
    public void doesContainsRefereeUTest() {
        Referee referee = new RefereeStub(new SystemUserStub("stubUsername", "stub", 93121),RefereeQualification.VAR_REFEREE);
        assertFalse(season.doesContainsReferee(referee));
        season.assignReferee(referee);
        assertTrue(season.doesContainsReferee(referee));
    }

    @Test
    public void assignAndUnAssignRefereeITest() {
        Referee referee = new Referee(new SystemUser("username", "name"),RefereeQualification.VAR_REFEREE);
        assertTrue(season.refereesSize()==0);
        season.assignReferee(referee);
        assertTrue(season.refereesSize()==1);

        assertTrue(season.doesContainsReferee(referee));

        season.unAssignReferee(referee);
        assertTrue(season.refereesSize()==0);
        assertFalse(season.doesContainsReferee(referee));
    }

    @After
    public void tearDown() throws Exception {
        season = null;
        EntityManager.getInstance().clearAll();
    }

    @AfterClass
    public static void afterClass() {
        DBManager.getInstance().closeConnection();
    }
}