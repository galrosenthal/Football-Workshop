package Domain.Users;

import Domain.Game.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamOwner extends Role {

    private List<Team> ownedTeams;

    public TeamOwner(SystemUser systemUser) {
        //TODO:
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
        if(!isOwnTeam())
        {
            ownedTeams.add(teamToOwn);
            return true;
        }
        return false;
    }

    public boolean isOwnTeam(){
        if (this.ownedTeams == null){
            return false;
        }

        return true;
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
        return ownedTeams.equals(teamOwner.ownedTeams) &&
                this.getSystemUser().getName().equals(teamOwner.getSystemUser().getName());
    }

    @Override
    public String toString() {
        return "TeamOwner{"+ this.getSystemUser().getName() +" }";
    }
}
