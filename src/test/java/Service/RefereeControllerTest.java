package Service;

import DB.DBManager;
import DB.DBManagerForTest;
import Domain.EntityManager;
import Domain.Game.*;
import Domain.Logger.*;
import Domain.Users.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class RefereeControllerTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBManager.getInstance().startTest();
        DBManagerForTest.startConnection();    }

    @Before
    public void setUp() throws Exception {
        UIController.setIsTest(true);
    }

    @Test
    public void updateGameEventsUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 0);
        assertFalse(RefereeController.updateGameEvents(systemUser));
    }

    @Test
    public void updateGameEventsITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 1031);
        assertFalse(RefereeController.updateGameEvents(systemUser));
        //"There are no games for this referee"
    }

    @Test
    public void updateGameEvents2ITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 1031);
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        Team firstTeam = new TeamStub(9511);
        Team secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        game.setHasFinished(true);

        game.addReferee(referee);
        referee.addGame(game);

        //UIController.setSelector();
        assertFalse(RefereeController.updateGameEvents(systemUser));
        //"There are no ongoing games for this referee"
    }

    @Test
    public void updateGameEventsSuccessRedCardITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 1031);
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(1031);//0,0,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Red Card has been added successfully

        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof RedCard);
        assertTrue(((RedCard) event).getMinute() == 1);
        assertTrue(((RedCard) event).getOffender().equals(player1));
    }

    @Test
    public void updateGameEventsSuccessRedCard2ITest() {
        SystemUser systemUser = getSystemUserReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(1031);//0,0,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Red Card has been added successfully

        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof RedCard);
        assertTrue(((RedCard) event).getMinute() == 1);
        assertTrue(((RedCard) event).getOffender().equals(player1));
    }

    private SystemUser getSystemUserReferee() {
        SystemUser systemUser = new SystemUser("username", "name");
        systemUser.addNewRole(new Referee(systemUser, "VAR"));
        return systemUser;
    }

    @Test
    public void updateGameEventsSuccessYellowCardITest() {
        SystemUser systemUser = getSystemUserReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(1032); //0,1,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Yellow Card has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof YellowCard);
        assertTrue(((YellowCard) event).getMinute() == 1);
        assertTrue(((YellowCard) event).getOffender().equals(player1));
    }

    @Test
    public void updateGameEventsSuccessGoalITest() {
        SystemUser systemUser = getSystemUserReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(1036); //0,2,1,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Goal has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof Goal);
        assertTrue(((Goal) event).getMinute() == 1);
        assertTrue(((Goal) event).getScoringTeam().equals(secondTeam));
        assertTrue(((Goal) event).getScoredOnTeam().equals(firstTeam));
    }

    @Test
    public void updateGameEventsSuccessOffsideITest() {
        SystemUser systemUser = getSystemUserReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(1038); //0,3,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Offside has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof Offside);
        assertTrue(((Offside) event).getMinute() == 1);
        assertTrue(((Offside) event).getTeamWhoCommitted().equals(firstTeam));
    }

    @Test
    public void updateGameEventsSuccessPenaltyITest() {
        SystemUser systemUser = getSystemUserReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(10310); //0,4,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Penalty has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof Penalty);
        assertTrue(((Penalty) event).getMinute() == 1);
        assertTrue(((Penalty) event).getTeamWhoCommitted().equals(firstTeam));
    }

    @Test
    public void updateGameEventsSuccessSwitchPlayersITest() {
        SystemUser systemUser = getSystemUserReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(10312); //0,5,0,1,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Switch Players has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof SwitchPlayers);
        assertTrue(((SwitchPlayers) event).getMinute() == 1);
        assertTrue(((SwitchPlayers) event).getTeam().equals(firstTeam));
        assertTrue(((SwitchPlayers) event).getEnteringPlayer().equals(player2));
        assertTrue(((SwitchPlayers) event).getExitingPlayer().equals(player1));

        UIController.setSelector(10314); //0,6,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
    }


    @Test
    public void updateGameEventsSuccessInjuryITest() {
        SystemUser systemUser = getSystemUserReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1", "Name1", 0));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(10314); //0,6,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Penalty has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof Injury);
        assertTrue(((Injury) event).getMinute() == 1);
        assertTrue(((Injury) event).getInjuredPlayer().equals(player1));
    }

    @Test
    public void updateGameEventsSuccessInjury2ITest() {
        SystemUser systemUser = getSystemUserReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        SystemUser arSystemUser = new SystemUser("arSystemUser", "arUser");
        new AssociationRepresentative(arSystemUser);
        new TeamOwner(arSystemUser);
        TeamOwner toRole = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
        Team firstTeam = new Team("Hapoel Beit Shan", toRole);
        Team secondTeam = new Team("Hapoel Beer Sheva", toRole);

        Game game = new Game(new Stadium("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new Player(new SystemUser("UserName1", "Name1"), new Date(2001, 01, 01));
        firstTeam.addTeamPlayer(toRole, player1);

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(10314); //0,6,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Penalty has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof Injury);
        assertTrue(((Injury) event).getMinute() == 1);
        assertTrue(((Injury) event).getInjuredPlayer().equals(player1));
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