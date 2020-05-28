package Domain.GameLogger;

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
        return super.toString()+" Injury injuredPlayer=" + injuredPlayer.getAssetName()+".";
    }
}
