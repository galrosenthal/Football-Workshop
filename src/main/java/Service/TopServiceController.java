package Service;

public class TopServiceController {

    private static volatile TopServiceController mInstance;
    UIController uiController;
    private TopServiceController(UIController uc) {
        uiController = uc;
    }

    public static TopServiceController getInstance() {
        if (mInstance == null) {
            synchronized (TopServiceController.class) {
                if (mInstance == null) {
                    mInstance = new TopServiceController(new UIController());
                }
            }
        }
        return mInstance;
    }

    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    public UIController getUiController() {
        return uiController;
    }
}
