package Domain.Game;

import Domain.EntityManager;
import java.util.Objects;

public class PointsPolicy {
    private int victoryPoints;
    private int lossPoints;
    private int tiePoints;

    public PointsPolicy(int victoryPoints, int lossPoints, int tiePoints) {
        this.victoryPoints = victoryPoints;
        this.lossPoints = lossPoints;
        this.tiePoints = tiePoints;
    }

    /**
     * Returns the default Points policy of: victory=3, loss=0, tie=1
     *
     * @return - PointsPolicy - The default points policy
     */
    public static PointsPolicy getDefaultPointsPolicy() {
        if (!EntityManager.getInstance().doesPointsPolicyExists(3, 0, 1)) {
            PointsPolicy pointsPolicy = new PointsPolicy(3, 0, 1);
            EntityManager.getInstance().addPointsPolicy(pointsPolicy);
        }
        return EntityManager.getInstance().getPointsPolicy(3, 0, 1);
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getLossPoints() {
        return lossPoints;
    }

    public int getTiePoints() {
        return tiePoints;
    }

    /**
     * Calculates and returns the points for each team for a given game.
     *
     * @param game - Game - A game played and
     * @return - Points -  The points for each team for a given game
     */
    public Points getPoints(Game game) {
        Score gameScore = game.getScore();
        int homePoints, awayPoints;
        if (gameScore.getHomeTeamGoalCount() > gameScore.getAwayTeamGoalCount()) {
            //Home Won
            homePoints = victoryPoints;
            awayPoints = lossPoints;
        } else if (gameScore.getHomeTeamGoalCount() < gameScore.getAwayTeamGoalCount()) {
            //Away Won
            homePoints = lossPoints;
            awayPoints = victoryPoints;
        } else {
            //Tie
            homePoints = tiePoints;
            awayPoints = tiePoints;
        }

        return new Points(gameScore.getHomeTeam(), gameScore.getAwayTeam(), homePoints, awayPoints);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointsPolicy)) return false;
        PointsPolicy that = (PointsPolicy) o;
        return victoryPoints == that.victoryPoints &&
                lossPoints == that.lossPoints &&
                tiePoints == that.tiePoints;
    }

    /**
     * Checks if a given arguments match this policy's fields
     *
     * @param victoryPoints - int - positive integer
     * @param lossPoints    - int - negative integer or zero
     * @param tiePoints     - int - integer
     * @return - boolean - true if all the params match this fields
     */
    public boolean equals(int victoryPoints, int lossPoints, int tiePoints) {
        return victoryPoints == this.victoryPoints &&
                lossPoints == this.lossPoints &&
                tiePoints == this.tiePoints;
    }

    @Override
    public int hashCode() {
        return Objects.hash(victoryPoints, lossPoints, tiePoints);
    }

    @Override
    public String toString() {
        return "victoryPoints=" + victoryPoints + ", lossPoints=" + lossPoints +", tiePoints=" + tiePoints;
    }
}
