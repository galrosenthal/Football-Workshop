package Users;

import Game.Team;

import java.util.ArrayList;
import java.util.List;

public class Fan extends Registered {
    protected List<Team> fanTeams = new ArrayList<Team>();
    protected List<PersonalPage> followedPages = new ArrayList<PersonalPage>();

    public Fan(String username, String pass, String name) {
        super(RegisteredTypes.FAN,username,pass,name);
    }

    public List<Team> getFanTeams() {
        return fanTeams;
    }

    public List<PersonalPage> getFollowedPages() {
        return followedPages;
    }

    public boolean addTeamToFan(Team t1){
        if(t1 != null)
        {
            if(!this.fanTeams.contains(t1))
            {
                this.fanTeams.add(t1);
            }
            return true;
        }
        return false;
    }


    public boolean addPageToFollow(PersonalPage pp1)
    {
        if(pp1 != null)
        {
            if(!this.followedPages.contains(pp1))
            {
                this.followedPages.add(pp1);
            }
            return true;
        }
        return false;
    }

    public boolean removePageFromFollow(PersonalPage pp1)
    {
        if(pp1 != null)
        {
            if(followedPages.contains(pp1))
            {
                return followedPages.remove(pp1);
            }
            return true;
        }
        return false;
    }

    public boolean removeTeamFromFan(Team t1)
    {
        if(t1 != null){
            if(fanTeams.contains(t1))
            {
                return fanTeams.remove(t1);
            }
            return true;
        }
        return false;
    }

}
