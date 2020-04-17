package Domain.Users;

import Domain.Game.Asset;
import Domain.Game.Team;
import Service.UIController;

import java.util.ArrayList;
import java.util.List;

public class Coach extends Role implements Asset {

    private CoachQualification qualification;
    private Team coachedTeam;
    private String teamJob;

    public final String teamJobString = "Team job";
    public final String qualificationString = "Qualification";


    public Coach(SystemUser su) {
        super(RoleTypes.COACH,su);
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

    public boolean addTeam(Team teamToAdd, TeamOwner teamOwner)
    {
        if(teamToAdd != null && teamToAdd.isTeamOwner(teamOwner))
        {
            this.coachedTeam = teamToAdd;
            return coachedTeam.addTeamCoach(teamOwner,this);
        }
        return false;
    }

}
