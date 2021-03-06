package Domain.Game;

import DB.DBManager;
import DB.DBManagerForTest;
import Generic.GenericTestAbstract;
import org.junit.*;
import Domain.SystemLogger.SystemLoggerManager;


import static org.junit.Assert.*;

public class LeagueTest extends GenericTestAbstract {

    private League league;



    @Before
    public void setUp() throws Exception {
        this.league = new League("test league name", true);
    }

    @Test
    public void getNameUTest() {
        assertTrue(this.league.getName().equals("test league name"));
    }

    @Test
    public void setNameUTest() {
        this.league.setName("different league name");
        assertTrue(this.league.getName().equals("different league name"));
    }

    @Test
    public void addSeasonITest() {
        assertFalse(league.doesSeasonExists("2020/21"));
        assertNotNull(this.league.addSeason("2020/21"));
        assertTrue(league.doesSeasonExists("2020/21"));
    }

//    @Test
//    public void doesSeasonExistsITest() {
//        assertFalse(league.doesSeasonExists("2020/21"));
//        assertNotNull(this.league.addSeason("2020/21"));
//        assertNotNull(this.league.addSeason("2021/22"));
//        assertTrue(league.doesSeasonExists("2020/21"));
//        assertTrue(league.doesSeasonExists("2021/22"));
//    }

    @After
    public void tearDown() throws Exception {
        this.league = null;
    }

}