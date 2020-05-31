package Domain.Game;

import Domain.EntityManager;
import Domain.Exceptions.*;
import Domain.Users.*;
import Service.UIController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This Class is representing a GUI.Football Team
 */
public class Team {

    private String teamName;
    private HashSet<BelongToTeam> playersAndCoaches;
    private List<TeamManager> teamManagers;
    private List<TeamOwner> teamOwners;
    private List<Season> seasons;
    private List<Stadium> stadiums;
    private TeamStatus status;
    private HashMap<TeamManager, List<TeamManagerPermissions>> permissionsPerTeamManager;


    public Team(String teamName, boolean addToDB) {
        this.teamName = teamName;
        teamOwners = new ArrayList<>();
        playersAndCoaches = new HashSet<>();
        teamManagers = new ArrayList<>();
        seasons = new ArrayList<>();
        stadiums = new ArrayList<>();
        status = TeamStatus.OPEN;
        permissionsPerTeamManager = new HashMap<>();
        if(addToDB)
        {
            EntityManager.getInstance().addTeam(this);
        }

    }

    public Team(String teamName,TeamStatus teamStatus ,  boolean addToDB) {
        this.teamName = teamName;
        teamOwners = new ArrayList<>();
        playersAndCoaches = new HashSet<>();
        teamManagers = new ArrayList<>();
        seasons = new ArrayList<>();
        stadiums = new ArrayList<>();
        status = teamStatus;
        permissionsPerTeamManager = new HashMap<>();
        if(addToDB)
        {
            EntityManager.getInstance().addTeam(this);
        }

    }

    public Team(String teamName, TeamOwner to, boolean addToDB) {
        teamOwners = new ArrayList<>();
        teamOwners.add(to);
        playersAndCoaches = new HashSet<>();
        teamManagers = new ArrayList<>();
        stadiums = new ArrayList<>();
        status = TeamStatus.OPEN;
        seasons = new ArrayList<>();
        this.teamName = teamName;
        permissionsPerTeamManager = new HashMap<>();
        if(addToDB)
        {
            EntityManager.getInstance().addTeam(this);
            EntityManager.getInstance().addTeamOwnerToTeam(to, this);
        }

    }

    public Team(Team anotherTeam) {
        teamManagers = new ArrayList<>(anotherTeam.teamManagers);
        teamOwners = new ArrayList<>(anotherTeam.teamOwners);
        stadiums = new ArrayList<>(anotherTeam.stadiums);
        teamName = new String(anotherTeam.teamName);
        playersAndCoaches = new HashSet<>(anotherTeam.playersAndCoaches);
        status = TeamStatus.OPEN;
        seasons = new ArrayList<>();
        permissionsPerTeamManager = new HashMap<>(anotherTeam.permissionsPerTeamManager);

    }

    public List<TeamOwner> getTeamOwners() {
        /*DB*/
        teamOwners = EntityManager.getInstance().getTeamsOwners(this);
        return teamOwners;
    }

    public List<String> getTeamOwnersString() {
        List<String> teamOwnersString = new ArrayList<>();
        /*DB*/
        teamOwners = EntityManager.getInstance().getTeamsOwners(this);
        for (TeamOwner to : teamOwners) {
            teamOwnersString.add(to.toString());
        }

        return teamOwnersString;
    }


    /**
     * Add a Player to the team,
     * can only be requested by the Team Owner
     *
     * @param teamPlayer a register user that represents a player
     * @return true if the player added successfully to the Team.
     */
    public boolean addTeamPlayer(TeamOwner townr, Role teamPlayer) {
        if (status != TeamStatus.OPEN || !(EntityManager.getInstance().isTeamOwner(townr,this))) {
            return false;
        }
        if (teamPlayer.getType() == RoleTypes.PLAYER) {
            return addConnection((Player) teamPlayer);
        }
        return false;
    }

    private boolean addConnection(PartOfTeam teamConnectionAsset) {
        BelongToTeam bg = new BelongToTeam(this, teamConnectionAsset);
        if (playersAndCoaches.contains(bg)) {
            return false;
        }
        playersAndCoaches.add(bg);
        boolean addedPlayerConnection = teamConnectionAsset.addTeamConnection(bg);
        if (addedPlayerConnection) {
            return true;
        } else {
            playersAndCoaches.remove(bg);
            return false;
        }
    }

