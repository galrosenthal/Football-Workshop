package GUI.RoleRelatedViews.TeamOwner;

import GUI.FootballMain;
import Service.MainController;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "TO", layout = FootballMain.class)
@PageTitle("Team Owner Controls")
public class TOControls extends VerticalLayout {
    public static final String VIEW_NAME = "Team Owner Controls";

    public TOControls() {
        buildControls();
    }

    private void buildControls() {
        createCloseTeamButton();
        createReopenTeamButton();
        createAddAnotherTeamOwnerButton();
        createAddTeamAssetButton();
        createModifyAssetButton();
    }

    private void createModifyAssetButton() {
        String buttonTaskName = "Modify Team Asset";
        Button modifyAssetButton = new Button(buttonTaskName);
        modifyAssetButton.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = (String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("username");
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.modifyTeamAsset(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(modifyAssetButton);
    }

    private void createAddTeamAssetButton() {
        String buttonTaskName = "Add Team Asset";
        Button addTeamAssetButton = new Button(buttonTaskName);
        addTeamAssetButton.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = (String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("username");
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.addTeamAsset(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(addTeamAssetButton);
    }

    private void createAddAnotherTeamOwnerButton() {
        String buttonTaskName = "Add Another Team Owner";
        Button anotherTeamOwnerButton = new Button(buttonTaskName);
        anotherTeamOwnerButton.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = (String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("username");
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.addAnotherTeamOwner(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(anotherTeamOwnerButton);
    }

    private void createReopenTeamButton() {
        String buttonTaskName = "ReOpen Team";
        Button reopenTeamButton = new Button(buttonTaskName);
        reopenTeamButton.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = (String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("username");
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.reopenTeam(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(reopenTeamButton);
    }

    private void createCloseTeamButton() {
        String buttonTaskName = "Close Team";
        Button closeTeamButton = new Button(buttonTaskName);
        closeTeamButton.addClickListener(e -> {

            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            String username = (String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("username");
            Thread t = new Thread(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                MainController.closeTeam(username);
            });
            t.setName(buttonTaskName.toUpperCase());
            t.start();

        });

        add(closeTeamButton);
    }
}
