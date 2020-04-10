package Domain.Controllers;

public class TopDomainController {

    private static volatile TopDomainController mInstance;
    TeamController teamController;


    private TopDomainController(TeamController tc) {
        teamController = tc;
    }

    public static TopDomainController getInstance() {
        if (mInstance == null) {
            synchronized (TopDomainController.class) {
                if (mInstance == null) {
                    mInstance = new TopDomainController(new TeamController());
                }
            }
        }
        return mInstance;
    }

    public void setTeamController(TeamController teamController) {
        this.teamController = teamController;
    }

    public TeamController getTeamController() {
        return teamController;
    }
}
