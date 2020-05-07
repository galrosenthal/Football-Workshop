package GUI;

import Domain.EntityManager;
import GUI.Login.LoginScreen;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

public class GuiInit implements VaadinServiceInitListener {

    private boolean firstTime = false;

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                if(!firstTime)
                {
                    enterEvent.rerouteTo("");
                    firstTime = true;
                }
            });
        });
    }
}
