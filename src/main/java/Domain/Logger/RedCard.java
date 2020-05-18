package Domain.Logger;

import Domain.Users.Player;

public class RedCard extends Card {


    public RedCard(Player offender, int minute) {
        super(offender, minute);
    }

    @Override
    public String toString() {
        return "Red Card " + super.toString() ;
    }
}
