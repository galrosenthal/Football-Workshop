package Domain.GameLogger;

import Domain.Users.Player;

public class RedCard extends Card {


    public RedCard(Player offender, int minute) {
        super(offender, minute);
    }

    @Override
    public String toString() {
        return super.toString()+" Red Card.";
    }
}
