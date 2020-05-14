package Domain.Game;

import Domain.Users.Referee;

import java.util.Date;
import java.util.List;

public class ScheduleMatch {
    private Team homeTeam;
    private Team awayTeam;
    private Stadium stadium;
    private Date matchDate;
    private List<Referee> referees;

    public ScheduleMatch(Team homeTeam, Team awayTeam, Stadium stadium, Date matchDate, List<Referee> referees) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.stadium = stadium;
        this.matchDate = matchDate;
        this.referees = referees;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public List<Referee> getReferees() {
        return referees;
    }
}