    public boolean addTeamCoach(TeamOwner townr, Role coach) {
        if (status != TeamStatus.OPEN || !(EntityManager.getInstance().isTeamOwner(townr,this))) {
            return false;
        }
        if (coach.getType() == RoleTypes.COACH) {
            return addConnection((Coach) coach);
        }
        return false;
    }


    public boolean addTeamManager(TeamOwner townr, Role teamManager) {
        if (status != TeamStatus.OPEN || !(EntityManager.getInstance().isTeamOwner(townr,this))) {
            return false;
        }
        if (teamManager.getType() == RoleTypes.TEAM_MANAGER) {
            boolean managerAdded = teamManagers.add((TeamManager) teamManager);
            if (managerAdded) {
                /*already added to db*/
                this.permissionsPerTeamManager.put((TeamManager) teamManager, new ArrayList<>());
                return true;
            }
        }
        return false;
    }

    public boolean addTeamOwner(Role teamOwner) {
        if (teamOwner == null || status != TeamStatus.OPEN) {
            return false;
        }
        if (teamOwner.getType() == RoleTypes.TEAM_OWNER && !teamOwners.contains(teamOwner) &&!(EntityManager.getInstance().isTeamOwner((TeamOwner)teamOwner,this))) {

            /*DB*/
            EntityManager.getInstance().addTeamOwnerToTeam((TeamOwner)teamOwner, this);
            return teamOwners.add((TeamOwner) teamOwner);
        }

        return false;
    }

    public boolean removeTeamOwner(TeamOwner teamOwner) {
        if (!teamOwners.contains(teamOwner) || status != TeamStatus.OPEN || !(EntityManager.getInstance().isTeamOwner(teamOwner,this))) {
            return false;
        }

        teamOwners.remove(teamOwner);
        /*update db*/
       // return EntityManager.getInstance().removeTeamOwner(teamOwner,this);
        return true;
    }

    public List<Player> getTeamPlayers() {
        /*List<Player> allPlayersInTeam = new ArrayList<>();

        for (BelongToTeam po : playersAndCoaches) {
            PartOfTeam teamAssetConnection = po.getAssetOfTheTeam();
            if (teamAssetConnection instanceof Player) {
                allPlayersInTeam.add((Player) teamAssetConnection);
            }
        }

         */
        List<Player> allPlayersInTeam = EntityManager.getInstance().getAllPlayersInTeam(this);
        return allPlayersInTeam;
    }
    public List<Coach> getTeamCoaches() {
        /*
        List<Coach> allPlayersInTeam = new ArrayList<>();
        for (BelongToTeam po : playersAndCoaches) {
            PartOfTeam teamAssetConnection = po.getAssetOfTheTeam();
            if (teamAssetConnection instanceof Coach) {
                allPlayersInTeam.add((Coach) teamAssetConnection);
            }
        }

         */
        List<Coach> allCoachesInTeam =EntityManager.getInstance().getAllCoachesInTeam(this);;
        return allCoachesInTeam;
    }

