package Domain.Users;

import Domain.Game.Asset;
import Domain.Game.Team;

import java.util.ArrayList;
import java.util.List;

public class Coach extends Role implements Asset {




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
        properties.add("Qualification");
        return properties;
    }
    @Override
    public boolean changeProperty(String property, String toChange)
    {
        if(property.equals("Team job"))
        {
            this.teamJob = toChange+"";
            return true;
        }
        else if(property.equals("Qualification"))
        {
            this.qualification = CoachQualification.valueOf(toChange);
        }
        return false;
    }

    @Override
    public boolean isListProperty(String property) {
        return false;
    }

    @Override
    public boolean isStringProperty(String property) {
        if(property.equals("Team job"))
        {
            return true;
        }
        else if(property.equals("Qualification"))
        {
            return false;
        }
        return false;
    }

    @Override
    public boolean isEnumProperty(String property) {
        if(property.equals("Team job"))
        {
            return false;
        }
        else if(property.equals("Qualification"))
        {
            return true;
        }
        return false;


    }

    @Override
    public void addProperty() {

    }

    @Override
    public void removeProperty() {

    }

    @Override
    public List<Enum> getAllValues(String property) {
        List<Enum> allEnumValues = new ArrayList<>();
        if(property.equals("Qualification"))
        {
            CoachQualification[] coachQualifications = CoachQualification.values();
            for (int i = 0; i < coachQualifications.length; i++) {
                //todo: check!
                allEnumValues.add(coachQualifications[i]);
            }
            return allEnumValues;
        }
        return allEnumValues;
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
