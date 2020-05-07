package GUI;

import Domain.EntityManager;
import GUI.Login.LoginScreen;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

public class GuiInit implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                if(!EntityManager.getInstance().isLoggedIn() && !LoginScreen.class.equals(enterEvent.getNavigationTarget()))
                {
                    enterEvent.rerouteTo("Login");
                }
            });
        });
    }
}
