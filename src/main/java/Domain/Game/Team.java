package Domain.Game;

import Domain.EntityManager;
import Domain.Exceptions.*;
import Domain.Users.*;
import Service.UIController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

    private List<Season> seasons;

    public List<TeamOwner> getTeamOwners() {
        return teamOwners;
    }

    public Team(Team anotherTeam)
    {
        teamCoaches = new ArrayList<>(anotherTeam.teamCoaches);
        teamManagers = new ArrayList<>(anotherTeam.teamManagers);
        teamOwners = new ArrayList<>(anotherTeam.teamOwners);
        teamPlayers = new ArrayList<>(anotherTeam.teamPlayers);
        stadiums = new ArrayList<>(anotherTeam.stadiums);
        teamName = new String(anotherTeam.teamName);

    }

    public Team() {
        teamOwners = new ArrayList<>();
        teamPlayers = new ArrayList<>();
        teamCoaches = new ArrayList<>();
        teamManagers = new ArrayList<>();
        stadiums = new ArrayList<>();
        seasons = new ArrayList<>();
    }
    /**
     * A method that returns a string containing all team information for display
     * @return string containing all team information.
     */
    public String DisplayTeam(){
        String output = "Team Name:" + teamName + "\n" +
                        "Team Coaches:";



        return output;
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
            boolean addedPlayer = teamPlayers.add((Player)teamPlayer);
            if(addedPlayer)
            {
                if( ((Player) teamPlayer).addTeam(this))
                {
                    return true;
                }
                else
                {
                    teamPlayers.remove((Player)teamPlayer);
                    return false;
                }
            }
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
            return teamCoaches.add((Coach) coach);
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

    public boolean addTeamOwner(Role teamOwner)
    {
        if(teamOwner == null){
            return false;
        }
        if(teamOwner.getType() == RoleTypes.TEAM_OWNER){
            return teamOwners.add((TeamOwner)teamOwner);
        }

        return false;
    }

    public boolean removeTeamOwner(TeamOwner teamOwner){
        if(!teamOwners.contains(teamOwner)){
            return false;
        }

        return teamOwners.remove(teamOwner);
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
                this.teamOwners.size() == team.teamOwners.size() &&
                this.teamCoaches.size() == team.teamCoaches.size() &&
                this.teamManagers.size() == team.teamManagers.size() &&
                this.stadiums.size() == team.stadiums.size() &&
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
                "teamPlayers=" + teamPlayersToString() +
                ", teamCoaches=" + teamCoachesToString() +
                ", teamManagers=" + teamManagersToString() +
                ", teamOwners=" + teamOwnersToString() +
                '}';
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
     * Finds the season is now playing and returns it
     * @return the current season
     */
    public Season getCurrentSeason(){
        Season currentSeason;

        if(seasons.size() == 0){
            return null;
        }
        currentSeason = seasons.get(0);
        for (Season s: seasons){
            if(s.getYear().isAfter(currentSeason.getYear())){
                currentSeason = s;
            }
        }

        return  currentSeason;
    }

    public  boolean addSeason(Season season){
        if(!seasons.contains(season)){
            seasons.add(season);
            return true;
        }

        return false;
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

    /**
     * @param assetName
     * @param teamOwner
     * @param assetType
     * @return
     * @throws UserNotFoundException
     * @throws StadiumNotFoundException
     */
    public boolean addAsset(String assetName, TeamOwner teamOwner, TeamAsset assetType)
            throws UserNotFoundException,StadiumNotFoundException {

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
        else
        {
            teamAsset = EntityManager.getInstance().getStadium(assetName);
            if(teamAsset == null)
            {
                throw new StadiumNotFoundException("Could not find a stadium by the given name" + assetName);
            }
        }


        teamAsset.addAllProperties();

        return teamAsset.addTeam(this,teamOwner);
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
//            e.printStackTrace();
            return null;
        }


    }


}
