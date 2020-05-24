package Domain.Logger;

import Domain.Users.Player;

import java.util.Date;

public class GameEnd extends Event  {
    private Date endDate;
    public GameEnd(Date endDate, int minute) {
        super(minute);
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return super.toString() +" GameEnd endDate=" + endDate+".";
    }
}
