package Domain.Game;

import Domain.Users.*;

import java.util.List;

public class Team {

    private String teamName;
    private List<Player> teamPlayers;
    private List<Coach> teamCoaches;
    private List<TeamManager> teamManagers;


    /**
     * A method that returns a string containing all team information for display
     * @return string containing all team information.
     */
    public String DisplayTeam(){
        String output = "Team Name:" + teamName + "\n" +
                        "Team Coaches:";



        return output;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' + "\n" +
                ", teamPlayers=" + teamPlayers +"\n" +
                ", teamCoaches=" + teamCoaches +"\n" +
                ", teamManagers=" + teamManagers +"\n" +
                '}';
    }

    public boolean addTeamPlayer(Registered teamPlayer)
    {
        if(teamPlayer.getType() == RegisteredTypes.PLAYER)
        {
            return teamPlayers.add((Player)teamPlayer);
        }
        return false;
    }

    public void addTeamStaff(Registered regUser) {
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
