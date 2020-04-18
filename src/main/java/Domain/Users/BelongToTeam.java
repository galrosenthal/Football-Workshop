package Domain.Users;

import Domain.Game.Team;

import java.util.Objects;

public class BelongToTeam {
    Team teamBealongsTo;
    PartOfTeam assetOfTheTeam;

    public BelongToTeam(Team teamBealongsTo, PartOfTeam assetOfTheTeam) {
        this.teamBealongsTo = teamBealongsTo;
        this.assetOfTheTeam = assetOfTheTeam;
    }

    public Team getTeamBealongsTo() {
        return teamBealongsTo;
    }

    public void setTeamBealongsTo(Team teamBealongsTo) {
        this.teamBealongsTo = teamBealongsTo;
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
        return teamBealongsTo.equals(anotherConnection.teamBealongsTo) &&
                assetOfTheTeam.equals(anotherConnection.assetOfTheTeam);
    }

}
