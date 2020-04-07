package Domain.Controllers;

import DB.DBManager;
import Domain.Game.Team;
import Domain.Users.*;

import java.util.List;


public class TeamController {

    public boolean addTeamOwner(Registered newOwner,Team teamToOwn){


         //If the user already team owner
        if(newOwner.getType() ==  RegisteredTypes.TEAM_OWNER){
            if(((TeamOwner)newOwner).isOwnTeam()){
                return false;
            }

            ((TeamOwner)newOwner).addTeamToOwn(teamToOwn);
        }
        else{
            List<String> teamOwnerDetails = DBManager.getInstance().getSystemUsers().getRecord(new String[]{"username"},new String[]{newOwner.getName()});
            TeamOwner newTeamOWner = new TeamOwner(newOwner.getUsername(),teamOwnerDetails.get(1),newOwner.getName());
            newOwner.setType(RegisteredTypes.TEAM_OWNER);
        }

        return true;
    }
}
