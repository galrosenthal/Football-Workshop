package GUI.RoleRelatedViews.Referee;

import GUI.FootballMain;
import Service.MainController;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

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
        createReportDownload();
    }

    private void createReportDownload() {
        String buttonTaskName = "Create report";
        Button downloadReport = new Button(buttonTaskName);

        downloadReport.addClickListener(e-> {
            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.produceReport(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();
        });

        add(downloadReport);
    }

    private InputStream createResource() {

        String svg = "<?xml version='1.0' encoding='UTF-8' standalone='no'?>"
                + "<svg  xmlns='http://www.w3.org/2000/svg' "
                + "xmlns:xlink='http://www.w3.org/1999/xlink'>"
                + "<rect x='10' y='10' height='100' width='100' "
                + "style=' fill: #90C3D4'/><text x='30' y='30' fill='red'>"
                + "</text>" + "</svg>";
        return new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8));
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
