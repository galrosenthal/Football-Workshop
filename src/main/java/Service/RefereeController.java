package Service;

import Domain.Game.Game;
import Domain.Logger.Event;
import Domain.Users.Referee;
import Domain.Users.RoleTypes;
import Domain.Users.SystemUser;
import com.vaadin.flow.component.UI;

import java.util.ArrayList;
import java.util.List;

public class RefereeController {

    public static boolean updateGameEvents(SystemUser systemUser) {
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

        String eventType = getEventTypeByChoice();

        switch (eventType){
            case "Red Card":
                
        }

        return true;
    }

    public static void showExistingEvents(Game chosenGame) {
        List<String> gameEventsStringList = chosenGame.getGameEventsStringList();
        StringBuilder stringBuilder = new StringBuilder();
        if(gameEventsStringList.isEmpty()){
            stringBuilder.append("the chosen game doesn't have events yet");
        }else {
            stringBuilder.append("the chosen game already have the following events:");
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
        int Index;
        do {
            Index = UIController.receiveInt("Choose a game number", gamesList);
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
}
