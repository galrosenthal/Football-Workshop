package Domain.Controllers;

import DB.DBManager;
import Domain.Game.Team;
import Domain.Users.*;

import java.util.List;


public class TeamController {

    public boolean addTeamOwner(String username,Team teamToOwn,TeamOwner owner){

        List<TeamOwner> teamOwners = teamToOwn.getTeamOwners();

        List<String> teamOwnerDetails = DBManager.getInstance().getSystemUsers().getRecord(new String[]{"username"},new String[]{username});
        String[] roles = teamOwnerDetails.get(teamOwnerDetails.size()-1).split(";");
        for (String rol : roles){
            if(rol.equalsIgnoreCase("team owner")){
                return false;
            }
        }

        if(!teamOwners.contains(owner)){
            return false;
        }

        TeamOwner newTeamOWner = new TeamOwner(username,teamOwnerDetails.get(1),teamOwnerDetails.get(2));
        newTeamOWner.addTeamToOwn(teamToOwn);
        teamToOwn.addTeamOwner(owner,newTeamOWner);



        return true;
    }
}
