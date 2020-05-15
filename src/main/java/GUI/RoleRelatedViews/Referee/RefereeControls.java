package GUI.RoleRelatedViews.Referee;

import GUI.FootballMain;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "REF", layout = FootballMain.class)
@PageTitle("Referee Controls")
public class RefereeControls extends VerticalLayout {
    public static final String VIEW_NAME = "Referee Controls";

    public RefereeControls() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.START);
        buildUI();
    }

    private void buildUI() {
        createShowActiveGameButton();
    }

    private void createShowActiveGameButton() {
    }
}
