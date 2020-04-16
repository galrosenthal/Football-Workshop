package Domain.Game;

import Domain.Controllers.TeamController;
import Domain.EntityManager;
import Domain.Exceptions.*;
import Domain.Users.*;
import Service.UIController;

import javax.management.relation.RoleNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This Class is representing a Football Team
 */
public class Team {

    private String teamName;
    private List<Player> teamPlayers;
    private List<Coach> teamCoaches;
    private List<TeamManager> teamManagers;
    private List<TeamOwner> teamOwners;
    private List<Stadium> stadiums;


    public List<TeamOwner> getTeamOwners() {
        return teamOwners;
    }

    public Team() {
        teamOwners = new ArrayList<>();
        teamPlayers = new ArrayList<>();
        teamCoaches = new ArrayList<>();
        teamManagers = new ArrayList<>();
        stadiums = new ArrayList<>();
    }


    /**
     * Add a Player to the team,
     * can only be requested by the Team Owner
     * @param teamPlayer a register user that represents a player
     * @return true if the player added successfully to the Team.
     */
    public boolean addTeamPlayer(TeamOwner townr, Role teamPlayer)
    {
        if(!teamOwners.contains(townr))
        {
            return false;
        }
        if(teamPlayer.getType() == RoleTypes.PLAYER)
        {
            return teamPlayers.add((Player)teamPlayer);
        }
        return false;
    }

    public boolean addTeamCoach(TeamOwner townr, Role coach)
    {
        if(!teamOwners.contains(townr))
        {
            return false;
        }
        if(coach.getType() == RoleTypes.COACH)
        {
            return teamCoaches.add((Coach)coach);
        }
        return false;
    }


    public boolean addTeamManager(TeamOwner townr,Role teamManager)
    {
        if(!teamOwners.contains(townr))
        {
            return false;
        }
        if(teamManager.getType() == RoleTypes.TEAM_MANAGER)
        {
            return teamManagers.add((TeamManager) teamManager);
        }
        return false;
    }

    public boolean addTeamOwner(TeamOwner townr, TeamOwner teamOwner)
    {
        if(teamOwners.contains(townr))
        {
            return false;
        }

        return teamOwners.add(teamOwner);
    }

    public List<Player> getTeamPlayers() {
        return teamPlayers;
    }

    public List<Coach> getTeamCoaches() {
        return teamCoaches;
    }

    public List<TeamManager> getTeamManagers() {
        return teamManagers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return this.teamPlayers.size() == team.teamPlayers.size() &&
                this.teamCoaches.size() == team.teamCoaches.size() &&
                this.teamManagers.size() == team.teamManagers.size() &&
                checkPlayerListEquals(this.teamPlayers,team.teamPlayers) &&
                checkCoachListEquals(this.teamCoaches,team.teamCoaches) &&
                checkManagersListEquals(this.teamManagers,team.teamManagers) &&
                checkTeamOwnersrListEquals(this.teamOwners,team.teamOwners);
    }

