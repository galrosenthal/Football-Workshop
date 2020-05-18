package Domain.Logger;

import Domain.Game.Team;

public class Goal extends Event {
    private Team scoringTeam;
    private Team scoredOnTeam;
    //todo:Add Player who scored


    public Goal(Team scoringTeam, Team scoredOnTeam, int goalMinute) {
        super(goalMinute);
        this.scoringTeam = scoringTeam;
        this.scoredOnTeam = scoredOnTeam;
    }

    public Team getScoringTeam() {
        return scoringTeam;
    }

    public Team getScoredOnTeam() {
        return scoredOnTeam;
    }


    @Override
    public String toString() {
        return "Goal scoringTeam=" + scoringTeam.getTeamName() + ", scoredOnTeam=" + scoredOnTeam.getTeamName() +" "+super.toString();
    }
}