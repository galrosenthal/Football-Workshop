package Domain.Users;

public class PlayerPersonalPage extends PersonalPage {

    private Player pagePlayer;


    public PlayerPersonalPage(Player pagePlayer) {
        this.pagePlayer = pagePlayer;
        this.pageName = pagePlayer.getSystemUser().getName();
    }




}
