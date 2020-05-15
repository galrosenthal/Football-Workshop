package Domain.Game;

public class Score {
    private Team homeTeam;
    private Team awayTeam;
    private int homeTeamGoalCount;
    private int awayTeamGoalCount;

    public Score(Team firstTeam, Team secondTeam, int firstTeamGoalCount, int secondTeamGoalCount) {
        this.homeTeam = firstTeam;
        this.awayTeam = secondTeam;
        this.homeTeamGoalCount = firstTeamGoalCount;
        this.awayTeamGoalCount = secondTeamGoalCount;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public int getHomeTeamGoalCount() {
        return homeTeamGoalCount;
    }

    public int getAwayTeamGoalCount() {
        return awayTeamGoalCount;
    }
}
