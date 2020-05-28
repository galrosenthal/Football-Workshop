package Domain.Users;

import Domain.Game.Team;
import Service.UIController;

import java.util.ArrayList;
import java.util.List;

public class Coach extends PartOfTeam {

    private CoachQualification qualification;
    private List<Team> coachedTeams;
    public final String teamJobString = "Team job";
    public final String qualificationString = "Qualification";

    public Coach(SystemUser systemUser) {
        super(RoleTypes.COACH, systemUser, true);
        coachedTeams = new ArrayList<>();
    }
    public Coach(SystemUser systemUser, CoachQualification qualification) {
        super(RoleTypes.COACH, systemUser, true);
        coachedTeams = new ArrayList<>();
        this.qualification = qualification;
    }

    public CoachQualification getQualification() {
        return qualification;
    }

    @Override
    public List<String> getProperties() {
        List<String> properties = new ArrayList<>();
        properties.add(teamJobString);
        properties.add(qualificationString);
        return properties;
    }
    @Override
    public boolean changeProperty(Team teamOfAsset, String property, String toChange)
    {
        if(property.equalsIgnoreCase(teamJobString))
        {
//            this.teamJob = toChange+"";
            BelongToTeam assetBelongsTo = getBelongTeamByTeam(teamOfAsset);
            if(assetBelongsTo == null)
            {
                return false;
            }
            assetBelongsTo.setTeamJob(toChange);
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
    public boolean addAllProperties(Team assetBelongsTo) {
        if(!addProperty(assetBelongsTo, teamJobString))
        {
            return false;
        }

        if(!addProperty(assetBelongsTo, qualificationString))
        {
            removeProperty(assetBelongsTo, teamJobString);
            return false;
        }
        return true;
    }

    @Override
    public boolean addProperty(Team teamBelongsTo, String property)
    {
        String valueOfProperty = "";
        if(property.equalsIgnoreCase(teamJobString))
        {
            valueOfProperty = UIController.receiveString("what is the Coach JobTitle?");
        }
        else if(property.equalsIgnoreCase(qualificationString))
        {
            int qualfIndex = getEnumIndex();
            valueOfProperty = CoachQualification.values()[qualfIndex].toString();
        }


        return this.changeProperty(teamBelongsTo, property,valueOfProperty);



    }

    private int getEnumIndex() {

        List<String> coachQualificationsList = new ArrayList<>();
        for (CoachQualification qlf: CoachQualification.values())
        {
            coachQualificationsList.add(qlf.name());
        }

        int index;

        do{
            index = UIController.receiveInt("Please Choose a qualification for the coach:",coachQualificationsList);
        }while (!(index >= 0 && index < PlayerFieldJobs.values().length));



        return index;
    }

    @Override
    public boolean removeProperty(Team teamBelongsTo, String property) {

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
                super.systemUser.equals(coach.systemUser);
    }

//    public boolean addTeam(Team teamToAdd, TeamOwner teamOwner)
//    {
//        if(teamToAdd != null && teamToAdd.isTeamOwner(teamOwner))
//        {
//            this.coachedTeams.add(teamToAdd);
//            return teamToAdd.addTeamCoach(teamOwner,this);
//        }
//        return false;
//    }
//
//    public boolean addTeamToCoach(Team playTeam){
//        if(!this.coachedTeams.contains(playTeam)) {
//            this.coachedTeams.add(playTeam);
//            return true;
//        }
//        return false;
//    }

//    public boolean removeTeam(Team team){
//        return this.coachedTeams.remove(team);
//    }




    @Override
    public String toString() {
        return "Coach{" +
                "qualification=" + qualification +
                ", coachedTeam=" + coachedTeams +
                ", teamJobString='" + teamJobString + '\'' +
                ", qualificationString='" + qualificationString + '\'' +
                '}';
    }
}
