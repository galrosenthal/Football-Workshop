package Domain.Game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;

import static org.junit.Assert.*;

public class SeasonTest {

    private Season season;

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

    @After
    public void tearDown() throws Exception {
        season = null;
    }
}