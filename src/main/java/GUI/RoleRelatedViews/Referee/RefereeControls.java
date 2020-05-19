package GUI.RoleRelatedViews.Referee;

import GUI.FootballMain;
import Service.MainController;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

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
        createDisplayScheduleGame();
    }

    private void createDisplayScheduleGame() {
        String buttonTaskName = "View Game Schedule";
        Button watchGamesBtn = new Button(buttonTaskName);
        watchGamesBtn.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();

                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.DisplayScheduledGame(username);

        });

        add(watchGamesBtn);
    }

    private void createShowActiveGameButton() {
        String buttonTaskName = "Update Game Events";
        Button activeGamesEventsBtn = new Button(buttonTaskName);

        activeGamesEventsBtn.addClickListener(e-> {
            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.updateGameEvents(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();
        });

        add(activeGamesEventsBtn);
    }

    private String getUsernameFromSession() {
        VaadinSession se = VaadinSession.getCurrent();
        return (String)se.getAttribute("username");

    }
}
