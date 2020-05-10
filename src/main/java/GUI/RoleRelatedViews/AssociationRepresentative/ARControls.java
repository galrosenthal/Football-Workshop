package GUI.RoleRelatedViews.AssociationRepresentative;

import GUI.FootballMain;
import Service.MainController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

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
            MainController.registerNewTeam((String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("username"));
        });

        add(regNewTeam);

    }
}
