package GUI;

import Service.MainController;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Route(value = "ModifyUsers", layout = FootballMain.class)
@PageTitle("Modify Users")
public class ModifyUsers extends FlexLayout {
    public static final String VIEW_NAME = "Modify Users";


    public ModifyUsers() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        buildModifier();
    }

    private void buildModifier() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setJustifyContentMode(JustifyContentMode.START);
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.setSizeFull();

        H1 modifyHeader = new H1("Modify User settings");
        modifyHeader.getStyle().set("marginLeft", "22%");
        modifyHeader.setWidth("50%");
        verticalLayout.add(modifyHeader);



        ComboBox<String> chooseUser = new ComboBox<>();
        chooseUser.setPlaceholder("Select a user");
        chooseUser.setLabel("Username to modify:");
        chooseUser.setItems(MainController.getAllUsersByUsername());
        chooseUser.setClearButtonVisible(true);
        verticalLayout.add(chooseUser);

        ComboBox<String> chooseRoleToAdd = new ComboBox<>();
        chooseRoleToAdd.setItems(MainController.getRoleTypes());
        chooseRoleToAdd.setLabel("Role to add for the user:");
        chooseRoleToAdd.setPlaceholder("Select a Role to add");
        verticalLayout.add(chooseRoleToAdd);
        chooseRoleToAdd.setVisible(false);

        DatePicker datePicker = new DatePicker();
        datePicker.setVisible(false);
        datePicker.setLabel("Enter Player Birth Date:");
        verticalLayout.add(datePicker);

        TextField refTraining = new TextField();
        refTraining.setPlaceholder("Enter Referee Training");
        refTraining.setVisible(false);
        refTraining.setLabel("Enter Relevant Training:");
        verticalLayout.add(refTraining);

        ComboBox<String> allRolesOfUser = new ComboBox<>();
        allRolesOfUser.setVisible(false);
        verticalLayout.add(allRolesOfUser);

        Button saveChanges = new Button("Save Changes");

        saveChanges.addClickListener(e -> {
            List<String> valuesToSave = new ArrayList<>();
            valuesToSave.add(chooseUser.getValue());
            if(chooseRoleToAdd.getValue().toLowerCase().equals("player"))
            {
                valuesToSave.add(datePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
            else if(chooseRoleToAdd.getValue().toLowerCase().equals("referee"))
            {
                valuesToSave.add(refTraining.getValue());
            }
            String[] values = new String[valuesToSave.size()];

            for (int i = 0; i < valuesToSave.size(); i++) {
                values[i] = valuesToSave.get(i);
            }

            MainController.addRoleToUser(chooseRoleToAdd.getValue(),values);
            getUI().get().navigate("");
            FootballMain.showNotification("Role added Successfully");
            FootballMain.showNotification("If you can't see the changes, please refresh the page");
        });
        verticalLayout.add(saveChanges);


        chooseUser.addValueChangeListener(e -> {
            if(e.getHasValue().isEmpty())
            {
                chooseRoleToAdd.setVisible(false);
                allRolesOfUser.setVisible(false);
            }
            else
            {
                chooseRoleToAdd.setVisible(true);
                allRolesOfUser.setVisible(true);
                allRolesOfUser.setItems(MainController.getUserRoles(e.getValue()));
            }
        });

        chooseRoleToAdd.addValueChangeListener(e -> {
           if(e.getValue().toLowerCase().equals("player"))
           {
               datePicker.setVisible(true);
               refTraining.setVisible(false);
           }
           else if(e.getValue().toLowerCase().equals("referee"))
           {
               datePicker.setVisible(false);
               refTraining.setVisible(true);
           }
           else{
               datePicker.setVisible(false);
               refTraining.setVisible(false);
           }
        });

        ComboBox<String> allTeams = new ComboBox<>("Teams selection");
        allTeams.setItems(MainController.getAllTeamsByName());
        allTeams.setClearButtonVisible(true);
        verticalLayout.add(allTeams);

        add(verticalLayout);

    }

    private Component buildModifyInfo() {
        VerticalLayout modifyInfo = new VerticalLayout();

        H1 loginInfoHeader = new H1("");
        loginInfoHeader.setWidth("100%");
        Span loginInfoText = new Span(
                "Log in as \"admin\" to have full access. Log in with any " +
                        "other username to have read-only access. For all " +
                        "users, the password is same as the username.");
        loginInfoText.setWidth("100%");
        modifyInfo.add(loginInfoHeader);
        modifyInfo.add(loginInfoText);

        return modifyInfo;
    }

}
