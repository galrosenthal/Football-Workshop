package GUI;

import Service.AllSubscribers;
import Service.MainController;
import Service.UIController;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "Start-System")
@PageTitle("System Start")
@Push
public class BootSystem extends VerticalLayout {

    public BootSystem() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        buildTheBootView();
    }

    private void buildTheBootView() {
        add(new H2("The System is not booted yet."));
        add(new H3("if you wish to boot the system, please use a System Admin user."));

        Button bootTheSystem = new Button("Boot the System");
        UI currentUI = UI.getCurrent();
        VaadinSession currentSession = VaadinSession.getCurrent();
        bootTheSystem.addClickListener(e -> {
           Thread t = new Thread(() -> {
               UI.setCurrent(currentUI);
               VaadinSession.setCurrent(currentSession);
               if(MainController.systemBoot())
               {
                   currentSession.access(() -> {
                       currentUI.navigate(Welcome.class);
                   });
               }
           });
           t.setName("Booting the System");
           t.start();
        });
        add(bootTheSystem);


    }
}
