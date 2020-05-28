package Domain.Users;

import Domain.Game.Team;

public class BelongToTeam {
    private Team teamBelongsTo;
    private PartOfTeam assetOfTheTeam;
    private String teamJob;

    public String getTeamJob() {
        return teamJob;
    }

    public void setTeamJob(String teamJob) {
        this.teamJob = teamJob;
    }

    public BelongToTeam(Team teamBelongsTo, PartOfTeam assetOfTheTeam) {
        this.teamBelongsTo = teamBelongsTo;
        this.assetOfTheTeam = assetOfTheTeam;
    }


    public Team getTeamBelongsTo() {
        return teamBelongsTo;
    }

    public void setTeamBelongsTo(Team teamBelongsTo) {
        this.teamBelongsTo = teamBelongsTo;
    }

    public PartOfTeam getAssetOfTheTeam() {
        return assetOfTheTeam;
    }

    public void setAssetOfTheTeam(PartOfTeam assetOfTheTeam) {
        this.assetOfTheTeam = assetOfTheTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BelongToTeam)) return false;
        BelongToTeam anotherConnection = (BelongToTeam) o;
        return checkTeamsAreEquals(teamBelongsTo, anotherConnection.teamBelongsTo) &&
                assetOfTheTeam.equals(anotherConnection.assetOfTheTeam);
    }

    /**
     * Checks if the teams of both instance of this Class are equals
     * @param teamBelongsTo current instance team
     * @param anotherTeamBelongsTo other instance team
     * @return
     */
    private boolean checkTeamsAreEquals(Team teamBelongsTo, Team anotherTeamBelongsTo) {
        if(teamBelongsTo.getTeamName().equals(anotherTeamBelongsTo.getTeamName()))
        {
            if(teamBelongsTo.getAllAssets().size() == anotherTeamBelongsTo.getAllAssets().size() &&
                teamBelongsTo.getTeamOwners().size() == anotherTeamBelongsTo.getTeamOwners().size() &&
                teamBelongsTo.getTeamPlayers().size() == anotherTeamBelongsTo.getTeamPlayers().size() &&
                teamBelongsTo.getTeamCoaches().size() == anotherTeamBelongsTo.getTeamCoaches().size() &&
                teamBelongsTo.getTeamManagers().size() == anotherTeamBelongsTo.getTeamManagers().size() &&
                teamBelongsTo.getStatus().equals(anotherTeamBelongsTo.getStatus()) &&
                teamBelongsTo.getStadiums().size() == anotherTeamBelongsTo.getStadiums().size() &&
                teamBelongsTo.getSeasons().size() == anotherTeamBelongsTo.getSeasons().size())
            {
                return true;
            }
        }
        return false;

    }

}