    public List<TeamManager> getTeamManagers() {
        teamManagers = EntityManager.getInstance().getTeamsManager(this);
        return teamManagers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return team.getTeamName().equals(this.teamName);
//        return this.teamOwners.size() == team.teamOwners.size() &&
//               this.teamManagers.size() == team.teamManagers.size() &&
//                this.stadiums.size() == team.stadiums.size() &&
//                this.playerAndCoaches.keySet().size() == team.playerAndCoaches.keySet().size() &&
//                checkManagersListEquals(this.teamManagers,team.teamManagers) &&
//                checkTeamOwnerListEquals(this.teamOwners,team.teamOwners) &&
//                checkStadiumListEquals(this.stadiums,team.stadiums);

    }

//    /**
//     * Iterate over all the stadiums and check if the other team has all of them.
//     * @param stadiums a list of this stadiums
//     * @param anotherStadiumsList a list of the other team stadiums
//     * @return true if the lists are equal
//     */
//    private boolean checkStadiumListEquals(List<Stadium> stadiums, List<Stadium> anotherStadiumsList) {
//
//        for (Stadium st : stadiums) {
//            if (!anotherStadiumsList.contains(st)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Iterate over all the teamManagers and check if the other team has all of them.
//     * @param teamManagers a list of this team managers
//     * @param anotherTeamOfManagers a list of the other team managers
//     * @return true if the lists are equal
//     */
//    private boolean checkManagersListEquals(List<TeamManager> teamManagers, List<TeamManager> anotherTeamOfManagers) {
//        for (TeamManager tm : teamManagers)
//        {
//            if(!anotherTeamOfManagers.contains(tm))
//            {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Iterate over all the teamCoaches and check if the other team has all of them.
//     * @param teamCoaches a list of this team coaches
//     * @param anotherListOfCoaches a list of the other team coaches
//     * @return true if the lists are equal
//     */
//    private boolean checkCoachListEquals(List<Coach> teamCoaches, List<Coach> anotherListOfCoaches) {
//        for (Coach c : teamCoaches)
//        {
//            if(!anotherListOfCoaches.contains(c))
//            {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    /**
//     * Iterate over all the teamPlayers and check if the other team has all of them.
//     * @param currentTeamPlayers a list of this team players
//     * @param anotherTeamPlayers a list of the other team players
//     * @return true if the lists are equal
//     */
//    private boolean checkPlayerListEquals(List<Player> currentTeamPlayers, List<Player> anotherTeamPlayers) {
//        for(Player p: currentTeamPlayers)
//        {
//            if(!anotherTeamPlayers.contains(p))
//            {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Iterate over all the teamPlayers and check if the other team has all of them.
//     * @param currentTeamOwners a list of this team owners
//     * @param anotherTeamOwners a list of the other team owners
//     * @return true if the lists are equal
//     */
//    private boolean checkTeamOwnerListEquals(List<TeamOwner> currentTeamOwners, List<TeamOwner> anotherTeamOwners) {
//        for(TeamOwner tw: currentTeamOwners)
//        {
//            if(!anotherTeamOwners.contains(tw))
//            {
//                return false;
//            }
//        }
//        return true;
//    }

    @Override
    public String toString() {
        StringBuilder teamString = new StringBuilder();
        teamString.append("Team {\n");
        teamString.append(" teamPlayers=");
        teamString.append(teamPlayersToString());
        teamString.append(", teamCoaches=");
        teamString.append(teamCoachesToString());
        teamString.append(", teamManagers=");
        teamString.append(teamManagersToString());
        teamString.append(", teamOwners=");
        teamString.append(teamOwnersToString());
        teamString.append(", teamStadiums=");
        teamString.append(stadiumsToString());
        teamString.append('}');
        return teamString.toString();
    }

    private String stadiumsToString() {
        StringBuilder stadiumString = new StringBuilder();
        stadiumString.append("Stadiums: \n");

        for (int i = 0; i < stadiums.size(); i++) {
            stadiumString.append(i + 1).append(". ");
            stadiumString.append(stadiums.get(i).getName());
            stadiumString.append("\n");
        }
        return stadiumString.toString();
    }

    private String teamOwnersToString() {
        StringBuilder teamOwnersString = new StringBuilder();
        teamOwnersString.append("Owners: \n");

        for (int i = 0; i < teamOwners.size(); i++) {
            teamOwnersString.append(i + 1).append(". ");
            teamOwnersString.append(teamOwners.get(i).getSystemUser().getName());
            teamOwnersString.append("\n");
        }
        return teamOwnersString.toString();
    }

    private String teamManagersToString() {
        StringBuilder teamManagersString = new StringBuilder();
        teamManagersString.append("Managers: \n");

        for (int i = 0; i < teamManagers.size(); i++) {
            teamManagersString.append(i + 1).append(". ");
            teamManagersString.append(teamManagers.get(i).getSystemUser().getName());
            teamManagersString.append("\n");
        }
        return teamManagersString.toString();
    }

    private String teamCoachesToString() {
        StringBuilder teamCoachesString = new StringBuilder();
        teamCoachesString.append("Coaches: \n");
        List<Coach> teamCoaches = getTeamCoaches();
        for (int i = 0; i < teamCoaches.size(); i++) {
            teamCoachesString.append(i + 1).append(". ");
            teamCoachesString.append(teamCoaches.get(i).getSystemUser().getName());
            teamCoachesString.append("\n");
        }
        return teamCoachesString.toString();
    }

    private String teamPlayersToString() {
        StringBuilder teamPlayersString = new StringBuilder();
        teamPlayersString.append("Players: \n");

        List<Player> teamPlayers = getTeamPlayers();
        for (int i = 0; i < teamPlayers.size(); i++) {
            teamPlayersString.append(i + 1).append(". ");
            teamPlayersString.append(teamPlayers.get(i).getSystemUser().getName());
            teamPlayersString.append("\n");
        }
        return teamPlayersString.toString();
    }

