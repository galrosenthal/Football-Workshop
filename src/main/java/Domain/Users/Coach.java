package Domain.Users;

import Domain.Game.Asset;
import Domain.Game.Team;
import Service.UIController;
import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.List;

public class Coach extends PartOfTeam {

    private CoachQualification qualification;
    private List<Team> coachedTeams;
    private String teamJob;
    public final String teamJobString = "Team job";
    public final String qualificationString = "Qualification";

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
    public List<String> getProperties() {
        List<String> properties = new ArrayList<>();
        properties.add(teamJobString);
        properties.add(qualificationString);
        return properties;
    }
    @Override
    public boolean changeProperty(String property, String toChange)
    {
        if(property.equalsIgnoreCase(teamJobString))
        {
            this.teamJob = toChange+"";
            return true;
        }
        else if(property.equals(qualificationString))
        {
            this.qualification = CoachQualification.valueOf(toChange);
            return true;
        }
        return false;
    }

    @Override
    public boolean isListProperty(String property) {
        return false;
    }

    @Override
    public boolean isStringProperty(String property) {
        if(property.equalsIgnoreCase(teamJobString))
        {
            return true;
        }
        else if(property.equalsIgnoreCase(qualificationString))
        {
            return false;
        }
        return false;
    }

    @Override
    public boolean isEnumProperty(String property) {
        if(property.equalsIgnoreCase(teamJobString))
        {
            return false;
        }
        else if(property.equalsIgnoreCase(qualificationString))
        {
            return true;
        }
        return false;


    }

    @Override
    public boolean addAllProperties() {
        if(!addProperty(teamJobString))
        {
            return false;
        }

        if(!addProperty(qualificationString))
        {
            removeProperty(teamJobString);
            return false;
        }
        return true;
    }

    @Override
    public boolean addProperty(String property)
    {
        String valueOfProperty = "";
        if(property.equalsIgnoreCase(teamJobString))
        {
            UIController.printMessage("what is the Coach JobTitle?");
            valueOfProperty = UIController.receiveString();
        }
        else if(property.equalsIgnoreCase(qualificationString))
        {
            int qualfIndex = getEnumIndex();
            valueOfProperty = CoachQualification.values()[qualfIndex].toString();
        }


        return this.changeProperty(property,valueOfProperty);



    }

    private int getEnumIndex() {
        UIController.printMessage("Please Choose a qualification for the coach:");

        int i = 0;
        for (CoachQualification qlf: CoachQualification.values())
        {
            UIController.printMessage(i++ +". " + qlf.toString());
        }

        int index;

        do{
            index = UIController.receiveInt();
        }while (!(index >= 0 && index < PlayerFieldJobs.values().length));



        return index;
    }

    @Override
    public boolean removeProperty(String property) {

        return false;
    }

    @Override
    public List<Enum> getAllValues(String property) {
        List<Enum> allEnumValues = new ArrayList<>();
        if(property.equals(qualificationString))
        {
            CoachQualification[] coachQualifications = CoachQualification.values();
            for (int i = 0; i < coachQualifications.length; i++) {
                allEnumValues.add(coachQualifications[i]);
            }
            return allEnumValues;
        }
        return null;
    }

    @Override
    public List<Enum> getAllPropertyList(Team team, String propertyName) {
        return null;
    }

    @Override
    public boolean addProperty(String propertyName, Enum anEnum , Team team) {
        return false;
    }

    @Override
    public boolean removeProperty(String propertyName, Enum anEnum, Team team) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coach)) return false;
        Coach coach = (Coach) o;
        return qualification == coach.qualification &&
                ((teamJob == null && coach.teamJob == null) || teamJob.equals(coach.teamJob)) &&
                super.systemUser.equals(coach.systemUser);
    }

    public boolean addTeam(Team teamToAdd, TeamOwner teamOwner)
    {
        if(teamToAdd != null && teamToAdd.isTeamOwner(teamOwner))
        {
            this.coachedTeams.add(teamToAdd);
            return teamToAdd.addTeamCoach(teamOwner,this);
        }
        return false;
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
    @Override
    public String toString() {
        return "Coach{" +
                "qualification=" + qualification +
                ", coachedTeam=" + coachedTeams +
                ", teamJob='" + teamJob + '\'' +
                ", teamJobString='" + teamJobString + '\'' +
                ", qualificationString='" + qualificationString + '\'' +
                '}';
    }
}