    /**
     * Iterate over all the teamManagers and check if the other team has all of them.
     * @param teamManagers a list of this team managers
     * @param anotherTeamOfManagers a list of the other team managers
     * @return true if the lists are equal
     */
    private boolean checkManagersListEquals(List<TeamManager> teamManagers, List<TeamManager> anotherTeamOfManagers) {
        for (TeamManager tm : teamManagers)
        {
            if(!anotherTeamOfManagers.contains(tm))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Iterate over all the teamCoaches and check if the other team has all of them.
     * @param teamCoaches a list of this team coaches
     * @param anotherListOfCoaches a list of the other team coaches
     * @return true if the lists are equal
     */
    private boolean checkCoachListEquals(List<Coach> teamCoaches, List<Coach> anotherListOfCoaches) {
        for (Coach c : teamCoaches)
        {
            if(!anotherListOfCoaches.contains(c))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Iterate over all the teamPlayers and check if the other team has all of them.
     * @param currentTeamPlayers a list of this team players
     * @param anotherTeamPlayers a list of the other team players
     * @return true if the lists are equal
     */
    private boolean checkPlayerListEquals(List<Player> currentTeamPlayers, List<Player> anotherTeamPlayers) {
        for(Player p: currentTeamPlayers)
        {
            if(!anotherTeamPlayers.contains(p))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Iterate over all the teamPlayers and check if the other team has all of them.
     * @param currentTeamOwners a list of this team owners
     * @param anotherTeamOwners a list of the other team owners
     * @return true if the lists are equal
     */
    private boolean checkTeamOwnersrListEquals(List<TeamOwner> currentTeamOwners, List<TeamOwner> anotherTeamOwners) {
        for(TeamOwner tw: currentTeamOwners)
        {
            if(!anotherTeamOwners.contains(tw))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Team{" +
                " teamPlayers=" + teamPlayersToString() +
                ", teamCoaches=" + teamCoachesToString() +
                ", teamManagers=" + teamManagersToString() +
                ", teamOwners=" + teamOwnersToString() +
                ", teamStadiums=" + stadiumsToString() +
                '}';
    }

    private String stadiumsToString() {
        StringBuilder stadiumString = new StringBuilder();
        stadiumString.append("Stadiums: \n");

        for (int i = 0; i < stadiums.size(); i++)
        {
            stadiumString.append(i+1).append(". ");
            stadiumString.append(stadiums.get(i).getName());
            stadiumString.append("\n");
        }
        return stadiumString.toString();
    }

    private String teamOwnersToString() {
        StringBuilder teamOwnersString = new StringBuilder();
        teamOwnersString.append("Owners: \n");

        for (int i = 0; i < teamOwners.size(); i++)
        {
            teamOwnersString.append(i+1).append(". ");
            teamOwnersString.append(teamOwners.get(i).getSystemUser().getName());
            teamOwnersString.append("\n");
        }
        return teamOwnersString.toString();
    }

    private String teamManagersToString() {
        StringBuilder teamManagersString = new StringBuilder();
        teamManagersString.append("Managers: \n");

        for (int i = 0; i < teamManagers.size(); i++)
        {
            teamManagersString.append(i+1).append(". ");
            teamManagersString.append(teamManagers.get(i).getSystemUser().getName());
            teamManagersString.append("\n");
        }
        return teamManagersString.toString();
    }

    private String teamCoachesToString() {
        StringBuilder teamCoachesString = new StringBuilder();
        teamCoachesString.append("Coaches: \n");

        for (int i = 0; i < teamCoaches.size(); i++)
        {
            teamCoachesString.append(i+1).append(". ");
            teamCoachesString.append(teamCoaches.get(i).getSystemUser().getName());
            teamCoachesString.append("\n");
        }
        return teamCoachesString.toString();
    }

    private String teamPlayersToString() {
        StringBuilder teamPlayersString = new StringBuilder();
        teamPlayersString.append("Players: \n");

        for (int i = 0; i < teamPlayers.size(); i++)
        {
            teamPlayersString.append(i+1).append(". ");
            teamPlayersString.append(teamPlayers.get(i).getSystemUser().getName());
            teamPlayersString.append("\n");
        }
        return teamPlayersString.toString();
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String testName) {
        this.teamName = testName;
    }

    /**
     *Getter Stadiums
     * @return list of Stadiums
     */
    public List<Stadium> getStadiums() {
        return stadiums;
    }

    /**
     *
     * @param stadium
     * add stadium to team
     * @return true if stadium added successfully!
     */
    public boolean addStadium(Stadium stadium) {
        if (!this.stadiums.contains(stadium)) {
            this.stadiums.add(stadium);
        }
        return true;
    }

    /**
     *
     * @param stadium
     * remove stadium to team
     * @return true if stadium removed successfully!
     */
    public boolean removeStadium(Stadium stadium) {
        if(this.stadiums.contains(stadium))
        {
            this.stadiums.remove(stadium);
            return true;
        }
        return false;
    }

    public List<Asset> getAllAssets() {

        List<Asset> allTeamAssets = new ArrayList<>();
        allTeamAssets.addAll(this.teamPlayers);
        allTeamAssets.addAll(this.teamCoaches);
        allTeamAssets.addAll(this.teamManagers);
        allTeamAssets.addAll(this.stadiums);
        return allTeamAssets;
    }


    public boolean isTeamOwner(TeamOwner teamOwner) {
        if(teamOwner != null && teamOwners.contains(teamOwner))
        {
            return true;
        }
        return false;
    }

    public boolean addAsset(String assetName, TeamOwner teamOwner, TeamAsset assetType)
            throws UserNotFoundException,NoSuchTeamAssetException,StadiumNotFoundException {

        Asset teamAsset;
        SystemUser assetUser;
        if(!assetType.equals(TeamAsset.STADIUM)) {
            assetUser = EntityManager.getInstance().getUser(assetName);
            if (assetUser == null) {
                throw new UserNotFoundException("Could not find user " + assetName);
            }
        }
        else
        {
            //Suppose to add a stadium as an asset
            assetUser = null;
        }

        if(assetType.equals(TeamAsset.PLAYER))
        {
            teamAsset = (Player)assetUser.getRole(RoleTypes.PLAYER);
            if(teamAsset == null)
            {
                Date playerBDate = getPlayerBirthDate();
                teamAsset = new Player(assetUser,playerBDate);
            }
        }
        else if(assetType.equals(TeamAsset.COACH))
        {
            teamAsset = (Coach)assetUser.getRole(RoleTypes.COACH);
            if(teamAsset == null)
            {
                teamAsset = new Coach(assetUser);
            }
        }
        else if(assetType.equals(TeamAsset.TEAM_MANAGER))
        {
            teamAsset = (TeamManager)assetUser.getRole(RoleTypes.TEAM_MANAGER);
            if(teamAsset == null)
            {
                teamAsset = new TeamManager(assetUser);
            }
        }
        else if(assetType.equals(TeamAsset.STADIUM))
        {
            teamAsset = EntityManager.getInstance().getStadium(assetName);
            if(teamAsset == null)
            {
                throw new StadiumNotFoundException("Could not find a stadium by the given name" + assetName);
            }
        }
        else
        {
            throw new NoSuchTeamAssetException("There is no Team Asset named " + assetType);
        }

        teamAsset.addAllProperties();

        return teamAsset.addTeam(this,teamOwner);





        /*if(!assetType.equals(TeamAsset.STADIUM)) {
            SystemUser assetUser = EntityManager.getInstance().getUser(assetName);
            if (assetUser == null) {
                throw new UserNotFoundException("Could not find a user by the given username" + assetName);
            }

            Role getRoleForUser;
            if(assetType.equals(TeamAsset.PLAYER))
            {
                getRoleForUser = assetUser.getRole(RoleTypes.PLAYER);
                Player playerRole;
                if(getRoleForUser == null)
                {
                    Date bday = getPlayerBirthDate();
                    playerRole = new Player(assetUser,bday);
                    if(!playerRole.addProperty(playerRole.fieldJobString)){
                        throw new PropertyAdditionException("Something went wrong trying to add a property");
                    }
                }
                else {
                    playerRole = (Player)getRoleForPlayer;
                    if(teamToAddPlayer.getTeamPlayers().contains(playerRole))
                    {
                        throw new PlayerIsAlreadyInThisTeamException("Player is already part of this team");
                    }
                }

            }
            else if(assetType.equals(TeamAsset.COACH))
            {
                getRoleForUser = assetUser.getRole(RoleTypes.COACH);
                Coach coachRole;
                if(getRoleForUser == null)
                {
                    coachRole = new Coach(assetUser);
                    if(coachRole.addProperty(coachRole.teamJobString))
                    {
                        throw new PropertyAdditionException("Something went wrong trying to add a property");
                    }
                    if(coachRole.addProperty(coachRole.qualificationString))
                    {
                        throw new PropertyAdditionException("Something went wrong trying to add a property");
                    }

                }
                else
                {
                    coachRole = (Coach) getRoleForUser;
                    if(teamToAddCoach.getTeamCoaches().contains(coachRole))
                    {
                        throw new CoachIsAlreadyInThisTeamException("Coach is already in this team");
                    }
                }
            }
            else if(assetType.equals(TeamAsset.TEAM_MANAGER))
            {
                getRoleForUser = assetUser.getRole(RoleTypes.TEAM_MANAGER);
                TeamManager managerRole;
                if(getRoleForUser == null)
                {
                    managerRole = new TeamManager(managerUser);
                }
                else {
                    managerRole = (TeamManager) getRoleForUser;
                    if(teamToAddManager.getTeamManagers().contains(managerRole))
                    {
                        throw new ManagerIsAlreadyInThisTeamException("This Manager is already a manager in this team");
                    }
                }
            }
            else
            {
                throw new NoSuchTeamAssetException("There is no such team asset type");
            }


        }
        else
        {
            Stadium stadiumAsset = EntityManager.getInstance().getStadium(assetName);
            if (stadiumAsset == null) {
                throw new StadiumNotFoundException("Could not find a stadium by the given name" + assetName);
            }
        }*/
    }

    private String getStadiumLocation() {
        UIController.printMessage("Please insert stadium locaion");
        return UIController.receiveString();
    }


    /**
     * Get the player date of birth from the user
     * @return the birth date of the player as java.util.Date
     */
    private static Date getPlayerBirthDate() {
        UIController.printMessage("Please insert Player Birth Date in format dd/MM/yyyy");
        String bDate;
        do {
            bDate = UIController.receiveString();
        }while (!bDate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$"));

        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(bDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }


}
