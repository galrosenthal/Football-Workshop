package Domain.Game;

import Domain.EntityManager;
import Domain.Users.Referee;
import com.google.common.collect.Sets;

import java.util.*;

public class SchedulingPolicy {
    private int gamesPerSeason; // number of rounds
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
        List<ScheduleMatch> schedule = new ArrayList<>();
        if(teams==null || referees==null ) {
            throw new Exception("error, there is something wrong with the season's participants");
        }
        if(teams.size() <= 1){
            throw new Exception("error, there are less than 2 teams in the season");
        }
        //check if every team has at least 1 stadium, and 11 players
        for(Team team : teams){
            if(team.getStadiums() == null || team.getStadiums().isEmpty()){
                throw new Exception("error, there are teams with no stadiums, like \""+team.getTeamName()+"\"");
            }
            if(team.getTeamPlayers().size() < 11){
                throw new Exception("error, there are teams with less than 11 players, like \""+team.getTeamName()+"\"");
            }
        }
        //shuffle the teams list in order to get random results in each activation.
        Collections.shuffle(teams);
        List<PairOfTeams> pairedTeams = createPairedTeams(teams);
        Date currGameDate = startDate;
        int currNumOfGamesInDay = 0;

        for(int i = 1; i <= gamesPerSeason; i++){//for each round
            //Matches that are in the current fixture
            List<PairOfTeams> pairsInCurrFixture = getPairsInFixtureAtRound(pairedTeams, i);
            while(!pairsInCurrFixture.isEmpty()) {
                //Now we can schedule the teams to the current fixture
                for (PairOfTeams pairOfTeams : pairsInCurrFixture) {
                    //check if we need to move the rest of the games to tomorrow
                    if (currNumOfGamesInDay >= gamesPerDay) {
                        currGameDate = getNextDate(currGameDate, 1);
                        currNumOfGamesInDay = 0;
                    }
                    Team homeTeam = pairOfTeams.getTeam1();
                    Team awayTeam = pairOfTeams.getTeam2();
                    schedule.add(new ScheduleMatch(homeTeam, awayTeam, currGameDate));
                    currNumOfGamesInDay++;
                }
                //Set the date of the next fixture's first match-day
                currGameDate = getNextDate(currGameDate, minimumRestDays);
                currNumOfGamesInDay = 0;
                pairsInCurrFixture = getPairsInFixtureAtRound(pairedTeams, i);
            }
        }
        assignStadiumsToSchedule(schedule);
        assignRefereesToSchedule(schedule, referees);
        return schedule;
    }

    /**
     * Get the biggest group of PairOfTeams at certain round, where no team appears more than once.
     * @param pairedTeams
     * @param round
     * @return
     */
    private List<PairOfTeams> getPairsInFixtureAtRound(List<PairOfTeams> pairedTeams, int round) {
        List<PairOfTeams> pairsInFixtureAtRound = new ArrayList<>();
        int pairsPerRound = pairedTeams.size() / gamesPerSeason;
        for(int i = pairsPerRound * (round-1); i < pairsPerRound + pairsPerRound * (round-1); i++){
            PairOfTeams pot = pairedTeams.get(i);
            if(!pot.isSelected() && !(isTeamInPairExistsInList(pairsInFixtureAtRound, pot))){
                pairsInFixtureAtRound.add(pot);
                pot.setSelected(true);
            }
        }
        return pairsInFixtureAtRound;
    }



    /**
     * Creates a List of PairOfTeams out of List of teams, which contains every pair possible
     * from the list of teams.
     * @param teams
     * @return
     */
    private List<PairOfTeams> createPairedTeams(List<Team> teams) {
        List<PairOfTeams> pairedTeams = new ArrayList<>();
        Set<Team> teamsAsSet = new LinkedHashSet<>();
        teamsAsSet.addAll(teams);
        Set<List<Team>> cartesianPairsOfTeams = Sets.cartesianProduct(teamsAsSet,teamsAsSet);
        int forFlipping = 0;
        for(List<Team> teamList : cartesianPairsOfTeams){
            Team team1 = teamList.get(0);
            Team team2 = teamList.get(1);
            if(!team1.equals(team2)){
                PairOfTeams pot = new PairOfTeams(team1, team2);
               if(!isPairExistsInList(pairedTeams,pot)) {
                   if(forFlipping % 2 == 0) {
                       pairedTeams.add(pot);
                   }
                   else{ //flip
                       pairedTeams.add(new PairOfTeams(team2, team1));
                   }
                   forFlipping ++;
               }
            }
        }
        List<PairOfTeams> copyOfPairedTeams = new ArrayList<>();
        copyOfPairedTeams.addAll(pairedTeams);
        for(int i = 2; i <= gamesPerSeason; i++){
            for(PairOfTeams copyPt: copyOfPairedTeams){
                if( i % 2 == 0){ //flip between home and away
                    pairedTeams.add(new PairOfTeams(copyPt.getTeam2(),copyPt.getTeam1()));
                }
                else{ // dont flip
                    pairedTeams.add(new PairOfTeams(copyPt.getTeam1(),copyPt.getTeam2()));
                }
            }
        }
        return pairedTeams;
    }

    /**
     * Checks if at least one team of a PairOfTeams is already exists in a list of PairOfTeams
     * @param pairsInList
     * @param pot
     * @return
     */
    private boolean isTeamInPairExistsInList(List<PairOfTeams> pairsInList, PairOfTeams pot){
        for(PairOfTeams pairInFixture : pairsInList){
            Team team1 = pairInFixture.getTeam1();
            Team team2 = pairInFixture.getTeam2();
            if(pot.containsTeam(team1) || pot.containsTeam(team2))
                return true;
        }
        return false;
    }

    /**
     * Checks if a PairOfTeams already exists in a list of PairOfTeams,
     * pairs with flipped teams are considered identical.
     * @param pairsInList
     * @param pot
     * @return
     */
    private boolean isPairExistsInList(List<PairOfTeams> pairsInList, PairOfTeams pot) {
        for(PairOfTeams pairInFixture : pairsInList){
            Team team1 = pairInFixture.getTeam1();
            Team team2 = pairInFixture.getTeam2();
            if(pot.containsTeam(team1) && pot.containsTeam(team2))
                return true;
        }
        return false;
    }


    /**
     * Get the next date of a given date, by days.
     * @param date the date to add days to.
     * @param days number of days to add to date.
     * @return
     */
    private Date getNextDate(Date date, int days){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
    /**
     * Assigns Stadiums to the schedule, the stadium is always the home team's stadium.
     * @param schedule List of ScheduleMatch
     */
    private void assignStadiumsToSchedule(List<ScheduleMatch> schedule) {
        for(ScheduleMatch match : schedule){
            match.setStadium(match.getHomeTeam().getStadiums().get(0));
        }
    }

    /**
     * Assigns referees to the schedule randomly with fair assignments.
     * Each schedule gets between 0 to 4 referees, according to the referees' list size.
     * Each referee can judge in maximum 1 game per match-day.
     * @param schedule List of ScheduleMatch
     * @param referees The Referees to assign
     */
    private void assignRefereesToSchedule(List<ScheduleMatch> schedule, List<Referee> referees) {
        if(referees.size() <= 0) {
            return;
        }
    //    int numOfRefsForEachMatch = Math.min(4, referees.size() - 1);
     //   numOfRefsForEachMatch = Math.max(1, numOfRefsForEachMatch);
        // shuffle the referee's list in order to get random results in each activation.
        Collections.shuffle(referees);
        List<ScheduleMatch> copyOfSchedule = new ArrayList<>();
        for(ScheduleMatch scheduleMatch: schedule)
            copyOfSchedule.add(scheduleMatch); //addAll didnt work

        int currIndexInRefs = 0;
        while(!copyOfSchedule.isEmpty()){
            List<ScheduleMatch> matchesInSameDay = getAndRemoveMatchesInTheSameDay(copyOfSchedule);
            int numOfRefsForEachMatch = Math.min(4, referees.size()/matchesInSameDay.size());
            for(ScheduleMatch match : matchesInSameDay) {
                for (int i = 1; i <= numOfRefsForEachMatch; i++) {
                    Referee refToCheck = referees.get(currIndexInRefs);
                    if(!match.getReferees().contains(refToCheck)
                      && !isRefInAnyMatch(matchesInSameDay , refToCheck)) {
                        match.addReferee(refToCheck);
                        currIndexInRefs = (currIndexInRefs + 1) % (referees.size());
                    }
                }
            }
        }


    }

    /**
     * Checks whether a referee is assigned to any match from a given matches list
     * @param matches
     * @return
     */
    private boolean isRefInAnyMatch(List<ScheduleMatch> matches, Referee refToCheck) {
        for(ScheduleMatch match: matches){
            if(match.getReferees().contains(refToCheck))
                return true;
        }
        return false;
    }

    /**
     * Start searching from the first match matches that are in the same day.
     * Stops when reaching a match that happens in different day.
     * Removes from the input list the matches that were found.
     * @param schedule
     * @return
     */
    private List<ScheduleMatch> getAndRemoveMatchesInTheSameDay(List<ScheduleMatch> schedule) {
        List<ScheduleMatch> matchesInTheSameDay = new ArrayList<>();
        if(schedule != null  && !schedule.isEmpty()) {
            Date dateToCompare = schedule.get(0).getMatchDate();
            for(ScheduleMatch scheduleMatch : schedule){
                Date matchDate = scheduleMatch.getMatchDate();
                if (dateToCompare.getYear() == matchDate.getYear() &&
                        dateToCompare.getMonth() == matchDate.getMonth() &&
                        dateToCompare.getDate() == matchDate.getDate()) {
                    matchesInTheSameDay.add(scheduleMatch);
                }
                else{
                    break;
                }
            }
        }
        for(ScheduleMatch scheduleMatch : matchesInTheSameDay){
            schedule.remove(scheduleMatch);
        }
        return matchesInTheSameDay;
    }

    public int getGamesPerSeason() {
        return gamesPerSeason;
    }

    public int getGamesPerDay() {
        return gamesPerDay;
    }

    public int getMinimumRestDays() {
        return minimumRestDays;
    }

    @Override
    public String toString() {
        return "Games Per Day = " + gamesPerDay + ", Games Per Season = " + gamesPerSeason +", Minimum rest days = " + minimumRestDays;
    }


    /**
     * Class that represents a pair of 2 teams.
     */
    private class PairOfTeams{
        private Team team1;
        private Team team2;
        private boolean isSelected; /** Whether the pair has been selected while scheduling matches */
        public PairOfTeams(Team team1, Team team2){
            this.team1 = team1;
            this.team2 = team2;
            isSelected = false;
        }
        public boolean containsTeam(Team teamToCheck){
            return team1.equals(teamToCheck) || team2.equals(teamToCheck);
        }

        public Team getTeam1() {
            return team1;
        }

        public Team getTeam2() {
            return team2;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PairOfTeams)) return false;
            PairOfTeams that = (PairOfTeams) o;
            return team1.equals(that.getTeam1()) && team2.equals(that.getTeam2());
        }
    }
}
