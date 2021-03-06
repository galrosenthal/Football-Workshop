package GUI;

import Service.MainController;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
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
        H2 h2 = new H2("The System is not booted yet.");
        h2.setId("bootH2");
        add(h2);
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
