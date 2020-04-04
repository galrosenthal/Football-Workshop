package Game;

import Users.*;

import java.util.List;

public class Team {

    private List<Player> teamPlayers;
    private List<Coach> teamCoaches;
    private List<TeamManager> teamManagers;



    public boolean addTeamPlayer(Registered teamPlayer)
    {
        if(teamPlayer.getType() == RegisteredTypes.PLAYER)
        {
            return teamPlayers.add((Player)teamPlayer);
        }
        return false;
    }


//    public boolean addTeamCoach(Registered teamPlayer)
//    {
//        if(teamPlayer.getType() == RegisteredTypes.PLAYER)
//        {
//            return teamPlayers.add((Player)teamPlayer);
//        }
//        return false;
//    }

}
