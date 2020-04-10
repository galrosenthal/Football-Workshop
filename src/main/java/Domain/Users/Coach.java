package Domain.Users;

import Domain.Game.Team;

public class Coach extends Registered {
    public enum CoachQualification {
            MAIN_COACH,SECOND_COACH,JUNIOR_COACH;
    }

    private CoachQualification qualification;
    private Team coachedTeam;
    private String teamJob;


    public Coach(String type, String username, String pass, String name,
                 CoachQualification qlf, Team teamToCoach, String jobTitle) {
        super(RoleTypes.COACH, username, pass, name);
        qualification = qlf;
        coachedTeam = teamToCoach;
        teamJob = jobTitle;
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
