package GUI;

import Domain.Users.Unregistered;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.Route;


@Route(value = "Registration", layout = FootballMain.class)
public class RegistrationView extends VerticalLayout {
    public static final String VIEW_NAME = "Register";
    public RegistrationView() {
        buildRegForm();
    }

    private void buildRegForm() {
        FormLayout layoutWithBinder = new FormLayout();
        add(layoutWithBinder);
        Binder<Unregistered> binder = new Binder<>();

// The object that will be edited
        Unregistered contactBeingEdited = new Unregistered();

// Create the fields
        TextField firstName = new TextField();
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        TextField lastName = new TextField();
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        TextField phone = new TextField();
        phone.setValueChangeMode(ValueChangeMode.EAGER);
        TextField email = new TextField();
        email.setValueChangeMode(ValueChangeMode.EAGER);
        DatePicker birthDate = new DatePicker();
        Checkbox doNotCall = new Checkbox("Do not call");
        Label infoLabel = new Label();
        Button save = new Button("Save");
        Button reset = new Button("Reset");

        layoutWithBinder.addFormItem(firstName, "First name");
        layoutWithBinder.addFormItem(lastName, "Last name");
        layoutWithBinder.addFormItem(birthDate, "Birthdate");
        layoutWithBinder.addFormItem(email, "E-mail");
        FormLayout.FormItem phoneItem = layoutWithBinder.addFormItem(phone, "Phone");
        phoneItem.add(doNotCall);

// Button bar
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset);
        add(actions);
        save.getStyle().set("marginRight", "10px");

        SerializablePredicate<String> phoneOrEmailPredicate = value -> !phone
                .getValue().trim().isEmpty()
                || !email.getValue().trim().isEmpty();

// E-mail and phone have specific validators
//        Binder.Binding<Unregistered, String> emailBinding = binder.forField(email)
//                .withValidator(phoneOrEmailPredicate,
//                        "Both phone and email cannot be empty")
//                .withValidator(new EmailValidator("Incorrect email address"))
//                .bind(Unregistered::getEmail, Unregistered::setEmail);

//        Binding<Contact, String> phoneBinding = binder.forField(phone)
//                .withValidator(phoneOrEmailPredicate,
//                        "Both phone and email cannot be empty")
//                .bind(Contact::getPhone, Contact::setPhone);

// Trigger cross-field validation when the other field is changed
//        email.addValueChangeListener(event -> phoneBinding.validate());
//        phone.addValueChangeListener(event -> emailBinding.validate());

// First name and last name are required fields
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);

//        binder.forField(firstName)
//                .withValidator(new StringLengthValidator(
//                        "Please add the first name", 1, null))
//                .bind(Contact::getFirstName, Contact::setFirstName);
//        binder.forField(lastName)
//                .withValidator(new StringLengthValidator(
//                        "Please add the last name", 1, null))
//                .bind(Contact::getLastName, Contact::setLastName);

// Birthdate and doNotCall don't need any special validators
//        binder.bind(doNotCall, Contact::isDoNotCall, Contact::setDoNotCall);
//        binder.bind(birthDate, Contact::getBirthDate, Contact::setBirthDate);

// Click listeners for the buttons
        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(contactBeingEdited)) {
                infoLabel.setText("Saved bean values: " + contactBeingEdited);
            } else {
                BinderValidationStatus<Unregistered> validate = binder.validate();
//                String errorText = validate.getFieldValidationStatuses()
//                        .stream().filter(BindingValidationStatus::isError)
//                        .map(BindingValidationStatus::getMessage)
//                        .map(Optional::get).distinct()
//                        .collect(Collectors.joining(", "));
                infoLabel.setText("There are errors: ");
            }
        });
        reset.addClickListener(event -> {
            // clear fields by setting null
            binder.readBean(null);
            infoLabel.setText("");
            doNotCall.setValue(false);
        });
    }
}
