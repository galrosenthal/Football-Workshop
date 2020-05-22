package Domain.Reports;

import Domain.Alert;
import Domain.EntityManager;
import Domain.Game.Game;
import Domain.Game.Score;
import Domain.Subject;
import Domain.Users.Referee;
import Domain.Users.SystemUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GameReport extends Report implements Subject, Observer {

    Game game;
    List<SystemUser> fans;

    /**
     *
     * constructor
     * @param game
     */
    public GameReport(Game game) {
        this.game = game;
        this.fans = new ArrayList<>();
        this.game.addObserver(this);
    }

    /**
     * Add fan to fans list
     * @param systemUser - system user who wants to get alert of this game
     * @return true - if the fan added successfully to fans list and false otherwise
     */
    public boolean addSubscriber(SystemUser systemUser)
    {
        if(fans.contains(systemUser))
        {
            return false;
        }
        else
        {
            fans.add(systemUser);
            return true;
        }
    }

    /**
     * notify fans and referees - game has been start
     */
    public void getGameDate() {
        if(java.time.LocalDate.now().equals(this.game.getGameDate())) {
            //notify referees and fans
            notifyReferees("Game has been started");
            notifyFans("Game has been started");
        }
    }

    /**
     * notify fans when game score has been updated
     *
     */
    public void setScore(Score score) {
        //notify fans!
        notifyFans("Score has been update: "+ score);

    }


    /**
     * notify referees
     */
    private void notifyReferees(String alert)
    {
        List<SystemUser> systemUsers = new ArrayList<>();
        List<Referee> referees = new ArrayList<>();
        for (int i = 0; i < referees.size(); i++) {
            systemUsers.add(referees.get(i).getSystemUser());
        }
        notifyObserver(systemUsers , alert);
    }

    /**
     * notify fans
     */
    private void notifyFans(String alert)
    {
        notifyObserver(fans , alert);

    }

    @Override
    public void notifyObserver(List<SystemUser> systemUsers, String alert) {
        Alert alertInstance = Alert.getInstance();
        alertInstance.update(systemUsers , alert);

    }

    @Override
    public void update(Observable o, Object arg) {


    }
}
