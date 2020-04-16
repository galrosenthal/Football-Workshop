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

    @After
    public void tearDown() throws Exception {
        this.league = null;
    }
}