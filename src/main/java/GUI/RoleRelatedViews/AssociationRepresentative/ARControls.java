package GUI.RoleRelatedViews.AssociationRepresentative;

import GUI.FootballMain;
import Service.MainController;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "AR", layout = FootballMain.class)
@PageTitle("Association Representative Controls")
public class ARControls extends VerticalLayout {
    public static final String VIEW_NAME = "AR Controls";
    public ARControls() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        buildUI();
    }

    private void buildUI() {

        Button regNewTeam = new Button("Create a new Team");
        regNewTeam.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            Component view = this;
            String username = (String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("username");
            Thread t = new Thread(() -> {
               UI.setCurrent(lastUI);
               VaadinSession.setCurrent(se);
               MainController.registerNewTeam(username, view);
            });
            t.setName("CREATE NEW TEAM");
            t.start();
        });

        add(regNewTeam);

    }
}
