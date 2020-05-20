package Domain.Users;

import Domain.Game.Game;
import Domain.Game.Season;

import java.util.ArrayList;
import java.util.List;

public class Referee extends Role {

    private RefereeQualification training;
    private List<Game> games;
    private List<Season> seasons;


    public Referee(SystemUser systemUser, RefereeQualification training) {
        super(RoleTypes.REFEREE, systemUser);
        this.training = training;
        this.games = new ArrayList<>();
        this.seasons = new ArrayList<>();
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
        return new ArrayList<>(seasons);
    }

    /**
     * Checks if the referee has future games to judge.
     *
     * @return - true if the referee is connected to future games.
     */
    public boolean hasFutureGames() {
        //TODO:Checks if the referee has future games to judge.
        return false;
    }

    /**
     * Un-assigns the referee role from all seasons
     */
    public void unAssignFromAllSeasons() {
        for (Season season : this.seasons) {
            season.unAssignReferee(this);
        }
        this.seasons = new ArrayList<>();
    }

    /**
     * Adds to this referee role the given season
     *
     * @param chosenSeason - Season - a season which doesn't contains this referee to be added
     */
    public void assignToSeason(Season chosenSeason) {
        if (chosenSeason != null) {
            this.seasons.add(chosenSeason);
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
        return this.games;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public void removeGame(Game game){
        this.games.remove(game);
    }
}
