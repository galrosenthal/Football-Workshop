package Domain.Logger;

import Domain.Users.Player;

public abstract class Card extends Event {
    protected Player offender;
    protected int minute;

    public Card(Player offender, int minute) {
        this.offender = offender;
        this.minute = minute;
    }
}
