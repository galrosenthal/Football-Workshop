package Domain.Game;

/**
 * Class the represents the score of a game.
 */
public class Score {
    private Team homeTeam; /** home team */
    private Team awayTeam; /** away team */
    private int homeTeamGoalCount; /** how many goals the home team scored */
    private int awayTeamGoalCount; /** how many goals the away team scored */

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
