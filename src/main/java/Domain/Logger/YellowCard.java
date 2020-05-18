package Domain.Logger;

import Domain.Users.Player;

public class YellowCard extends Card {

    public YellowCard(Player offender, int minute) {
        super(offender, minute);
    }

    @Override
    public String toString() {
        return "Yellow Card " + super.toString() +" " ;
    }
}
