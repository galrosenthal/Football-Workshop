package Domain.Users;

import Domain.Game.Team;

public class Coach extends Role {


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
    public String[] getProperties() {
        String [] properties = new String[3];
        properties[0] = "Qualification";
        properties[1] = "Team";
        properties[2] = "Team job";
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


}
