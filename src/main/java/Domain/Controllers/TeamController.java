package Domain.Controllers;

import Domain.EntityManager;
import Domain.Game.Team;
import Domain.Users.*;
import Domain.Exceptions.*;
import Service.UIController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TeamController {


    public static boolean addTeamOwner(String username, Team teamToOwn, TeamOwner owner)
    throws Exception{

        List<TeamOwner> teamOwners = teamToOwn.getTeamOwners();

        SystemUser newTeamOwnerUser = EntityManager.getInstance().getUser(username);

        //TODO: check already owned another team in the league
        if(newTeamOwnerUser == null)
        {
            throw new UserNotFoundException("Could not find a user by the given username" + username);
        }

        Role newTeamOwnerRole = newTeamOwnerUser.getRole(RoleTypes.TEAM_OWNER);
        TeamOwner teamOwner;
        if(newTeamOwnerRole == null)
        {
            teamOwner = new TeamOwner(newTeamOwnerUser);
        }
        else
        {

            teamOwner = (TeamOwner) newTeamOwnerRole;
            if(teamOwners.contains(teamOwner))
            {
                throw new alreadyTeamOwnerException("This User is already a team owner");
            }
        }
        teamOwner.addTeamToOwn(teamToOwn);
        teamToOwn.addTeamOwner(owner,teamOwner);



        return true;
    }


    /**
     * Add a player with playerUsername to the teamToAddPlayer by the teamOwner
     * @param playerUsername is the Username of the player to add
     * @param teamToAddPlayer is the Team to add the player to
     * @param teamOwner is the Owner who asks to add the player
     * @return true if the player was added successfully
     * @throws UserNotFoundException
     * @throws PlayerIsAlreadyInThisTeamException
     */
    public static boolean addPlayer(String playerUsername, Team teamToAddPlayer, TeamOwner teamOwner) throws Exception
    {
        SystemUser playerUser = EntityManager.getInstance().getUser(playerUsername);
        if(playerUser == null)
        {
            throw new UserNotFoundException("Could not find a user by the given username" + playerUsername);
        }

        Role getRoleForPlayer = playerUser.getRole(RoleTypes.PLAYER);
        Player playerRole;
        if(getRoleForPlayer == null)
        {
            PlayerFieldJobs filedJob = getPlayerFieldJob();
            Date bday = getPlayerBirthDate();
            playerRole = new Player(playerUser,filedJob,bday);
        }
        else {
            playerRole = (Player)getRoleForPlayer;
            if(teamToAddPlayer.getTeamPlayers().contains(playerRole))
            {
                throw new PlayerIsAlreadyInThisTeamException("Player is already part of this team");
            }
        }

        return teamToAddPlayer.addTeamPlayer(teamOwner,playerRole);
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

    /**
     * Gets the player field job chosen by the user from the list of filed jobs
     * @return the field job chosen for the suer
     * @see PlayerFieldJobs
     */
    private static PlayerFieldJobs getPlayerFieldJob() {
        UIController.printMessage("Please Choose FiledJob for player");
        for (int i = 0; i < PlayerFieldJobs.values().length; i++) {
            UIController.printMessage(i + ". " + PlayerFieldJobs.values()[i]);
        }
        int jobIndex;

        do{
            jobIndex = UIController.receiveInt();
        }while (!(jobIndex >= 0 && jobIndex < PlayerFieldJobs.values().length));

        return PlayerFieldJobs.values()[jobIndex];
    }

    public static boolean addCoach(String coachName, Team teamToAddCoach, TeamOwner teamOwner) throws Exception {
        return false;
    }

    public static boolean addTeamManager(String managerName, Team teamToAddManager, TeamOwner teamOwner) throws Exception {
        return false;
    }

    public static boolean addStadium(String stadiumName, Team teamToAddStadium, TeamOwner teamOwner) throws Exception {
        return false;
    }
}
