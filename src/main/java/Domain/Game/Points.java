package Domain.Game;

public class Points {
    private Team homeTeam;
    private Team awayTeam;
    private int homeTeamPoints;
    private int awayTeamPoints;

    public Points(Team firstTeam, Team secondTeam, int firstTeamPoints, int secondTeamPoints) {
        this.homeTeam = firstTeam;
        this.awayTeam = secondTeam;
        this.homeTeamPoints = firstTeamPoints;
        this.awayTeamPoints = secondTeamPoints;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public int getHomeTeamPoints() {
        return homeTeamPoints;
    }

    public int getAwayTeamPoints() {
        return awayTeamPoints;
    }
}


