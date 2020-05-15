package GUI.Registration;

import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class ConfirmPassValidator extends AbstractValidator<String> {

    private PasswordField passEntered;
    private String errorMessage;


    public ConfirmPassValidator(String errorMessage,PasswordField passToConfirm) {
        super(errorMessage);
        passEntered = passToConfirm;
        this.errorMessage = errorMessage;
    }

    public ValidationResult apply(String value, ValueContext context) {
        if(!isSamePassword(value))
        {
            return ValidationResult.error(errorMessage);
        }
        return ValidationResult.ok();
    }

    private boolean isSamePassword(String passwordToConfirm) {
        return passwordToConfirm.equals(passEntered.getValue());
    }


}
