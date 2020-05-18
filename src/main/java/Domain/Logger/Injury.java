package Domain.Logger;

import Domain.Users.Player;

public class Injury extends Event {
    private Player injuredPlayer;

    public Injury(Player injuredPlayer, int minute) {
        super(minute);
        this.injuredPlayer = injuredPlayer;
    }

    public Player getInjuredPlayer() {
        return injuredPlayer;
    }

    @Override
    public String toString() {
        return "Injury injuredPlayer=" + injuredPlayer.getAssetName() +" "+super.toString();
    }
}
