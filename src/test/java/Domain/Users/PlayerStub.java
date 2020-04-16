package Domain.Users;

import java.util.Date;

public class PlayerStub extends Player{
    private int selector;
    public PlayerStub(SystemUser user, Date bdate, int selector) {
        super(user,bdate);
        this.selector = selector;
    }





}