    public String getTeamName() {
        return this.teamName;
    }

    public boolean setTeamName(String testName) {
        /*update db*/
         if(EntityManager.getInstance().updateTeamName(this.teamName,testName))
         {
             this.teamName = testName;
             return true;
         }
         return false;
    }

    public TeamStatus getStatus() {
        return status;
    }

    public void setStatus(TeamStatus status) {
        /*update db*/
        EntityManager.getInstance().updateTeamStatus(this.teamName,status);
        this.status = status;
    }

    /**
     * Getter Stadiums
     *
     * @return list of Stadiums
     */
    public List<Stadium> getStadiums() {
        stadiums = EntityManager.getInstance().getStadiumsInTeam(this);
        return stadiums;
    }

    /**
     * @param stadium add stadium to team
     * @return true if stadium added successfully!
     */
    public boolean addStadium(Stadium stadium) {
        if (!this.stadiums.contains(stadium)) {

            if (EntityManager.getInstance().addStadium(stadium, this)) {
                this.stadiums.add(stadium);
                return true;
            }


        }
        return false;
    }

    /**
     * @param stadium remove stadium to team
     * @return true if stadium removed successfully!
     */
    public boolean removeStadium(Stadium stadium) {
        if (this.stadiums.contains(stadium)) {
            this.stadiums.remove(stadium);
            return true;
        }
        if(getStadiums().contains(stadium))
        {
            this.stadiums.remove(stadium);
            EntityManager.getInstance().removeStadiumFromTeam(stadium , this);
        }
        return false;
    }

    /**
     * Each team has a lot of Assets {@link TeamAsset},
     *
     * @return a list of all the team assets
     */
    public List<Asset> getAllAssets() {

        List<Asset> allTeamAssets = new ArrayList<>();
        allTeamAssets.addAll(getTeamPlayers());
        allTeamAssets.addAll(getTeamCoaches());
        allTeamAssets.addAll(getTeamManagers());
        allTeamAssets.addAll(getStadiums());
        return allTeamAssets;
    }

    /**
     * Checks whether or not the teamOwner is owner if this team
     *
     * @param teamOwner the team owner to validate with
     * @return true if teamOwner is this team wner.
     */
    public boolean isTeamOwner(TeamOwner teamOwner) {
        if (teamOwner != null && teamOwners.contains(teamOwner)) {
            return true;
        }
        /*check in db*/
        if(teamOwner == null)
        {
            return false;
        }
        return EntityManager.getInstance().isTeamOwner(teamOwner, this);
        //   return false;
    }

    /**
     * General Function to add an asset to the team,
     * receives an assetName a teamOwner and an AssetType
     *
     * @param assetName the assetName suppose to be the Username of the {@link SystemUser} or the name of the {@link Stadium}
     * @param teamOwner the {@link TeamOwner} of this team
     * @param assetType the {@link TeamAsset} of the user to add to the team
     * @return true if the asset was added successfully
     * @throws UserNotFoundException
     * @throws StadiumNotFoundException
     */
    public boolean addAsset(String assetName, TeamOwner teamOwner, TeamAsset assetType)
            throws UserNotFoundException, StadiumNotFoundException {

        Asset teamAsset;
        SystemUser assetUser;
        if (!assetType.equals(TeamAsset.STADIUM)) {
            assetUser = EntityManager.getInstance().getUser(assetName);
            if (assetUser == null) {
                throw new UserNotFoundException("Could not find user " + assetName);
            }
        } else {
            //Suppose to add a stadium as an asset
            assetUser = null;
        }

        if (assetType.equals(TeamAsset.PLAYER)) {
            teamAsset = (Player) assetUser.getRole(RoleTypes.PLAYER);
            if (teamAsset == null) {
                Date playerBDate = getPlayerBirthDate();
                teamAsset = new Player(assetUser, playerBDate, true);
            }
        } else if (assetType.equals(TeamAsset.COACH)) {
            teamAsset = (Coach) assetUser.getRole(RoleTypes.COACH);
            if (teamAsset == null) {
                teamAsset = new Coach(assetUser, true);
            }
        } else if (assetType.equals(TeamAsset.TEAM_MANAGER)) {
            teamAsset = (TeamManager) assetUser.getRole(RoleTypes.TEAM_MANAGER);
            if (teamAsset == null) {
                teamAsset = new TeamManager(assetUser, true);
            }
        } else {
            /**
             * assetType Should be TeamAsset.STADIUM
             */
            teamAsset = EntityManager.getInstance().getStadium(assetName);
            if (teamAsset == null) {
                throw new StadiumNotFoundException("Could not find a stadium by the given name" + assetName);
            }
        }

        if (!teamAsset.addTeam(this, teamOwner)) {
            return false;
        }
        /*TODO*/

        return teamAsset.addAllProperties(this);

    }


