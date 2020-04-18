package Domain.Game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LeagueTest {

    private League league;

    @Before
    public void setUp() throws Exception {
        this.league = new League("test league name");
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
        assertTrue(this.league.addSeason("2020/21"));
        assertTrue(league.doesSeasonExists("2020/21"));
    }

    @Test
    public void doesSeasonExistsITest() {
        assertFalse(league.doesSeasonExists("2020/21"));
        assertTrue(this.league.addSeason("2020/21"));
        assertTrue(this.league.addSeason("2021/22"));
        assertTrue(league.doesSeasonExists("2020/21"));
        assertTrue(league.doesSeasonExists("2021/22"));
    }

    @After
    public void tearDown() throws Exception {
        this.league = null;
    }
}