package Service;

import Domain.EntityManager;
import Domain.Game.Game;
import Domain.Game.Stadium;
import Domain.Game.Team;
import Domain.Logger.Event;
import Domain.Users.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RefereeController {

    public static boolean updateGameEvents(SystemUser systemUser) {
        createGameForTet();
        if (!systemUser.isType(RoleTypes.REFEREE)) {
            return false;
        }
        Referee refereeRole = (Referee) systemUser.getRole(RoleTypes.REFEREE);
        Game chosenGame = null;
        try {
            chosenGame = getRefereeNotFinishedGameByChoice(refereeRole);
        } catch (Exception e) {
            UIController.showNotification(e.getMessage());
            return false;
        }

        //Show existing events
        showExistingEvents(chosenGame);

        //Get new event type
        String eventType = getEventTypeByChoice();

        //Adding the new event
        try {
            switch (eventType) {
                case "Red Card":
                case "Yellow Card"://player,min
                    addCardEvent(eventType, chosenGame);
                    break;
                case "Goal":
                    addGoalEvent(chosenGame);
                    break;
                case "Offside":
                    addOffsideEvent(chosenGame);
                    break;
                case "Penalty":
                    addPenaltyEvent(chosenGame);
                    break;
                case "Switch Players":
                    addSwitchPlayersEvent(chosenGame);
                    break;
                case "Injury":
                    addInjuryEvent(chosenGame);
                    break;
            }
        } catch (Exception e) {
            UIController.showNotification(e.getMessage());
            return false;
        }

        UIController.showNotification("The new " + eventType + " has been added successfully");
        return true;
    }

    private static void createGameForTet() {
        SystemUser systemUser = EntityManager.getInstance().getUser("Administrator");

        Referee referee = (Referee) systemUser.getRole(RoleTypes.REFEREE);

        SystemUser arSystemUser = new SystemUser("arSystemUser", "arUser");
        new AssociationRepresentative(arSystemUser);
        new TeamOwner(arSystemUser);
        TeamOwner toRole = (TeamOwner) arSystemUser.getRole(RoleTypes.TEAM_OWNER);
        Team firstTeam = new Team("Hapoel Beit Shan", toRole);
        Team secondTeam = new Team("Hapoel Beer Sheva", toRole);

        Game game = new Game(new Stadium("staName", "staLoca"), firstTeam, secondTeam, new Date(2020, 01, 01), new ArrayList<>());
        Player player1 = new Player(new SystemUser("AviCohen","Avi Cohen"),new Date(2001, 01, 01));
        firstTeam.addTeamPlayer(toRole,player1);

        game.addReferee(referee);
        referee.addGame(game);
    }


    /**
     * Receives an integer from the user for the minute the event occurred.
     *
     * @return - int - a non negative integer
     */
    private static int getMinuteByChoice() {
        int minute = -1;
        do {
            minute = UIController.receiveInt("Enter the minute of the event (positive integer)");
        } while (minute < 0);
        return minute;
    }

    /**
     * Receives a game and returns a player who plays in the game based on the user selection
     *
     * @param game - Game - a game to chose a player from
     * @return - Player - the player the user chose
     */
    private static Player getPlayerFromGameByChoice(Game game, String message) {
        List<Player> gamePlayers = game.getPlayers();
        List<String> playersList = new ArrayList<>();
        for (int i = 0; i < gamePlayers.size(); i++) {
            playersList.add(gamePlayers.get(i).getAssetName());
        }
        int Index;
        do {
            Index = UIController.receiveInt(message, playersList);
        } while (!(Index >= 0 && Index < gamePlayers.size()));

        return gamePlayers.get(Index);
    }

    private static Team getTeamFromGameByChoice(Game game, String message) {
        List<Team> gameTeams = game.getTeams();
        List<String> teamsList = new ArrayList<>();
        for (int i = 0; i < gameTeams.size(); i++) {
            teamsList.add(gameTeams.get(i).getTeamName());
        }
        int Index;
        do {
            Index = UIController.receiveInt(message, teamsList);
        } while (!(Index >= 0 && Index < gameTeams.size()));

        return gameTeams.get(Index);
    }


    public static void showExistingEvents(Game chosenGame) {
        List<String> gameEventsStringList = chosenGame.getGameEventsStringList();
        StringBuilder stringBuilder = new StringBuilder();
        if (gameEventsStringList.isEmpty()) {
            stringBuilder.append("the chosen game doesn't have events yet");
        } else {
            stringBuilder.append("the chosen game already have the following events:\n");
            for (String string : gameEventsStringList) {
                stringBuilder.append(string + "\n");
            }
        }
        UIController.showNotification(stringBuilder.toString());
    }

    private static Game getRefereeNotFinishedGameByChoice(Referee refereeRole) throws Exception {
        List<Game> gamesOfReferee = refereeRole.getGames();
        if (gamesOfReferee == null || gamesOfReferee.isEmpty()) {
            throw new Exception("There are no games for this referee");
        }
        List<String> gamesList = new ArrayList<>();
        for (int i = 0; i < gamesOfReferee.size(); i++) {
            if (!gamesOfReferee.get(i).hasFinished()) { //only games which didn't finish
                gamesList.add(gamesOfReferee.get(i).toString());
            }
        }
        if (gamesList.isEmpty()) {
            throw new Exception("There are no ongoing games for this referee");
        }
        int Index;
        do {
            Index = UIController.receiveInt("Choose a game", gamesList);
        } while (!(Index >= 0 && Index < gamesOfReferee.size()));

        return gamesOfReferee.get(Index);
    }

    private static String getEventTypeByChoice() {
        List<String> eventType = Event.getEventsTypes();
        int Index;
        do {
            Index = UIController.receiveInt("Choose an event number you would like to add", eventType);
        } while (!(Index >= 0 && Index < eventType.size()));

        return eventType.get(Index);
    }

    /**
     * Receives the necessary arguments from the user and logs a new card event
     *
     * @param cardType - String - The type of the card
     * @param game     - Game - The game to log the event to
     */
    private static void addCardEvent(String cardType, Game game) {
        Player player = getPlayerFromGameByChoice(game, "Choose a player");
        int minute = getMinuteByChoice();
        game.addCard(cardType, player, minute);
    }

    /**
     * Receives the necessary arguments from the user and logs a new goal event
     *
     * @param game - Game - The game to log the event to
     */
    private static void addGoalEvent(Game game) throws Exception {
        Team scored = getTeamFromGameByChoice(game, "Choose a team who scored");
        Team scoredOn = getTeamFromGameByChoice(game, "Choose a team who got scored on");
        //todo:Add Player who scored
        int minute = getMinuteByChoice();

        game.addGoal(scored, scoredOn, minute);
    }

    /**
     * Receives the necessary arguments from the user and logs a new offside event
     *
     * @param game - Game - The game to log the event to
     */
    private static void addOffsideEvent(Game game) {
        Team teamWhoCommitted = getTeamFromGameByChoice(game, "Choose a team which committed the offside");
        int minute = getMinuteByChoice();
        game.addOffside(teamWhoCommitted, minute);
    }

    /**
     * Receives the necessary arguments from the user and logs a new penalty event
     *
     * @param game - Game - The game to log the event to
     */
    private static void addPenaltyEvent(Game game) {
        Team teamWhoCommitted = getTeamFromGameByChoice(game, "Choose a team which committed the penalty");
        int minute = getMinuteByChoice();
        game.addPenalty(teamWhoCommitted, minute);
    }

    /**
     * Receives the necessary arguments from the user and logs a new player switching  event
     *
     * @param game - Game - The game to log the event to
     */
    private static void addSwitchPlayersEvent(Game game) {
        Team teamWhoCommitted = getTeamFromGameByChoice(game, "Choose a team which switched players");
        Player enteringPlayer = getPlayerFromGameByChoice(game, "Choose the entering player");
        Player exitingPlayer = getPlayerFromGameByChoice(game, "Choose the exiting player");
        int minute = getMinuteByChoice();
        game.addSwitchPlayers(teamWhoCommitted, enteringPlayer, exitingPlayer, minute);
    }

    /**
     * Receives the necessary arguments from the user and logs a new injury event
     *
     * @param game - Game - The game to log the event to
     */
    private static void addInjuryEvent(Game game) {
        Player player = getPlayerFromGameByChoice(game, "Choose a player who got injured");
        int minute = getMinuteByChoice();
        game.addInjury(player, minute);
    }

    public static void displayScheduledGames(SystemUser systemUser) throws Exception {
        if (!systemUser.isType(RoleTypes.REFEREE)) {
            throw new Exception("The user is not a referee");
        }
        Referee refereeRole = (Referee) systemUser.getRole(RoleTypes.REFEREE);
        List<Game> gamesOfReferee = refereeRole.getGames();
        if (gamesOfReferee == null || gamesOfReferee.isEmpty() ) {
            throw new Exception("There are no games for this referee");
        }

        List<String> gamesByString = new ArrayList<>();

        for (Game game: gamesOfReferee){
            gamesByString.add(game.toString());
        }

        UIController.showModal(gamesByString);
    }
}
