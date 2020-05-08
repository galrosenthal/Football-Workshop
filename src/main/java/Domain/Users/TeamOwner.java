package Domain.Users;

import Domain.Game.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamOwner extends Role {

    private List<Team> ownedTeams;
    private SystemUser appointedOwner; /** The team owner who appointed -this- team owner */

    public TeamOwner(SystemUser systemUser) {
        super(RoleTypes.TEAM_OWNER,systemUser);
        ownedTeams = new ArrayList<>();
    }


    public List<Team> getOwnedTeams() {
        return ownedTeams;
    }

//    public boolean addPplToTeam(Role regUser)
//    {
//        if(regUser != null)
//        {
//            switch (regUser.type){
//                case PLAYER:
//                    ownedTeams.addTeamPlayer(this,regUser);
//                    break;
//                case COACH:
//                    ownedTeam.addTeamCoach(this,regUser);
//                    break;
//                case TEAM_MANAGER:
//                    ownedTeam.addTeamManager(this,regUser);
//                    break;
//            }
//        }
//
//        return false;
//    }

    public boolean addTeamToOwn(Team teamToOwn)
    {
        if(!ownedTeams.contains(teamToOwn))
        {
            ownedTeams.add(teamToOwn);
            return true;
        }
        return false;
    }

    public boolean removeTeamOwned(Team team){
        return this.ownedTeams.remove(team);
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

    /**
     * Get the SystemUser who appointed this team owner.
     * @return the SystemUser who appointed this team owner.
     */
    public SystemUser getAppointedOwner() {
        return appointedOwner;
    }

    /**
     * Set the SystemUser who appointed this team owner.
     * @param appointedOwner SystemUser to set as the appointing team owner.
     * @return true if successfully seted.
     */
    public boolean setAppointedOwner(SystemUser appointedOwner) {

        if(appointedOwner != null){
            this.appointedOwner = appointedOwner;
            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamOwner)) return false;
        TeamOwner teamOwner = (TeamOwner) o;
        return this.getSystemUser().equals(teamOwner.getSystemUser());
    }

    @Override
    public String toString() {
        return "TeamOwner{"+ this.getSystemUser().getName() +" }";
    }
}
