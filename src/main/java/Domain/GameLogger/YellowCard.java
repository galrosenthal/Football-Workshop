package Domain.GameLogger;

import Domain.Users.Player;

public class YellowCard extends Card {

    public YellowCard(Player offender, int minute) {
        super(offender, minute);
    }

    @Override
    public String toString() {
        return super.toString()+" Yellow Card.";
    }
}
