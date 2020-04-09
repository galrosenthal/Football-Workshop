package Domain.Game;

import Domain.Users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This Class is representing a Football Team
 */
public class Team {

    private List<Player> teamPlayers;
    private List<Coach> teamCoaches;
    private List<TeamManager> teamManagers;
    private TeamOwner teamOwner;

    public Team(TeamOwner teamOwner) {
        this.teamOwner = teamOwner;
        teamPlayers = new ArrayList<>();
        teamCoaches = new ArrayList<>();
        teamManagers = new ArrayList<>();
    }

    /**
     * Add a Player to the team,
     * can only be requested by the Team Owner
     * @param teamPlayer a register user that represents a player
     * @return true if the player added successfully to the Team.
     */
    public boolean addTeamPlayer(TeamOwner townr, Registered teamPlayer)
    {
        if(!townr.equals(this.teamOwner))
        {
            return false;
        }
        if(teamPlayer.getType() == RegisteredTypes.PLAYER)
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

    public boolean addTeamCoach(TeamOwner townr, Registered coach)
    {
        if(!townr.equals(this.teamOwner))
        {
            return false;
        }
        if(coach.getType() == RegisteredTypes.COACH)
        {
            return teamCoaches.add((Coach) coach);
        }
        return false;
    }


    public boolean addTeamManager(TeamOwner townr,Registered teamManager)
    {
        if(!townr.equals(this.teamOwner))
        {
            return false;
        }
        if(teamManager.getType() == RegisteredTypes.TEAM_MANAGER)
        {
            return teamManagers.add((TeamManager) teamManager);
        }
        return false;
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
                this.teamOwner.getUsername().equals(team.teamOwner.getUsername());
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


    @Override
    public String toString() {
        return "Team{" +
                "teamPlayers=" + teamPlayersToString() +
                ", teamCoaches=" + teamCoachesToString() +
                ", teamManagers=" + teamManagersToString() +
                ", teamOwner=" + teamOwner.toString() +
                '}';
    }

    private String teamManagersToString() {
        StringBuilder teamManagersString = new StringBuilder();
        teamManagersString.append("Managers: \n");

        for (int i = 0; i < teamManagers.size(); i++)
        {
            teamManagersString.append(i+1).append(". ");
            teamManagersString.append(teamManagers.get(i).getName());
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
            teamCoachesString.append(teamCoaches.get(i).getName());
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
            teamPlayersString.append(teamPlayers.get(i).getName());
            teamPlayersString.append("\n");
        }
        return teamPlayersString.toString();
    }
}
