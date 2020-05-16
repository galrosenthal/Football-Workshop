package Domain.Game;

import Domain.Users.Referee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleMatch {
    private Team homeTeam;
    private Team awayTeam;
    private Stadium stadium;
    private Date matchDate;
    private List<Referee> referees;

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

    public boolean addReferee(Referee referee){
        if(!referees.contains(referee)){
            referees.add(referee);
            return true;
        }
        return false;
    }

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
