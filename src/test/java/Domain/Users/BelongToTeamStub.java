package Domain.Users;

import Domain.Game.Team;

public class BelongToTeamStub extends BelongToTeam{

    @Override
    public String getTeamJob() {
        return super.getTeamJob();
    }

    @Override
    public void setTeamBelongsTo(Team teamBelongsTo) {
        super.setTeamBelongsTo(teamBelongsTo);
    }

    public BelongToTeamStub(Team teamBelongsTo, PartOfTeam assetOfTheTeam) {
        super(teamBelongsTo, assetOfTheTeam);
    }
}
