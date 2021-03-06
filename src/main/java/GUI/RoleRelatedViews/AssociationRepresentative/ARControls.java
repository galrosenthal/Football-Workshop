package GUI.RoleRelatedViews.AssociationRepresentative;

import GUI.FootballMain;
import Service.MainController;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "AR", layout = FootballMain.class)
@PageTitle("Association Representative Controls")
public class ARControls extends VerticalLayout {
    public static final String VIEW_NAME = "AR Controls";


    public ARControls() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.START);
        buildUI();
        this.setId("ARControls");
    }

    private void buildUI() {

        createRegNewTeamButton();
        createDefinePointsPolicyButton();
        createChangePointsPolicyButton();
        createDefineGamesPolicyButton();
        createActivateSchedulingPolicyButton();
        createAddLeaugeButton();
        createAddSeasonToLeagueButton();
        createAddRefereeButton();
        createRemoveRefereeButton();
        createAssignRefereeButton();
        createAddTeamsToSeasonButton();
        createRemoveTeamsFromSeasonButton();
    }

    private void createActivateSchedulingPolicyButton() {
        String buttonTaskName = "Activate Scheduling Policy";
        Button removeTeamsFromSeason = new Button(buttonTaskName);
        removeTeamsFromSeason.setId("activeSchedulingPolicy");
        removeTeamsFromSeason.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.activateSchedulingPolicy(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(removeTeamsFromSeason);
    }

    private void createChangePointsPolicyButton() {
        String buttonTaskName = "Set Points Policy";
        Button removeTeamsFromSeason = new Button(buttonTaskName);
        removeTeamsFromSeason.setId("setPointsPolicy");
        removeTeamsFromSeason.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.changePointsPolicy(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(removeTeamsFromSeason);
    }

    private void createRemoveTeamsFromSeasonButton() {
        String buttonTaskName = "Remove Teams To Season";
        Button removeTeamsFromSeason = new Button(buttonTaskName);
        removeTeamsFromSeason.setId("removeTeamsFromSeason");
        removeTeamsFromSeason.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.removeTeamsFromSeason(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(removeTeamsFromSeason);
    }

    private void createAddTeamsToSeasonButton() {
        String buttonTaskName = "Add Teams To Season";
        Button addTeamsToSeason = new Button(buttonTaskName);
        addTeamsToSeason.setId("addTeamsToSeason");
        addTeamsToSeason.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.addTeamsToSeason(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(addTeamsToSeason);
    }

    private void createAssignRefereeButton() {
        String buttonTaskName = "Assign a Referee";
        Button assignReferee = new Button(buttonTaskName);
        assignReferee.setId("assignReferee");
        assignReferee.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.assignReferee(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(assignReferee);
    }

    private void createRemoveRefereeButton() {
        String buttonTaskName = "Remove a Referee";
        Button removeReferee = new Button(buttonTaskName);
        removeReferee.setId("removeReferee");
        removeReferee.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.removeReferee(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(removeReferee);
    }

    private void createAddRefereeButton() {
        String buttonTaskName = "Add New Referee";
        Button addReferee = new Button(buttonTaskName);
        addReferee.setId("addReferee");
        addReferee.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.addReferee(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(addReferee);
    }

    private void createAddSeasonToLeagueButton() {
        String buttonTaskName = "Add Season to League";
        Button addSeasonToLeague = new Button(buttonTaskName);
        addSeasonToLeague.setId("addSeasonToLeague");
        addSeasonToLeague.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.addSeasonToLeague(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(addSeasonToLeague);
    }

    private void createAddLeaugeButton() {
        String buttonTaskName = "Add New Leauge";
        Button addNewLeague = new Button(buttonTaskName);
        addNewLeague.setId("addNewLeague");
        addNewLeague.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.addNewLeague(username);
                System.out.println("Leauge added successfully");
                lastUI.access(() -> lastUI.getPage().reload());
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(addNewLeague);
    }


    private void createDefineGamesPolicyButton() {
        Button defineGamePolicy = new Button("Create New Games Policy");
        defineGamePolicy.setId("defineGamePolicy");
        defineGamePolicy.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.createGamePolicy(username);
            });
            t.setName("DEFINE GAME POLICY");
            t.start();

        });

        add(defineGamePolicy);
    }

    private void createDefinePointsPolicyButton() {
        Button definePointPolicy = new Button("Create New Points Policy");
        definePointPolicy.setId("definePointPolicy");
        definePointPolicy.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.addPointPolicy(username);
            });
            t.setName("DEFINE POINTS POLICY");
            t.start();

        });

        add(definePointPolicy);
    }

    private void createRegNewTeamButton() {
        Button regNewTeam = new Button("Create a new Team");
        regNewTeam.setId("regNewTeam");
        regNewTeam.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = getUsernameFromSession();
            Thread t = new Thread(() -> {
               UI.setCurrent(lastUI);
               VaadinSession.setCurrent(se);
               MainController.registerNewTeam(username);
            });
            t.setName("CREATE NEW TEAM");
            t.start();

        });

        add(regNewTeam);
    }

    private String getUsernameFromSession() {
        VaadinSession se = VaadinSession.getCurrent();
        return (String)se.getAttribute("username");

    }
}
