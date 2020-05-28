package Domain.GameLogger;

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
        return super.toString()+" offender=" + offender.getAssetName()+".";
    }
}
