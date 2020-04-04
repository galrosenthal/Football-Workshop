package Users;

import Game.Team;

public class Coach extends Registered {
    public enum CoachQualification {
            MAIN_COACH,SECOND_COACH,JUNIOR_COACH;
    }

    private CoachQualification qualification;
    private Team coachedTeam;
    private String teamJob;


    public Coach(String type, String username, String pass, String name,
                 CoachQualification qlf, Team teamToCoach, String jobTitle) {
        super("Coach", username, pass, name);
        qualification = qlf;
        coachedTeam = teamToCoach;
        teamJob = jobTitle;
    }
}
