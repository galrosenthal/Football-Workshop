package Domain.Game;

import Domain.Users.Referee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that represent a scheduled match.
 */
public class ScheduleMatch {
    private Team homeTeam; /** home team */
    private Team awayTeam; /** away team */
    private Stadium stadium; /** the stadium the match is taken place at */
    private Date matchDate; /** When the match starts*/
    private List<Referee> referees; /** referees assigned to the match*/

    public ScheduleMatch(Team homeTeam, Team awayTeam, Date matchDate) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.referees = new ArrayList<>();
    }

    public ScheduleMatch(Team homeTeam, Team awayTeam, Stadium stadium, Date matchDate) {
        this(homeTeam,awayTeam,matchDate);
        this.stadium = stadium;
    }

    public ScheduleMatch(Team homeTeam, Team awayTeam, Stadium stadium, Date matchDate, List<Referee> referees) {
        this(homeTeam,awayTeam,stadium,matchDate);
        this.referees = referees;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public List<Referee> getReferees() {
        return referees;
    }

    /**
     * Add a referee to the list of referees if he isn't there already.
     * @param referee Referee to add.
     * @return True if the referee was added, else false.
     */
    public boolean addReferee(Referee referee){
        if(!referees.contains(referee)){
            referees.add(referee);
            return true;
        }
        return false;
    }

    /**
     * @return String that shows the details of the scheduled match.
     */
    @Override
    public String toString() {
        String str = homeTeam.getTeamName()+" vs. "+awayTeam.getTeamName()
                +". Stadium: "+stadium.getName() +". Date: "+matchDate +". Referees:";
        if(referees.isEmpty()){
            str += "Not enough referees in season.";
        }
        else{
            for(Referee ref : referees){
                str += " "+ref.getSystemUser().getName() +",";
            }
        }
        str = str.substring(0,str.length()-1);
        str += ".";
        return str;
    }
}
