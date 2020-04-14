package Domain.Users;

import Domain.Game.Asset;
import Domain.Game.Team;

import java.util.ArrayList;
import java.util.List;

public class Coach extends Role implements Asset {


    public enum CoachQualification {
            MAIN_COACH,SECOND_COACH,JUNIOR_COACH;
    }

    private CoachQualification qualification;
    private Team coachedTeam;
    private String teamJob;


    public Coach(SystemUser su , CoachQualification qlf, Team teamToCoach , String jobTitle) {
        super(RoleTypes.COACH,su);
        qualification = qlf;
        coachedTeam = teamToCoach;
        teamJob = jobTitle;
    }

    @Override
    public List<String> getProperties() {
        List<String> properties = new ArrayList<>();
        properties.add("Team job");
        return properties;
    }
    @Override
    public boolean changeProperty(String property)
    {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coach)) return false;
        Coach coach = (Coach) o;
        return qualification == coach.qualification &&
                teamJob.equals(coach.teamJob);
    }

    public boolean addTeam(Team teamToAdd)
    {
        if(teamToAdd != null)
        {
            this.coachedTeam = teamToAdd;
            return true;
        }
        return false;
    }

}
