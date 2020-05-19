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
    private static List<String> getMinutesList() {
        List<String> list = new ArrayList<>();
        for(int i = 1; i <= 120; i++){
            list.add(""+i);
        }
        return list;
    }

    /**
     * Receives a game and returns a player who plays in the game based on the user selection
     *
     * @param game - Game - a game to chose a player from
     * @return - Player - the player the user chose
     */
    private static List<String> getPlayerNamesFromGame(Game game) {
        List<Player> gamePlayers = game.getPlayers();
        List<String> playersList = new ArrayList<>();
        for (int i = 0; i < gamePlayers.size(); i++) {
            playersList.add(gamePlayers.get(i).getAssetName());
        }
        return playersList;
    }

    private static List<String> getTeamNamesFromGame(Game game) {
        List<Team> gameTeams = game.getTeams();
        List<String> teamsList = new ArrayList<>();
        for (int i = 0; i < gameTeams.size(); i++) {
            teamsList.add(gameTeams.get(i).getTeamName());
        }
        return teamsList;
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
        String msg = "Choose a player received the card;Choose the minute of the event";
        String chosenArgs = UIController.receiveStringFromMultipleInputs(msg, getPlayerNamesFromGame(game), getMinutesList());
        String[] chosenArgsArray = chosenArgs.split(";");//0 - player 1 - minute
        Player player = (Player)EntityManager.getInstance().getUser(chosenArgsArray[0]).getRole(RoleTypes.PLAYER);
        int minute = Integer.parseInt(chosenArgsArray[1]);
        game.addCard(cardType, player, minute);
    }

    /**
     * Receives the necessary arguments from the user and logs a new goal event
     *
     * @param game - Game - The game to log the event to
     */
    private static void addGoalEvent(Game game) throws Exception {
        String msg = "Choose a team who scored;Choose a team who got scored on;Choose the player who scored;" +
                "Choose the minute of the event";
        String chosenArgs = UIController.receiveStringFromMultipleInputs(msg,
                getTeamNamesFromGame(game), getTeamNamesFromGame(game),getPlayerNamesFromGame(game),getMinutesList());
        String[] chosenArgsArray = chosenArgs.split(";");//0 - team, 1 - team, 2 - player 3 - minute
        Team teamScored = EntityManager.getInstance().getTeam(chosenArgsArray[0]);
        Team teamScoredOn = EntityManager.getInstance().getTeam(chosenArgsArray[1]);
        Player playerScored = (Player)EntityManager.getInstance().getUser(chosenArgsArray[2]).getRole(RoleTypes.PLAYER);
        int minute = Integer.parseInt(chosenArgsArray[3]);

        game.addGoal(teamScored, teamScoredOn, playerScored, minute);
    }

    /**
     * Receives the necessary arguments from the user and logs a new offside event
     *
     * @param game - Game - The game to log the event to
     */
    private static void addOffsideEvent(Game game) {
        String msg = "Choose a team which committed the offside;Choose the minute of the event";
        String chosenArgs = UIController.receiveStringFromMultipleInputs(msg, getTeamNamesFromGame(game),getMinutesList());
        String[] chosenArgsArray = chosenArgs.split(";");//0 - team, 1 - minute
        Team teamWhoCommitted = EntityManager.getInstance().getTeam(chosenArgsArray[0]);
        int minute = Integer.parseInt(chosenArgsArray[1]);
        game.addOffside(teamWhoCommitted, minute);
    }

    /**
     * Receives the necessary arguments from the user and logs a new penalty event
     *
     * @param game - Game - The game to log the event to
     */
    private static void addPenaltyEvent(Game game) {
        String msg = "Choose a team the penalty is in favor of;Choose the minute of the event";
        String chosenArgs = UIController.receiveStringFromMultipleInputs(msg, getTeamNamesFromGame(game),getMinutesList());
        String[] chosenArgsArray = chosenArgs.split(";");//0 - team, 1 - minute
        Team teamInFavor = EntityManager.getInstance().getTeam(chosenArgsArray[0]);
        int minute = Integer.parseInt(chosenArgsArray[1]);
        game.addPenalty(teamInFavor, minute);
    }

    /**
     * Receives the necessary arguments from the user and logs a new player switching  event
     *
     * @param game - Game - The game to log the event to
     */
    private static void addSwitchPlayersEvent(Game game) {
        String msg = "Choose a team which switched players;Choose the entering player;Choose the exiting player;" +
                "Choose the minute of the event";
        String chosenArgs = UIController.receiveStringFromMultipleInputs(msg, getTeamNamesFromGame(game),
        getPlayerNamesFromGame(game), getPlayerNamesFromGame(game), getMinutesList());
        String[] chosenArgsArray = chosenArgs.split(";");//0 - team, 1 - player1, 2 - player2, 3 - minute
        Team teamWhoCommitted = EntityManager.getInstance().getTeam(chosenArgsArray[0]);
        Player enteringPlayer = (Player)EntityManager.getInstance().getUser(chosenArgsArray[1]).getRole(RoleTypes.PLAYER);
        Player exitingPlayer = (Player)EntityManager.getInstance().getUser(chosenArgsArray[2]).getRole(RoleTypes.PLAYER);
        int minute = Integer.parseInt(chosenArgsArray[3]);
        game.addSwitchPlayers(teamWhoCommitted, enteringPlayer, exitingPlayer, minute);
    }

    /**
     * Receives the necessary arguments from the user and logs a new injury event
     *
     * @param game - Game - The game to log the event to
     */
    private static void addInjuryEvent(Game game) {
        String msg = "Choose a player who got injured;Choose the minute of the event";
        String chosenArgs = UIController.receiveStringFromMultipleInputs(msg, getPlayerNamesFromGame(game), getMinutesList());
        String[] chosenArgsArray = chosenArgs.split(";");//0 - player, 1 - minute
        Player player = (Player)EntityManager.getInstance().getUser(chosenArgsArray[0]).getRole(RoleTypes.PLAYER);
        int minute = Integer.parseInt(chosenArgsArray[1]);
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
