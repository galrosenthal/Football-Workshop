package Domain.Users;

import Domain.Game.Team;

public class TeamOwner extends Registered {

    private Team ownedTeam;

    public TeamOwner(String username, String pass, String name) {
        super(RegisteredTypes.TEAM_OWNER, username, pass, name);
    }

    public boolean addPplToTeam(Registered regUser)
    {
        if(regUser != null)
        {
            switch (regUser.type){
                case PLAYER:
                    ownedTeam.addTeamPlayer(this,regUser);
                    break;
                case COACH:
                    ownedTeam.addTeamCoach(this,regUser);
                    break;
                case TEAM_MANAGER:
                    ownedTeam.addTeamManager(this,regUser);
                    break;
            }
        }

        return false;
    }

    public boolean addTeamToOwn(Team teamToOwn)
    {
        if(this.ownedTeam == null)
        {
            ownedTeam = teamToOwn;
            return true;
        }
        return false;
    }

//    /**
////     * Checks whether or not the user is part of the team asset or not,
////     * @param regUser
////     * @return
////     */
////    private boolean isUserPartOfTeamAssets(Registered regUser) {
////        if(regUser != null)
////        {
////            if(regUser.type == RegisteredTypes.COACH ||
////                    regUser.type == RegisteredTypes.PLAYER ||
////                    regUser.type == RegisteredTypes.TEAM_MANAGER)
////            {
////                return true;
////            }
////        }
////        return false;
////    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamOwner)) return false;
        TeamOwner teamOwner = (TeamOwner) o;
        return ownedTeam.equals(teamOwner.ownedTeam);
    }

    @Override
    public String toString() {
        return "TeamOwner{"+ this.getName() +" }";
    }
}
