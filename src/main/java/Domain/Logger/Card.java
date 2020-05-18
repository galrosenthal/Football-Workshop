package Domain.Logger;

import Domain.Users.Player;

public abstract class Card extends Event {
    protected Player offender;

    public Card(Player offender, int minute) {
        super(minute);
        this.offender = offender;
    }

    public Player getOffender() {
        return offender;
    }

    @Override
    public String toString() {
        return "offender=" + offender.getAssetName() +" "+super.toString();
    }
}
