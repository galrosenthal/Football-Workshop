package Domain.Users;

public class CoachPersonalPage extends PersonalPage {

    private Coach coachPage;


    public CoachPersonalPage(Coach coachPage) {
        this.coachPage = coachPage;
        this.pageName = coachPage.getSystemUser().getName();
    }

}
