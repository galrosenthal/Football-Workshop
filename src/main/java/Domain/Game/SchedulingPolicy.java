package Domain.Game;

import Domain.EntityManager;

import java.util.Objects;

public class SchedulingPolicy {
    private int gamesPerSeason;
    private int gamesPerDay;
    private int minimumRestDays;


    public SchedulingPolicy(int gamesPerSeason, int gamesPerDay, int minRest) {
        this.gamesPerSeason = gamesPerSeason;
        this.gamesPerDay = gamesPerDay;
        this.minimumRestDays = minRest;
    }

    /**
     * Returns the default scheduling policy of: gamesPerSeason=2, gamesPerDay=2, minimumRestDays=2
     *
     * @return - SchedulingPolicy - The default scheduling policy
     */
    public static SchedulingPolicy getDefaultSchedulingPolicy() {
        if (!EntityManager.getInstance().doesSchedulingPolicyExists(2, 2, 2)) {
            SchedulingPolicy schedulingPolicy = new SchedulingPolicy(2, 2, 2);
            EntityManager.getInstance().addSchedulingPolicy(schedulingPolicy);
        }
        return EntityManager.getInstance().getSchedulingPolicy(2, 2, 2);
    }

    /**
     * Checks if a given arguments match this policy's fields
     *
     * @param gamesPerSeason - int - positive integer
     * @param gamesPerDay    - int - positive integer
     * @param minRest        - int - non-negative integer
     * @return - boolean - true if all the params match this fields
     */
    public boolean equals(int gamesPerSeason, int gamesPerDay, int minRest) {
        return gamesPerSeason == this.gamesPerSeason &&
                gamesPerDay == this.gamesPerDay &&
                minRest == this.minimumRestDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchedulingPolicy)) return false;
        SchedulingPolicy that = (SchedulingPolicy) o;
        return gamesPerSeason == that.gamesPerSeason &&
                gamesPerDay == that.gamesPerDay &&
                minimumRestDays == that.minimumRestDays;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gamesPerSeason, gamesPerDay, minimumRestDays);
    }
}
