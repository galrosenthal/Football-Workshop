package Domain.Users;

import Domain.Game.Team;

import java.util.ArrayList;
import java.util.List;

public class Coach extends Role {
    public enum CoachQualification {
            MAIN_COACH,SECOND_COACH,JUNIOR_COACH;
    }

    private CoachQualification qualification;
    private List<Team> coachedTeams;
    private String teamJob;

    public Coach(SystemUser systemUser) {
        super(RoleTypes.COACH, systemUser);
        coachedTeams = new ArrayList<>();
    }

    public Coach(SystemUser su , CoachQualification qlf,  String jobTitle) {
        super(RoleTypes.COACH,su);
        qualification = qlf;
        teamJob = jobTitle;
        coachedTeams = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coach)) return false;
        Coach coach = (Coach) o;
        return qualification == coach.qualification &&
                teamJob.equals(coach.teamJob);
    }

    public boolean addTeamToCoach(Team playTeam){
        if(!this.coachedTeams.contains(playTeam)) {
            this.coachedTeams.add(playTeam);
            return true;
        }
        return false;
    }

    public boolean removeTeamToCoach(Team team){
        return this.coachedTeams.remove(team);
    }

    public List<Team> getCoachedTeams() {
        return coachedTeams;
    }

    public String getTeamJob() {
        return teamJob;
    }
}
