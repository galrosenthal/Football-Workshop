package Service;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Exceptions.TeamAlreadyExistsException;
import Domain.Exceptions.UserNotFoundException;
import Domain.Game.*;
import Domain.Users.*;
import org.junit.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ARControllerTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.startTest();
        DBManagerForTest.startConnection();
    }

    @Before
    public void setUp() throws Exception {
        UIController.setIsTest(true);
    }

    @Test
    public void addLeagueUTest() {
        assertFalse(ARController.addLeague(new SystemUserStub("stubUsername", "stub", 4)));
    }

    @Test
    public void addLeague2UTest() {
        AssociationRepresentativeStub.setSelector(0);
        assertFalse(ARController.addLeague(new SystemUserStub("stubUsername", "stub", 5)));
    }

    @Test
    public void addLeague3UTest() {
        AssociationRepresentativeStub.setSelector(1);
        assertTrue(ARController.addLeague(new SystemUserStub("stubUsername", "stub", 5)));
    }

    @Test
    public void addLeagueITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(5);
        //First league creation
        assertTrue(ARController.addLeague(systemUser));
        //Duplicated league creation
        assertFalse(ARController.addLeague(systemUser));
        //cleanUp
        assertTrue(EntityManager.getInstance().removeLeagueByName("newLeagueName"));
    }


    /**
     * Creates a new system user with AssociationRepresentative role.
     *
     * @return - SystemUser - a new system user with AssociationRepresentative role
     **/
    private SystemUser getSystemUserAR() {
        SystemUser systemUser = new SystemUser("username", "name");
        systemUser.addNewRole(new AssociationRepresentative(systemUser));
        return systemUser;
    }

    @Test
    public void addSeasonToLeagueUTest() {
        UIController.setSelector(0);
        assertFalse(ARController.addSeasonToLeague(new SystemUserStub("stubUsername", "stub", 4)));
    }

    @Test
    public void addSeasonToLeague2UTest() {
        UIController.setSelector(0);
        assertFalse(ARController.addSeasonToLeague(new SystemUserStub("stubUsername", "stub", 5)));
    }

    @Test
    public void addSeasonToLeagueITest() {
        //Adding SystemUser for integration
        SystemUser systemUser = getSystemUserAR();
        assertFalse(ARController.addSeasonToLeague(systemUser));
    }

    @Test
    public void addSeasonToLeague2ITest() {
        //success no re-tries test
        SystemUser systemUser = getSystemUserAR();
        EntityManager.getInstance().addLeague(new League("newLeagueName"));

        UIController.setSelector(921); //0 , "2020/21","2021/22"

        assertTrue(ARController.addSeasonToLeague(systemUser));

        League league = EntityManager.getInstance().getLeagues().get(0);
        assertTrue(league.doesSeasonExists("2020/21"));

        //cleanUp
        assertTrue(EntityManager.getInstance().removeLeagueByName("newLeagueName"));
    }

    @Test
    public void addSeasonToLeague3ITest() {
        //duplicated creation attempt failure and then change input and success.
        SystemUser systemUser = getSystemUserAR();
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        UIController.setSelector(921); //0 , "2020/21", "2020/21", "2021/22"

        assertTrue(ARController.addSeasonToLeague(systemUser));
        League league = EntityManager.getInstance().getLeagues().get(0);
        assertTrue(league.doesSeasonExists("2020/21"));

        assertTrue(ARController.addSeasonToLeague(systemUser));
        assertTrue(league.doesSeasonExists("2021/22"));

        //cleanUp
        assertTrue(EntityManager.getInstance().removeLeagueByName("newLeagueName"));
    }

    @Test
    public void addSeasonToLeague4ITest() {
        //success after wrong format
        SystemUser systemUser = getSystemUserAR();
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        UIController.setSelector(924); //0 , "wrong Format","2021/22"

        assertTrue(ARController.addSeasonToLeague(systemUser));

        League league = EntityManager.getInstance().getLeagues().get(0);
        assertTrue(league.doesSeasonExists("2021/22"));

        //cleanUp
        assertTrue(EntityManager.getInstance().removeLeagueByName("newLeagueName"));
    }

    @Test
    public void addRefereeUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 0);
        assertFalse(ARController.addReferee(systemUser));
    }

    @Test
    public void addReferee2UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 5);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName", 9311));
        AssociationRepresentativeStub.setSelector(0);
        UIController.setSelector(9311);

        assertFalse(ARController.addReferee(systemUser));
    }

    @Test
    public void addReferee3UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 5);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName", 9311));
        AssociationRepresentativeStub.setSelector(0);
        UIController.setSelector(9312);

        assertFalse(ARController.addReferee(systemUser));
    }

    @Test
    public void addReferee4UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 5);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName", 9311));
        AssociationRepresentativeStub.setSelector(1);
        UIController.setSelector(9311);

        assertTrue(ARController.addReferee(systemUser));
    }

    @Test
    public void addReferee5UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 5);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName", 9311));
        AssociationRepresentativeStub.setSelector(1);
        UIController.setSelector(9312);

        assertTrue(ARController.addReferee(systemUser));
    }

    @Test
    public void addRefereeITest() {
        SystemUser systemUser = getSystemUserAR();

        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName", 93131));
        UIController.setSelector(9311);

        assertTrue(ARController.addReferee(systemUser));
    }

    @Test
    public void addReferee2ITest() {
        SystemUser systemUser = getSystemUserAR();

        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName", 93132));
        UIController.setSelector(9311);

        assertFalse(ARController.addReferee(systemUser));
    }

    @Test
    public void addReferee3ITest() {
        SystemUser systemUser = getSystemUserAR();
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        UIController.setSelector(9311);

        assertTrue(ARController.addReferee(systemUser));

        assertNotNull(refereeUser);
        assertTrue(refereeUser.isType(RoleTypes.REFEREE));
        Referee refRole = (Referee) refereeUser.getRole(RoleTypes.REFEREE);
        assertTrue(refRole.getTraining() == RefereeQualification.VAR_REFEREE);
    }

    @Test
    public void addReferee4ITest() {
        SystemUser systemUser = getSystemUserAR();
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        UIController.setSelector(9312);

        assertTrue(ARController.addReferee(systemUser));

        assertNotNull(refereeUser);
        assertTrue(refereeUser.isType(RoleTypes.REFEREE));
        Referee refRole = (Referee) refereeUser.getRole(RoleTypes.REFEREE);
        assertTrue(refRole.getTraining()== RefereeQualification.VAR_REFEREE);
    }

    @Test
    public void addReferee5ITest() {
        SystemUser systemUser = getSystemUserAR();
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        new Referee(refereeUser, RefereeQualification.SIDE_REFEREE);

        UIController.setSelector(9311);
        //The user is already a referee
        assertFalse(ARController.addReferee(systemUser));
    }

    @Test
    public void removeRefereeUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 0);
        assertFalse(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeReferee2UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);

        assertFalse(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeReferee3UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        AssociationRepresentativeStub.setSelector(0);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName", 9311));
        UIController.setSelector(9321);

        assertFalse(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeReferee4UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        AssociationRepresentativeStub.setSelector(1);
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName", 9311));
        UIController.setSelector(9321);

        assertTrue(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeRefereeITest() {
        SystemUser systemUser = getSystemUserAR();

        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "refName", 9311));
        UIController.setSelector(9321);

        assertTrue(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeReferee2ITest() {
        SystemUser systemUser = getSystemUserAR();
        SystemUser refereeUser = new SystemUser("AviCohen", "name");

        UIController.setSelector(9321);
        //There are no referees
        assertFalse(ARController.removeReferee(systemUser));
    }

    @Test
    public void removeReferee3ITest() {
        SystemUser systemUser = getSystemUserAR();
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        new Referee(refereeUser, RefereeQualification.VAR_REFEREE);
        UIController.setSelector(9321);
        //There are no referees
        assertTrue(ARController.removeReferee(systemUser));
        assertFalse(refereeUser.isType(RoleTypes.REFEREE));
    }


    @Test //ITEST
    public void assignRefereeUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 0);
        assertFalse(ARController.assignReferee(systemUser));
    }

    @Test //ITEST
    public void assignReferee2UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        /*
        Expected: There are no leagues
                  Please add a league before assigning a referee
         */
        assertFalse(ARController.assignReferee(systemUser));
    }

    @Test
    public void assignReferee3UTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        UIController.setSelector(0);
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        /*
        Expected: Choose a League Number
                  0. newLeagueName
                  There are no seasons in the league
                  Please add a season before assigning a referee
         */
        assertFalse(ARController.assignReferee(systemUser));
    }

    @Test
    public void assignRefereeITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        UIController.setSelector(0);
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        League league = EntityManager.getInstance().getLeagues().get(0);
        league.addSeason("2020/21");
        /*
        Expected: Choose a League Number
                  0. newLeagueName
                  Choose a Season Number
                  0. 2020/21
                  There are no referees
         */
        assertFalse(ARController.assignReferee(systemUser));
    }

    @Test
    public void assignReferee3ITest() {
        SystemUser systemUser = new SystemUser("username", "name");
        assertFalse(ARController.assignReferee(systemUser));
    }

    @Test
    public void assignReferee4ITest() {
        SystemUser systemUser = getSystemUserAR();
        /*
        Expected: There are no leagues
                  Please add a league before assigning a referee
         */
        assertFalse(ARController.assignReferee(systemUser));
    }

    @Test
    public void assignReferee5ITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(0);
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        /*
        Expected: Choose a League Number
                  0. newLeagueName
                  There are no seasons in the league
                  Please add a season before assigning a referee
         */
        assertFalse(ARController.assignReferee(systemUser));
    }

    @Test
    public void assignReferee6ITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        UIController.setSelector(0);
        AssociationRepresentativeStub.setSelector(1);
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        League league = EntityManager.getInstance().getLeagues().get(0);
        league.addSeason("2020/21");
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        new Referee(refereeUser, RefereeQualification.VAR_REFEREE);
        /*
        Expected: The referee has been assigned to the season successfully
         */
        assertTrue(ARController.assignReferee(systemUser));
    }

    @Test
    public void assignReferee7ITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        UIController.setSelector(0);
        AssociationRepresentativeStub.setSelector(0);
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        League league = EntityManager.getInstance().getLeagues().get(0);
        league.addSeason("2020/21");
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        new Referee(refereeUser, RefereeQualification.VAR_REFEREE);
        /*
        Expected: This referee is already assigned to the chosen season
         */
        assertFalse(ARController.assignReferee(systemUser));
    }


    @Test
    public void assignReferee2ITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(0);
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        League league = EntityManager.getInstance().getLeagues().get(0);
        league.addSeason("2020/21");
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        new Referee(refereeUser, RefereeQualification.VAR_REFEREE);
        /*
        Expected: The referee has been assigned to the season successfully
         */
        assertTrue(ARController.assignReferee(systemUser));
    }

    @Test
    public void assignReferee8ITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(0);

        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        League league = EntityManager.getInstance().getLeagues().get(0);
        league.addSeason("2020/21");
        SystemUser refereeUser = new SystemUser("AviCohen", "name");
        new Referee(refereeUser, RefereeQualification.VAR_REFEREE);
        /*
        Expected: The referee has been assigned to the season successfully
         */
        assertTrue(ARController.assignReferee(systemUser));
        /*
        Expected: This referee is already assigned to the chosen season
         */
        assertFalse(ARController.assignReferee(systemUser));
    }


    @Test
    public void registerNewTeamUTest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9101);
        assertFalse(ARController.registerNewTeam(systemUser));
    }

    @Test
    public void registerNewTeam2UTest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser arSystemUser = new SystemUserStub("stubUsername", "stub", 9102); //AR
        EntityManager.getInstance().addTeam(new TeamStub(9102));
        UIController.setSelector(9102);
        try {
            ARController.registerNewTeam(arSystemUser);
            Assert.fail();
        } catch (TeamAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void registerNewTeam3UTest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser arSystemUser = new SystemUserStub("stubUsername", "stub", 9103); //AR
        UIController.setSelector(9103);
        try {
            ARController.registerNewTeam(arSystemUser);
            Assert.fail();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void registerNewTeam4UTest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser arSystemUser = new SystemUserStub("stubUsername", "stub", 9104); //AR
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "toName", 91041));
        UIController.setSelector(91041);
        assertTrue(ARController.registerNewTeam(arSystemUser));
    }

    @Test
    public void registerNewTeamITest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser systemUser = getSystemUserAR();
        EntityManager.getInstance().addUser(new SystemUserStub("AviCohen", "toName", 91051));
        UIController.setSelector(91051);
        assertTrue(ARController.registerNewTeam(systemUser));
    }

    @Test
    public void registerNewTeam2ITest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser systemUser = getSystemUserAR();
        SystemUser usrToBeOwner = new SystemUser("AviCohen", "name");
        UIController.setSelector(91052);
        assertTrue(ARController.registerNewTeam(systemUser));

        assertNotNull(usrToBeOwner);
        assertTrue(usrToBeOwner.isType(RoleTypes.TEAM_OWNER));
        TeamOwner toRole = (TeamOwner) usrToBeOwner.getRole(RoleTypes.TEAM_OWNER);
        assertEquals(1, toRole.getOwnedTeams().size());
    }

    @Test
    public void registerNewTeam3ITest() throws TeamAlreadyExistsException, UserNotFoundException {
        SystemUser systemUser = getSystemUserAR();
        SystemUser usrToBeOwner = new SystemUser("AviCohen", "name");
        usrToBeOwner.addNewRole(new TeamOwner(usrToBeOwner));
        assertTrue(usrToBeOwner.isType(RoleTypes.TEAM_OWNER));
        TeamOwner toRole = (TeamOwner) usrToBeOwner.getRole(RoleTypes.TEAM_OWNER);
        assertEquals(0, toRole.getOwnedTeams().size());

        UIController.setSelector(91053);
        assertTrue(ARController.registerNewTeam(systemUser));
        assertNotNull(usrToBeOwner);
        assertTrue(usrToBeOwner.isType(RoleTypes.TEAM_OWNER));
        assertEquals(1, toRole.getOwnedTeams().size());
    }

    @Test
    public void addTeamsToSeasonUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 91121);
        //not ar
        assertFalse(ARController.addTeamsToSeason(systemUser));
        SystemUser arSystemUser = new SystemUserStub("stubUsername", "stub", 91122); //AR
        //"There are no leagues"
        assertFalse(ARController.addTeamsToSeason(arSystemUser));

        LeagueStub leagueStub = new LeagueStub("stubLeg", 91123);
        EntityManager.getInstance().addLeague(leagueStub);
        //"There are no leagues that their latest season hasn't started"
        assertFalse(ARController.addTeamsToSeason(arSystemUser));

        leagueStub.setSelector(91124); // now there is latest season that hasn't started
        UIController.setSelector(91125); // choose 0 (the league)
        //"There are no teams"
        assertFalse(ARController.addTeamsToSeason(arSystemUser));

        TeamStub teamStub = new TeamStub(91126);
        EntityManager.getInstance().addTeam(teamStub);
        UIController.setSelector(91126);
        //success
        assertTrue(ARController.addTeamsToSeason(arSystemUser));

    }

    @Test
    public void addTeamsToSeasonITest() {
        SystemUser arSystemUser = getSystemUserAR();
        new TeamOwner(arSystemUser);
        TeamOwner toRole = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
        Team team1 = new Team("Hapoel Beit Shan", toRole);
        Team team2 = new Team("Hapoel Beer Sheva", toRole);
        //"There are no leagues"
        assertFalse(ARController.addTeamsToSeason(arSystemUser));
        League league = new League("Ligat ul");
        league.addSeason("2019/20");
        Season season = league.getLatestSeason();
        EntityManager.getInstance().addTeam(team1);
        EntityManager.getInstance().addTeam(team2);
        EntityManager.getInstance().addLeague(league);
        assertNotNull(season);
        assertEquals("2019/20", season.getYears());
        season.startSeason();
        //"There are no leagues that their latest season hasn't started"
        assertFalse(ARController.addTeamsToSeason(arSystemUser));

        league.addSeason("2020/21");
        Season newSeason = league.getLatestSeason();
        assertNotNull(newSeason);
        assertEquals("2020/21", newSeason.getYears());
        newSeason.addTeam(team1);
        newSeason.addTeam(team2);
        team1.addSeason(newSeason);
        team2.addSeason(newSeason);
        UIController.setSelector(91131); //choose 0 for league
        //"There are no teams that do not belong already to the chosen league's latest season"
        assertFalse(ARController.addTeamsToSeason(arSystemUser));

        newSeason.removeTeam(team1);
        team1.removeSeason(newSeason);
        assertEquals(1, newSeason.getTeams().size());
        assertEquals(0, team1.getSeasons().size());
        UIController.setSelector(91132); // 0 for league, then 0 and -1
        //successfully added team1 to newSeason
        assertTrue(ARController.addTeamsToSeason(arSystemUser));
        assertEquals(2, newSeason.getTeams().size());
        assertEquals(1, team1.getSeasons().size());
    }


    @Test
    public void removeTeamsFromSeasonUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 91221);
        //not ar
        assertFalse(ARController.removeTeamsFromSeason(systemUser));
        SystemUser arSystemUser = new SystemUserStub("stubUsername", "stub", 91222); //AR
        //"There are no leagues"
        assertFalse(ARController.removeTeamsFromSeason(arSystemUser));

        LeagueStub leagueStub = new LeagueStub("stubLeg", 91223);
        EntityManager.getInstance().addLeague(leagueStub);
        //"There are no leagues that their latest season hasn't started"
        assertFalse(ARController.removeTeamsFromSeason(arSystemUser));

        leagueStub.setSelector(91224); // now there is latest season that hasn't started
        UIController.setSelector(91225); // choose 0 (the league)
        //"There are no teams"
        assertFalse(ARController.removeTeamsFromSeason(arSystemUser));

        TeamStub teamStub = new TeamStub(91226);
        EntityManager.getInstance().addTeam(teamStub);
        UIController.setSelector(91226);
        //"There are no teams that belong to the chosen league's latest season"
        assertFalse(ARController.removeTeamsFromSeason(arSystemUser));
    }

    @Test
    public void removeTeamsFromSeasonITest() {
        SystemUser arSystemUser = getSystemUserAR();
        new TeamOwner(arSystemUser);
        TeamOwner toRole = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
        Team team1 = new Team("Hapoel Beit Shan", toRole);
        Team team2 = new Team("Hapoel Beer Sheva", toRole);
        //"There are no leagues"
        assertFalse(ARController.removeTeamsFromSeason(arSystemUser));
        League league = new League("Ligat ul");
        league.addSeason("2019/20");
        Season season = league.getLatestSeason();
        EntityManager.getInstance().addTeam(team1);
        EntityManager.getInstance().addTeam(team2);
        EntityManager.getInstance().addLeague(league);
        assertNotNull(season);
        assertEquals("2019/20", season.getYears());
        season.startSeason();
        //"There are no leagues that their latest season hasn't started"
        assertFalse(ARController.removeTeamsFromSeason(arSystemUser));

        league.addSeason("2020/21");
        Season newSeason = league.getLatestSeason();
        assertNotNull(newSeason);
        assertEquals("2020/21", newSeason.getYears());
        UIController.setSelector(91231); //choose 0 for league
        //"There are no teams that belong to the chosen league's latest season"
        assertFalse(ARController.removeTeamsFromSeason(arSystemUser));

        newSeason.addTeam(team1);
        newSeason.addTeam(team2);
        team1.addSeason(newSeason);
        team2.addSeason(newSeason);

        assertEquals(2, newSeason.getTeams().size());
        assertEquals(1, team1.getSeasons().size());
        UIController.setSelector(91232); // 0 for league, then 0 and -1
        //successfully removed team1 from newSeason
        assertTrue(ARController.removeTeamsFromSeason(arSystemUser));
        assertEquals(1, newSeason.getTeams().size());
        assertEquals(0, team1.getSeasons().size());
    }


    @Test
    public void addPointsPolicyUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 0);
        assertFalse(ARController.addPointsPolicy(systemUser));
    }

    @Test
    public void addPointsPolicyITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        UIController.setSelector(9511);//1,-1,0
        AssociationRepresentativeStub.setSelector(9511);
        assertFalse(ARController.addPointsPolicy(systemUser));
    }

    @Test
    public void addPointsPolicy2ITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        UIController.setSelector(9511);//1,-1,0
        AssociationRepresentativeStub.setSelector(9512);
        assertFalse(ARController.addPointsPolicy(systemUser));
    }

    @Test
    public void addPointsPolicy3ITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        UIController.setSelector(9511);//1,-1,0
        AssociationRepresentativeStub.setSelector(9513);
        assertFalse(ARController.addPointsPolicy(systemUser));
    }

    @Test
    public void addPointsPolicy4ITest() {
        //success
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(9511);//1,-1,0
        assertTrue(ARController.addPointsPolicy(systemUser));
        assertTrue(EntityManager.getInstance().doesPointsPolicyExists(1, -1, 0));
        assertNotNull(EntityManager.getInstance().getPointsPolicy(1, -1, 0));
    }

    @Test
    public void addPointsPolicy5ITest() {
        //duplicated policy
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(9511); //1,-1,0
        assertTrue(ARController.addPointsPolicy(systemUser));
        assertFalse(ARController.addPointsPolicy(systemUser));
        //This Points policy already exists
    }

    @Test
    public void addPointsPolicy6ITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(9512);//-1,0,1
        assertFalse(ARController.addPointsPolicy(systemUser));
        //The victory points most be positive
    }

    @Test
    public void addPointsPolicy7ITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(9514);//1,1,-1
        assertFalse(ARController.addPointsPolicy(systemUser));
        //The loss points most be negative or zero
    }

    @Test
    public void setPointsPolicyUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 0);
        assertFalse(ARController.setPointsPolicy(systemUser));
    }

    @Test
    public void setPointsPolicyITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        assertFalse(ARController.setPointsPolicy(systemUser));
    }

    @Test
    public void setPointsPolicy2ITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        UIController.setSelector(0);
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        assertFalse(ARController.setPointsPolicy(systemUser));
    }

    @Test
    public void setPointsPolicy3ITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 9321);
        UIController.setSelector(0);
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        League league = EntityManager.getInstance().getLeagues().get(0);
        league.addSeason("2020/21");
        assertTrue(ARController.setPointsPolicy(systemUser));
        assertTrue(league.getSeasons().get(0).getPointsPolicy().equals(3, 0, 1));
    }

    @Test
    public void setPointsPolicy4ITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(0);
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        League league = EntityManager.getInstance().getLeagues().get(0);
        league.addSeason("2020/21");
        assertTrue(ARController.setPointsPolicy(systemUser));
        assertTrue(league.getSeasons().get(0).getPointsPolicy().equals(3, 0, 1));
    }

    @Test
    public void setPointsPolicy5ITest() {
        SystemUser systemUser = getSystemUserAR();
        EntityManager.getInstance().addLeague(new League("newLeagueName"));
        League league = EntityManager.getInstance().getLeagues().get(0);
        league.addSeason("2020/21");

        AssociationRepresentative aR = (AssociationRepresentative) systemUser.getRole(RoleTypes.ASSOCIATION_REPRESENTATIVE);
        try {
            aR.addPointsPolicy(1, -1, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(league.getSeasons().get(0).getPointsPolicy().equals(3, 0, 1));
        UIController.setSelector(95211);
        assertTrue(ARController.setPointsPolicy(systemUser));
        assertTrue(league.getSeasons().get(0).getPointsPolicy().equals(1, -1, 0));
    }


    @Test
    public void addSchedulingPolicyUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 0);
        assertFalse(ARController.addSchedulingPolicy(systemUser));
    }

    @Test
    public void addSchedulingPolicyITest() {
        //success
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(962);//1,1,1,..
        assertTrue(ARController.addSchedulingPolicy(systemUser));
        assertTrue(EntityManager.getInstance().doesSchedulingPolicyExists(1, 1, 1));
        assertNotNull(EntityManager.getInstance().getSchedulingPolicy(1, 1, 1));
    }

    @Test
    public void addSchedulingPolicy2ITest() {
        //duplicated policy
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(962); //1,1,1,...
        assertTrue(ARController.addSchedulingPolicy(systemUser));
        assertFalse(ARController.addSchedulingPolicy(systemUser));
        //This scheduling policy already exists
    }

    @Test
    public void addSchedulingPolicy3ITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(961);//0,1,1
        assertFalse(ARController.addSchedulingPolicy(systemUser));
        //The number of games for each team per season must be positive integer
    }

    @Test
    public void addSchedulingPolicy4ITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(9511);//1,-1,0
        assertFalse(ARController.addSchedulingPolicy(systemUser));
        //The number of games on the same day must be positive integer
    }

    @Test
    public void addSchedulingPolicy5ITest() {
        SystemUser systemUser = getSystemUserAR();
        UIController.setSelector(9514);//1,1,-1
        assertFalse(ARController.addSchedulingPolicy(systemUser));
        //The minimum rest days between games must be non-negative integer
    }


    @After
    public void tearDown() throws Exception {
        EntityManager.getInstance().clearAll();
    }

    @AfterClass
    public static void afterClass() {
        DBManager.getInstance().closeConnection();
    }
}