package Domain.Logger;

import Domain.Game.Team;

public class Goal extends Event {
    private Team scoringTeam;
    private Team scoredOnTeam;
    private int goalMinute;

    public Goal(Team scoringTeam, Team scoredOnTeam, int goalMinute) {
        this.scoringTeam = scoringTeam;
        this.scoredOnTeam = scoredOnTeam;
        this.goalMinute = goalMinute;
    }

    public Team getScoringTeam() {
        return scoringTeam;
    }

    public Team getScoredOnTeam() {
        return scoredOnTeam;
    }

    public int getGoalMinute() {
        return goalMinute;
    }
}
