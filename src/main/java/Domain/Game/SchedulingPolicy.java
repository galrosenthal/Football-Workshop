package Domain.Game;

import Domain.EntityManager;
import Domain.Users.Referee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    /**
     * Generates a schedule starting from the given start date
     * @param startDate - Date - the starting date of the schedule
     * @param teams - List<Team> - the teams
     * @param referees - List<Referee> - the referees
     * @return - List<ScheduleMatch> - the matches schedule
     * @throws Exception - throws if //todo: fill
     */
    public List<ScheduleMatch> generateSchedule(Date startDate, List<Team> teams, List<Referee> referees) throws Exception {
        //TODO: add more checks
        if(teams==null ||teams.isEmpty() || referees==null || referees.isEmpty()){
            throw new Exception("There is something wrong with the season's participants");
        }else if (false){ //TODO: Add checks on referee types

        }else if (false){ //TODO: Add checks on Teams

        }
        //TODO:Generate schedule


        return new ArrayList<>();

    }
}
