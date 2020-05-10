package GUI;

import GUI.Login.LoginScreen;
import GUI.Registration.RegistrationView;
import Service.MainController;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;

public class GuiInit implements VaadinServiceInitListener {



    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                if(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("username") == null
                && (!pageAllowedForGuest(enterEvent.getNavigationTarget())))
                {
                    FootballMain.showNotification("Wrong Page, You dont have sufficient permissions");
                    enterEvent.rerouteTo("");
                }
                else if(enterEvent.getNavigationTarget().equals(ModifyUsers.class) &&
                        !MainController.getUserRoles((String)VaadinService.getCurrentRequest().
                                getWrappedSession().getAttribute("username")).contains("SYSTEM_ADMIN"))
                {
                    FootballMain.showNotification("Wrong Page, You dont have sufficient permissions");
                    enterEvent.rerouteTo("");
                }
            });
        });
    }


    /**
     * If the navigationTarget is a View that a guest should be able to visit
     * this function will return true
     * <p></p>
     * <b>Each allowed View should be listed below!</b>.
     * @param navigationTarget the target View class
     * @return true if the View is allowed for this user
     */
    private boolean pageAllowedForGuest(Class<?> navigationTarget) {
        return navigationTarget.equals(Welcome.class) ||
                navigationTarget.equals(LoginScreen.class) ||
                navigationTarget.equals(RegistrationView.class);
    }


}
