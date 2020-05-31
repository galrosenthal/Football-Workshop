package GUI.Registration;

import Service.MainController;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.StringLengthValidator;

public class UsernameValidator extends StringLengthValidator {

    public UsernameValidator() {
        super("", 3, Integer.MAX_VALUE);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        ValidationResult result = super.apply(value, context);
        if(result.isError())
        {
            return ValidationResult.error("Username must be longer than 2 character");
        }
        else if(isUserExists(value))
        {
            return ValidationResult.error("User already exists");
        }
        return ValidationResult.ok();
    }

    private boolean isUserExists(String username) {
        return MainController.isUserExists(username);
    }
}
