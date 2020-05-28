package Domain.GameLogger;

import Domain.Game.Team;
import Domain.Users.Player;

public class Goal extends Event {
    private Team scoringTeam;
    private Team scoredOnTeam;
    private Player playerScored;


    public Goal(Team scoringTeam, Team scoredOnTeam, Player playerScored, int goalMinute) {
        super(goalMinute);
        this.scoringTeam = scoringTeam;
        this.scoredOnTeam = scoredOnTeam;
        this.playerScored = playerScored;
    }

    public Team getScoringTeam() {
        return scoringTeam;
    }

    public Team getScoredOnTeam() {
        return scoredOnTeam;
    }


    @Override
    public String toString() {
        return super.toString()+" Goal scoringTeam=" + scoringTeam.getTeamName() + ", scoredOnTeam=" + scoredOnTeam.getTeamName() +
                ", playerScored=" + playerScored.getAssetName()+".";
    }
}