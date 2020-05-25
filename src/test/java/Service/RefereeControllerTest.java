package Service;

import Domain.EntityManager;
import Domain.Game.*;
import Domain.GameLogger.*;
import Domain.Users.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class RefereeControllerTest {
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
        game.setEndDate(new Date()); // end the game, for the test

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
        Player player1 = new PlayerStub(new SystemUserStub("AviCohen","Name1",10312 ));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2","Name2",0 ));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        EntityManager.getInstance().addTeam(firstTeam);
        EntityManager.getInstance().addTeam(secondTeam);
        EntityManager.getInstance().addUser(player1.getSystemUser());
        EntityManager.getInstance().addUser(player2.getSystemUser());

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(1031110);//0,0,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Red Card has been added successfully

        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof RedCard);
        assertTrue(((RedCard) event).getMinute()==1);
        assertTrue(((RedCard) event).getOffender().equals(player1));
    }
    @Test
    public void updateGameEventsSuccessRedCard2ITest() {
        SystemUser systemUser = getSystemUserVarReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("AviCohen","Name1",10312 ));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2","Name2",0 ));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        EntityManager.getInstance().addTeam(firstTeam);
        EntityManager.getInstance().addTeam(secondTeam);
        EntityManager.getInstance().addUser(player1.getSystemUser());
        EntityManager.getInstance().addUser(player2.getSystemUser());

        UIController.setSelector(1031110);//0,0,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Red Card has been added successfully

        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof RedCard);
        assertTrue(((RedCard) event).getMinute()==1);
        assertTrue(((RedCard) event).getOffender().equals(player1));
    }

    private SystemUser getSystemUserVarReferee() {
        SystemUser systemUser = new SystemUser("username", "name");
        systemUser.addNewRole(new Referee(systemUser,RefereeQualification.VAR_REFEREE));
        return systemUser;
    }

    private SystemUser getSystemUserMainReferee() {
        SystemUser systemUser = new SystemUser("username2", "name2");
        systemUser.addNewRole(new Referee(systemUser,RefereeQualification.MAIN_REFEREE));
        return systemUser;
    }

    @Test
    public void updateGameEventsSuccessEndGameITest() {
        SystemUser systemUser = getSystemUserVarReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        SystemUser systemUser2 = getSystemUserMainReferee();
        Referee mainRef = (Referee) systemUser2.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("AviCohen", "Name1", 10312));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2", "Name2", 0));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        game.addReferee(mainRef);
        referee.addGame(game);
        mainRef.addGame(game);

        EntityManager.getInstance().addTeam(firstTeam);
        EntityManager.getInstance().addTeam(secondTeam);
        EntityManager.getInstance().addUser(player1.getSystemUser());
        EntityManager.getInstance().addUser(player2.getSystemUser());

        UIController.setSelector(103711); //0,7,true,89(90th minute)
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Game End has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof GameEnd);
        assertEquals(90,((GameEnd) event).getMinute());
        assertTrue(game.hasFinished());
        //"There are no ongoing games for this referee"
        assertFalse(RefereeController.updateGameEvents(systemUser));

        //Now main referee add another event after the game ended, 5 hours have not passed
        UIController.setSelector(10314); //0,6,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser2));
        //The new Injury has been added successfully
        Event event2 = game.getEventsLogger().getGameEvents().get(1);
        assertTrue(event2 instanceof Injury);
        assertEquals(1,((Injury) event2).getMinute());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1400);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 05);
        calendar.set(Calendar.HOUR_OF_DAY,20);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,0);
        Date currDate = calendar.getTime();
        game.setEndDate(currDate);
        //Now 600 years passed. cannot add events at all.
        //"There are no ongoing games for this referee"
        assertFalse(RefereeController.updateGameEvents(systemUser2));
    }

    @Test
    public void updateGameEventsSuccessYellowCardITest() {
        SystemUser systemUser = getSystemUserVarReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("AviCohen","Name1",10312 ));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2","Name2",0 ));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);


        EntityManager.getInstance().addTeam(firstTeam);
        EntityManager.getInstance().addTeam(secondTeam);
        EntityManager.getInstance().addUser(player1.getSystemUser());
        EntityManager.getInstance().addUser(player2.getSystemUser());

        UIController.setSelector(1032110); //0,1,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Yellow Card has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof YellowCard);
        assertTrue(((YellowCard) event).getMinute()==1);
        assertTrue(((YellowCard) event).getOffender().equals(player1));
    }

    @Test
    public void updateGameEventsSuccessGoalITest() {
        SystemUser systemUser = getSystemUserVarReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1","Name1",0 ));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2","Name2",0 ));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        EntityManager.getInstance().addTeam(firstTeam);
        EntityManager.getInstance().addTeam(secondTeam);
        EntityManager.getInstance().addUser(player1.getSystemUser());
        EntityManager.getInstance().addUser(player2.getSystemUser());

        UIController.setSelector(1036); //0,2,1,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Goal has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof Goal);
        assertTrue(((Goal) event).getMinute()==1);
        assertTrue(((Goal) event).getScoringTeam().equals(secondTeam));
        assertTrue(((Goal) event).getScoredOnTeam().equals(firstTeam));
    }

    @Test
    public void updateGameEventsSuccessOffsideITest() {
        SystemUser systemUser = getSystemUserVarReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1","Name1",0 ));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2","Name2",0 ));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        EntityManager.getInstance().addTeam(firstTeam);
        EntityManager.getInstance().addTeam(secondTeam);
        EntityManager.getInstance().addUser(player1.getSystemUser());
        EntityManager.getInstance().addUser(player2.getSystemUser());

        UIController.setSelector(1038); //0,3,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Offside has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof Offside);
        assertTrue(((Offside) event).getMinute()==1);
        assertTrue(((Offside) event).getTeamWhoCommitted().equals(firstTeam));
    }

    @Test
    public void updateGameEventsSuccessPenaltyITest() {
        SystemUser systemUser = getSystemUserVarReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("UserName1","Name1",0 ));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2","Name2",0 ));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        EntityManager.getInstance().addTeam(firstTeam);
        EntityManager.getInstance().addTeam(secondTeam);
        EntityManager.getInstance().addUser(player1.getSystemUser());
        EntityManager.getInstance().addUser(player2.getSystemUser());

        UIController.setSelector(10310); //0,4,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Penalty has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof Penalty);
        assertTrue(((Penalty) event).getMinute()==1);
        assertTrue(((Penalty) event).getTeamWhoCommitted().equals(firstTeam));
    }

    @Test
    public void updateGameEventsSuccessSwitchPlayersITest() {
        SystemUser systemUser = getSystemUserVarReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("AviCohen","Name1",10312 ));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2","Name2",10312 ));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        game.addReferee(referee);
        referee.addGame(game);

        EntityManager.getInstance().addTeam(firstTeam);
        EntityManager.getInstance().addTeam(secondTeam);
        EntityManager.getInstance().addUser(player1.getSystemUser());
        EntityManager.getInstance().addUser(player2.getSystemUser());

        UIController.setSelector(10312); //0,5,0,1,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Switch Players has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof SwitchPlayers);
        assertTrue(((SwitchPlayers) event).getMinute()==1);
        assertTrue(((SwitchPlayers) event).getTeam().equals(firstTeam));
        assertTrue(((SwitchPlayers) event).getEnteringPlayer().equals(player2));
        assertTrue(((SwitchPlayers) event).getExitingPlayer().equals(player1));

        UIController.setSelector(10314); //0,6,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
    }


    @Test
    public void updateGameEventsSuccessInjuryITest() {
        SystemUser systemUser = getSystemUserVarReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        TeamStub firstTeam = new TeamStub(9511);
        TeamStub secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new PlayerStub(new SystemUserStub("AviCohen","Name1",10312 ));
        Player player2 = new PlayerStub(new SystemUserStub("UserName2","Name2",0 ));
        firstTeam.addPlayer(player1);
        firstTeam.addPlayer(player2);

        EntityManager.getInstance().addTeam(firstTeam);
        EntityManager.getInstance().addTeam(secondTeam);
        EntityManager.getInstance().addUser(player1.getSystemUser());
        EntityManager.getInstance().addUser(player2.getSystemUser());

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(10314); //0,6,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Penalty has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof Injury);
        assertTrue(((Injury) event).getMinute()==1);
        assertTrue(((Injury) event).getInjuredPlayer().equals(player1));
    }
    @Test
    public void updateGameEventsSuccessInjury2ITest() {
        SystemUser systemUser = getSystemUserVarReferee();
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        SystemUser arSystemUser = new SystemUser("arSystemUser", "arUser");
        new AssociationRepresentative(arSystemUser);
        new TeamOwner(arSystemUser);
        TeamOwner toRole = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
        Team firstTeam = new Team("Hapoel Beit Shan", toRole);
        Team secondTeam = new Team("Hapoel Beer Sheva", toRole);

        Game game = new Game(new Stadium("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new Player(new SystemUser("AviCohen","Name1"),new Date(2001, 01, 01));
        firstTeam.addTeamPlayer(toRole,player1);

        game.addReferee(referee);
        referee.addGame(game);

        EntityManager.getInstance().addTeam(firstTeam);
        EntityManager.getInstance().addTeam(secondTeam);
        EntityManager.getInstance().addUser(player1.getSystemUser());

        UIController.setSelector(10314); //0,6,0,1
        assertTrue(RefereeController.updateGameEvents(systemUser));
        //The new Injury has been added successfully
        Event event = game.getEventsLogger().getGameEvents().get(0);
        assertTrue(event instanceof Injury);
        assertTrue(((Injury) event).getMinute()==1);
        assertTrue(((Injury) event).getInjuredPlayer().equals(player1));
    }

    @Test
    public void produceGameReportUTest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 0);
        assertFalse(RefereeController.produceGameReport(systemUser));
    }

    @Test
    public void produceGameReportITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 1031);
        assertFalse(RefereeController.produceGameReport(systemUser));
        //"There are no games for this referee"
    }

    @Test
    public void produceGameReport2ITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 1031);
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        Team firstTeam = new TeamStub(9511);
        Team secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());

        game.addReferee(referee);
        referee.addGame(game);

        assertFalse(RefereeController.produceGameReport(systemUser));
        //"There are no finished games for this referee"
    }

    @Test
    public void produceGameReport3ITest() {
        SystemUser systemUser = new SystemUserStub("stubUsername", "stub", 1031);
        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        Team firstTeam = new TeamStub(9511);
        Team secondTeam = new TeamStub(9512);
        Game game = new Game(new StadiumStub("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        game.setEndDate(new Date()); // end the game, for the test

        game.addReferee(referee);
        referee.addGame(game);

        UIController.setSelector(10411);//0,"."
        boolean succeeded = RefereeController.produceGameReport(systemUser);
        assertTrue(succeeded);
        if(succeeded){
            //delete the created report
            File dir = new File(".");
            File[] directoryListing = dir.listFiles();
            for (File file : directoryListing) {
                if(file.getName().startsWith("GameReport_stubTeam9511")){
                    assertTrue(file.delete());
                }
            }
        }

    }

    @After
    public void tearDown() throws Exception {
        EntityManager.getInstance().clearAll();
    }
}