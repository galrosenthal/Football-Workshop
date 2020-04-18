package Domain.Users;

import Domain.Game.Team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerStub extends Player{
    private int selector;

    public PlayerStub(SystemUser systemUser) {
        this(systemUser, null);
    }

    public PlayerStub(SystemUser user, Date bdate) {
        super(user,bdate);
    }

    public void setSelector(int selector) {
        this.selector = selector;
    }

    @Override
    public boolean addAllProperties() {
        return true;
    }

    @Override
    public boolean addTeam(Team playTeam, TeamOwner teamOwner) {
        if(selector == 0)
        {
            return super.addTeam(playTeam,teamOwner);
        }

        return true;
    }
}