    /**
     * Get the player date of birth from the user
     *
     * @return the birth date of the player as java.util.Date
     */
    private Date getPlayerBirthDate() {
        String bDate;
        do {
            bDate = UIController.receiveDate("Please insert Player Birth Date in format dd/MM/yyyy");
        } while (!bDate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$"));

        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(bDate);
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }


    }


    /**
     * Finds the season is now playing and returns it
     *
     * @return the current season
     */
    private Season getCurrentSeason() {
        Season currentSeason;
        /*DB*/
        seasons = EntityManager.getInstance().getAllSeasonInTeam(this);
        if (seasons.size() == 0) {
            return null;
        }
        currentSeason = seasons.get(0);
        for (Season s : seasons) {
            if (s.getYear().isAfter(currentSeason.getYear())) {
                currentSeason = s;
            }
        }

        return currentSeason;
    }

    /**
     * Finds the seasons is now playing, and returns all the seasons in the same year
     *
     * @return the current seasons
     */
    public List<Season> getCurrentSeasons() {
        List<Season> currentSeasons = new ArrayList<>();
        Season currentSeason = getCurrentSeason();
        /*DB - already in getCurrentSeason()*/
        if (seasons.size() == 0) {
            return null;
        }
        for (Season s : seasons) {
            if (s.getYear().equals(currentSeason.getYear())) {
                currentSeasons.add(s);
            }
        }
        return currentSeasons;
    }

    public List<Season> getSeasons() {
        /*DB*/
        seasons = EntityManager.getInstance().getAllSeasonInTeam(this);
        return seasons;
    }

    public boolean addSeason(Season season) {
        /*DB*/
        if (!seasons.contains(season) && !(EntityManager.getInstance().seasonInTeam(season , this))) {
            seasons.add(season);
            EntityManager.getInstance().addSeasonToTeam(season , this);
            return true;
        }
        return false;
    }


    public boolean removeSeason(Season season) {
        /*DB*/
        if (!(EntityManager.getInstance().seasonInTeam(season , this))) {
            return false;
        }
        if(seasons.contains(season)) {
            seasons.remove(season);
        }
        return EntityManager.getInstance().removeSeasonFromTeam(season , this);
    }

    private boolean removePlayerOrCoach(PartOfTeam playerOrCoach) {
        for (BelongToTeam po : playersAndCoaches) {
            if (po.getAssetOfTheTeam().equals(playerOrCoach)) {
                playersAndCoaches.remove(po);
                return true;
            }
        }
        return false;
    }

    public boolean removeTeamPlayer(Player player) {
        return removePlayerOrCoach(player);
    }

    public boolean removeTeamCoach(Coach coach) {
        return removePlayerOrCoach(coach);
    }

    public boolean removeTeamManager(TeamManager teamManager) {
        if (teamManagers.remove(teamManager)) {
            this.permissionsPerTeamManager.remove(teamManager);
            /*DB*/
            //EntityManager.getInstance().removeTeamManager(teamManager , this);
            return true;
        }
        /*
        else if(EntityManager.getInstance().isTeamManager(teamManager , this))
        {
            return EntityManager.getInstance().removeTeamManager(teamManager , this);
        }

         */

        return false;
    }


    /**
     * - assumption - propertyName is Enum type!
     *
     * @param asset
     * @param propertyName
     * @return List<Enum> - contains all the Enum options to property of the asset
     */
    public List<Enum> getAllProperty(Asset asset, String propertyName) {
        List<Enum> enumList = asset.getAllPropertyList(this, propertyName);
        return enumList;
    }

    /**
     * add or remove permission to team manager
     *
     * @param teamManager
     * @param permissions
     */
    public void updatePermissionsPerTeamManger(TeamManager teamManager, List<TeamManagerPermissions> permissions) {
        this.permissionsPerTeamManager.put(teamManager, permissions);
        /*DB*/
        EntityManager.getInstance().updateTeamMangerPermission(teamManager,permissions,this);
    }

}
