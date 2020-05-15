package Domain.Logger;

import Domain.Users.Player;

public class Injury extends Event {
    private Player injuredPlayer;
    private int minute;

    public Injury(Player injuredPlayer, int minute) {
        this.injuredPlayer = injuredPlayer;
        this.minute = minute;
    }
}
