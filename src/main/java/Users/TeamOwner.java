package Users;

import Game.Team;

public class TeamOwner extends Registered {

    private Team ownedTeam;

    public TeamOwner(String type, String username, String pass, String name, Team ownedTeam) {
        super(RegisteredTypes.TEAM_OWNER, username, pass, name);
        this.ownedTeam = ownedTeam;
    }

    public boolean addPplToTeam(Registered regUser)
    {
        if(regUser != null)
        {
            if(isUserPartOfTeam(regUser))
            {
                ownedTeam.addTeamStaff(regUser);
            }
        }

        return false;
    }

    private boolean isUserPartOfTeam(Registered regUser) {
        if(regUser != null)
        {
            if(regUser.type == RegisteredTypes.COACH ||
                    regUser.type == RegisteredTypes.PLAYER ||
                    regUser.type == RegisteredTypes.TEAM_MANAGER)
            {
                return true;
            }
        }
        return false;
    }
}
