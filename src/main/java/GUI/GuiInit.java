package GUI;

import GUI.Login.LoginScreen;
import GUI.Registration.RegistrationView;
import GUI.RoleRelatedViews.AssociationRepresentative.ARControls;
import GUI.RoleRelatedViews.TeamOwner.TOControls;
import Service.MainController;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;

public class GuiInit implements VaadinServiceInitListener {



    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {

            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {

                if(!MainController.isSystemBooted())
                {
                    if(!enterEvent.getNavigationTarget().equals(BootSystem.class))
                    {
                        enterEvent.rerouteTo(BootSystem.class);
                        FootballMain.showNotification("Please Boot the System");
                    }
                }
                else if(VaadinSession.getCurrent().getAttribute("username") == null
                        && (!pageAllowedForGuest(enterEvent.getNavigationTarget())))
                {
                    FootballMain.showNotification("Wrong Page, You dont have sufficient permissions");
                    enterEvent.rerouteTo("");
                }
                else if(enterEvent.getNavigationTarget().equals(ModifyUsers.class) &&
                        !MainController.getUserRoles((String)VaadinSession.getCurrent().getAttribute("username")).contains("SYSTEM_ADMIN"))
                {
                    FootballMain.showNotification("Wrong Page, You dont have sufficient permissions");
                    enterEvent.rerouteTo("");
                }
                else if(enterEvent.getNavigationTarget().equals(ARControls.class) &&
                        !MainController.getUserRoles((String)VaadinSession.getCurrent().getAttribute("username")).contains("ASSOCIATION_REPRESENTATIVE"))
                {
                    FootballMain.showNotification("Wrong Page, You dont have sufficient permissions");
                    enterEvent.rerouteTo("");
                }
                else if(enterEvent.getNavigationTarget().equals(TOControls.class) &&
                        !MainController.getUserRoles((String)VaadinSession.getCurrent().getAttribute("username")).contains("TEAM_OWNER"))
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
