package Domain.Users;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Game.League;
import Domain.Game.Season;
import Domain.Game.SeasonStub;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RefereeTest {

    private Referee referee;

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.startTest();
        DBManagerForTest.startConnection();    }

    @Before
    public void setUp() {
        this.referee = new Referee(new SystemUser("username", "name"), RefereeQualification.MAIN_REFEREE, true);
    }

    @Test
    public void getTrainingUTest() {
        assertTrue(this.referee.getTraining() == RefereeQualification.MAIN_REFEREE);
    }

    @Test
    public void assignToSeasonUTest() {
        List<Season> seasonArrayList = assign2SeasonsStubs();
        List<Season> seasons = referee.getSeasons();
        assertTrue(seasons.size() == 2);
        assertTrue(seasons.contains(seasonArrayList.get(0)));
        assertTrue(seasons.contains(seasonArrayList.get(1)));
    }

    private List<Season> assign2SeasonsStubs() {
        Season season1 = new SeasonStub(new League("noName1"), "2020/21");
        Season season2 = new SeasonStub(new League("noName2"), "2020/21");
        referee.assignToSeason(season1);
        referee.assignToSeason(season2);
        ArrayList<Season> seasonArrayList = new ArrayList<>();
        seasonArrayList.add(season1);
        seasonArrayList.add(season2);
        return seasonArrayList;
    }

    @Test
    public void unAssignFromAllSeasonsUTest() {
        List<Season> seasonArrayList = assign2SeasonsStubs();
        List<Season> seasons = referee.getSeasons();
        assertTrue(seasons.size() == 2);
        referee.unAssignFromAllSeasons();
        seasons = referee.getSeasons();
        assertTrue(seasons.size() == 0);
        assertFalse(seasonArrayList.get(0).doesContainsReferee(referee));
    }

    @Test
    public void assignToSeasonITest() {
        List<Season> seasonArrayList = assign2Seasons();
        List<Season> seasons = referee.getSeasons();
        assertTrue(seasons.size() == 2);
        assertTrue(seasons.contains(seasonArrayList.get(0)));
        assertTrue(seasons.contains(seasonArrayList.get(1)));
    }

    private List<Season> assign2Seasons() {
        Season season1 = new Season(new League("noName1"), "2020/21");
        Season season2 = new Season(new League("noName2"), "2020/21");
        referee.assignToSeason(season1);
        referee.assignToSeason(season2);
        season1.assignReferee(referee);
        season2.assignReferee(referee);
        ArrayList<Season> seasonArrayList = new ArrayList<>();
        seasonArrayList.add(season1);
        seasonArrayList.add(season2);
        return seasonArrayList;
    }

    @Test
    public void unAssignFromAllSeasonsITest() {
        List<Season> seasonArrayList = assign2Seasons();

        seasonArrayList.get(0).getReferees();

        List<Season> seasons = referee.getSeasons();
        assertTrue(seasons.size() == 2);
        referee.unAssignFromAllSeasons();
        seasons = referee.getSeasons();
        assertTrue(seasons.size() == 0);
        assertFalse(seasonArrayList.get(0).doesContainsReferee(referee));
        seasonArrayList.get(0).getReferees();
    }


    @After
    public void tearDown() {
        this.referee = null;
        EntityManager.getInstance().clearAll();
    }

    @AfterClass
    public static void afterClass() {
        DBManager.getInstance().closeConnection();
    }
}