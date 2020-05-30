package Domain.Users;

import Domain.EntityManager;
import Domain.Game.Game;
import Domain.Game.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Referee extends Role {

    private RefereeQualification training;
    private List<Game> games;
    private List<Season> seasons;

    /**
     * Constructor
     *
     * @param systemUser - SystemUser - The system user to add the new role to
     * @param addToDB    - boolean - Whether to add the new role to the database
     * @param training   - RefereeQualification - The training of the referee
     */
    public Referee(SystemUser systemUser, RefereeQualification training, boolean addToDB) {
        super(RoleTypes.REFEREE, systemUser);
        this.training = training;
        this.games = new ArrayList<>();
        this.seasons = new ArrayList<>();
        if (addToDB) {
            EntityManager.getInstance().addRole(this);
        }
    }

    /**
     * Getter for this referee's training
     *
     * @return - RefereeQualification - this referee's training
     */
    public RefereeQualification getTraining() {
        return training;
    }

    /**
     * Returns a list of all the seasons that this referee is assigned to
     *
     * @return - List<Season> - A list of all the seasons that this referee is assigned to
     */
    public List<Season> getSeasons() {
        if (this.seasons.isEmpty()) {
            this.seasons = EntityManager.getInstance().getRefereeSeasons(this);
        }
        return this.seasons;
        //TODO:Pull From DB
    }

    /**
     * Checks if the referee has future games to judge.
     *
     * @return - true if the referee is connected to future games.
     */
    public boolean hasFutureGames() {
        /*
        if (this.games.isEmpty()) {

            //Pull games from DB
            List<Game> refereeGames = EntityManager.getInstance().getRefereeGames(this);
            for (Game game : refereeGames) {
                if (!this.games.contains(game)) {
                    this.games.add(game);
                }
            }
        }
        */
        HashMap<String, Boolean> gamesStatus = EntityManager.getInstance().getRefereeGamesStatus(this);
        for (Map.Entry<String, Boolean> hasGameFinished : gamesStatus.entrySet()) {
            if (!hasGameFinished.getValue()) {
                return true;
            }
        }
        for (Game game : getGames()) {
            if (!game.hasFinished()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Un-assigns the referee role from all seasons
     */
    public void unAssignFromAllSeasons() {
        for (Season season : getSeasons()) {
            season.unAssignReferee(this);
        }
        //EntityManager.getInstance().unAssignRefereeFromAllSeasons(this); //Check:Should be covered with CASCADE
        this.seasons = new ArrayList<>();
    }

    /**
     * Un-assigns the referee role from all games
     */
    public void unAssignFromAllGames() {
        for (Game game : this.games) {
            game.unAssignReferee(this);
        }
        //EntityManager.getInstance().unAssignRefereeFromAllGames(this); //Check:Should be covered with CASCADE
        this.seasons = new ArrayList<>();
    }

    /**
     * Adds to this referee role the given season
     *
     * @param chosenSeason - Season - a season which doesn't contains this referee to be added
     */
    public void assignToSeason(Season chosenSeason) {
        if (chosenSeason != null) {
            getSeasons().add(chosenSeason);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Referee) {
            Referee other = (Referee) obj;
            if (this == other || this.systemUser.equals(other.systemUser)) {
                return true;
            }
        }
        return false;
    }

    public List<Game> getGames() {
        if (this.games.isEmpty()) {
            this.games = EntityManager.getInstance().getRefereeGames(this);
        }
        return this.games;
    }

    public void addGame(Game game) {
        getGames().add(game);
        EntityManager.getInstance().addRefereeToGame(this, game);
    }

    public void removeGame(Game game) {
        getGames().remove(game);
        //removal from DB based on CASCADE
    }
}
