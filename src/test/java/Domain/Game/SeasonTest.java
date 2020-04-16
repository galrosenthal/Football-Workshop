package Domain.Game;

import org.junit.Test;

import java.time.Year;

import static org.junit.Assert.*;

public class SeasonTest {

    @Test
    public void getYear() {
        Season season = new Season(new League("noName"),"2020/21");
        assertTrue(season.getYear().equals(Year.parse("2021")));
    }

    @Test
    public void getTeams() {
    }

    @Test
    public void areGoodYears() {
        assertTrue(Season.isGoodYearsFormat("2020/21"));
        assertFalse(Season.isGoodYearsFormat("2020/211"));
        assertFalse(Season.isGoodYearsFormat("2022/21"));
        assertFalse(Season.isGoodYearsFormat("20das1"));
    }
}