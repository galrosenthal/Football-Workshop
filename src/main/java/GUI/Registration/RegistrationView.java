package GUI.Registration;

import GUI.FootballMain;
import GUI.NotRegiseredView;
import Service.MainController;
import Service.UIController;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vaadin.flow.component.UI.getCurrent;


@Route(value = "Registration", layout = NotRegiseredView.class)
public class RegistrationView extends VerticalLayout {
    public static final String VIEW_NAME = "Register";
    public RegistrationView() {
        buildRegForm();

    }

    private void buildRegForm() {
        FormLayout layoutWithBinder = new FormLayout();
        add(layoutWithBinder);
        Binder<ValidatorUser> binder = new Binder<>();

// The object that will be edited
//        Unregistered contactBeingEdited = new Unregistered();

// Create the fields
        TextField username = new TextField();
        username.setValueChangeMode(ValueChangeMode.EAGER);
        username.setPlaceholder("Enter Username");
        TextField firstName = new TextField();
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        firstName.setPlaceholder("Enter Your First Name");
        TextField lastName = new TextField();
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        lastName.setPlaceholder("Enter Your Last Name");
        PasswordField pass = new PasswordField();
        pass.setValueChangeMode(ValueChangeMode.EAGER);
        pass.setPlaceholder("Enter Password");
        PasswordField confirmPass = new PasswordField();
        confirmPass.setValueChangeMode(ValueChangeMode.EAGER);
        confirmPass.setPlaceholder("Confirm Password");


        Button save = new Button("Save");
        Button reset = new Button("Reset");
        Button back = new Button("Back");

        layoutWithBinder.addFormItem(username, "Username");
        layoutWithBinder.addFormItem(firstName, "First name");
        layoutWithBinder.addFormItem(lastName, "Last name");
        layoutWithBinder.addFormItem(pass, "Password");
        layoutWithBinder.addFormItem(confirmPass, "Confirm Password");

// Button bar
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset, back);
        add(actions);
        save.getStyle().set("marginRight", "10px");


        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);

        binder.forField(firstName)
                .withValidator(new StringLengthValidator(
                        "Please add the first name", 1, null))
                .bind(ValidatorUser::getFirstName, ValidatorUser::setFirstName);
        binder.forField(lastName)
                .withValidator(new StringLengthValidator(
                        "Please add the last name", 1, null))
                .bind(ValidatorUser::getLastName, ValidatorUser::setLastName);

        binder.forField(pass)
                .withValidator(new PasswordValidator())
                .bind(ValidatorUser::getPass,ValidatorUser::setPass);

        binder.forField(confirmPass)
                .withValidator(new ConfirmPassValidator("Passwords do not match",pass))
                .bind(ValidatorUser::getPass, ValidatorUser::setPass);

        binder.forField(username)
                .withValidator(new UsernameValidator())
                .bind(ValidatorUser::getUsername, ValidatorUser::setUsername);

        ValidatorUser userToRegister = new ValidatorUser();
// Click listeners for the buttons
        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(userToRegister)) {
                List<String> userDetails = new ArrayList<>();
                userDetails.add(userToRegister.getUsername());
                userDetails.add(userToRegister.getFirstName());
                userDetails.add(userToRegister.getLastName());
                userDetails.add(userToRegister.getPass());
                if(!MainController.signup(userDetails))
                {
                    FootballMain.showNotification("Something Went Wrong please try again!");
                }
                else {
                    getUI().get().navigate("");
                    FootballMain.showNotification("Successful sign up. Welcome, " + userToRegister.getUsername());

                }
            } else {
                BinderValidationStatus<ValidatorUser> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                FootballMain.showNotification("Error: " + errorText);
            }
        });
        reset.addClickListener(event -> {
            // clear fields by setting null
            binder.readBean(null);
        });

        back.addClickListener(e -> {
            getElement().executeJs("window.history.back()");
        });
    }
}