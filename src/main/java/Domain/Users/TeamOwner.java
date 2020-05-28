package Domain.Users;

import Domain.Alert;
import Domain.EntityManager;
import Domain.Game.Team;
import Domain.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamOwner extends Role implements Subject {

    //    private List<Team> ownedTeams;
//    private SystemUser appointedOwner; /** The team owner who appointed -this- team owner */
    private HashMap<Team, SystemUser> teamsAndAppointers;

    /**
     * Constructor
     *
     * @param systemUser - SystemUser - The system user to add the new role to
     * @param addToDB    - boolean - Whether to add the new role to the database
     */
    public TeamOwner(SystemUser systemUser, boolean addToDB) {
        super(RoleTypes.TEAM_OWNER, systemUser, addToDB);
        teamsAndAppointers = new HashMap<>();
    }


    public List<Team> getOwnedTeams() {
        List<Team> allTeams = new ArrayList<Team>(teamsAndAppointers.keySet());
        return allTeams;
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

    public boolean addTeamToOwn(Team teamToOwn, SystemUser appointer) {
        if (teamToOwn == null || appointer == null) {
            return false;
        }
        if (!getOwnedTeams().contains(teamToOwn)) {
            teamsAndAppointers.put(teamToOwn, appointer);
            return true;
        }
        return false;
    }

    public boolean removeTeamOwned(Team team) {
        return this.teamsAndAppointers.remove(team) != null;
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
     *
     * @return the SystemUser who appointed this team owner.
     */
    public SystemUser getAppointedOwner(Team ownedTeam) {
        return teamsAndAppointers.get(ownedTeam);
    }

    /**
     * Set the SystemUser who appointed this team owner.
     *
     * @param appointedOwner SystemUser to set as the appointing team owner.
     * @return true if successfully seted.
     */
    public boolean setAppointedOwner(Team teamToChangeAppointer, SystemUser appointedOwner) {

        if (appointedOwner != null && teamToChangeAppointer != null) {
            teamsAndAppointers.put(teamToChangeAppointer, appointedOwner);
            /*update db*/
            EntityManager.getInstance().setTeamOwnerAppointed(this, teamToChangeAppointer, appointedOwner.getUsername());
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
        return "TeamOwner{" + this.getSystemUser().getName() + " }";
    }


    /*notify teamOwners  and TeamMangers and systemAdmins - close team or reopen team*/
    public void closeReopenTeam(Team team, String closeOrReopen) {
        List<TeamOwner> teamOwners = new ArrayList<>();
        List<TeamManager> teamManagers = new ArrayList<>();
        EntityManager entityManager = EntityManager.getInstance();
        List<SystemAdmin> SystemAdmins = entityManager.getSystemAdmins();
        List<SystemUser> systemUsers = new ArrayList<>();
        teamOwners.addAll(team.getTeamOwners());
        teamManagers.addAll(team.getTeamManagers());
        for (int i = 0; i < teamOwners.size(); i++) {
            systemUsers.add(teamOwners.get(i).getSystemUser());
        }
        for (int i = 0; i < teamManagers.size(); i++) {
            systemUsers.add(teamManagers.get(i).getSystemUser());
        }
        for (int i = 0; i < SystemAdmins.size(); i++) {
            systemUsers.add(SystemAdmins.get(i).getSystemUser());
        }
        String alert = team.getTeamName() + " has been " + closeOrReopen;
        notifyObserver(systemUsers, alert);
    }


    /*notify Team Owner - removal */
    public void removeTeamOwnerNotify(TeamOwner teamOwner, Team team) {
        List<SystemUser> systemUsers = new ArrayList<>();
        systemUsers.add(teamOwner.getSystemUser());
        String alert = teamOwner.getSystemUser().getUsername() + " has been remove from been owner in" + team.getTeamName();
        notifyObserver(systemUsers, alert);
    }

    @Override
    public void notifyObserver(List<SystemUser> systemUsers, String alert) {
        Alert alertInstance = Alert.getInstance();
        alertInstance.update(systemUsers, alert);
    }
}
