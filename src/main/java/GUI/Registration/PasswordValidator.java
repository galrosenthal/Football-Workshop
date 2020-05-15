package GUI.Registration;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.*;


public class PasswordValidator extends StringLengthValidator {

    public PasswordValidator() {
        super("", 8, Integer.MAX_VALUE);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        ValidationResult result = super.apply(value, context);
        if (result.isError()) {
            return ValidationResult
                    .error("Password should contain at least 8 characters");
        } else if (!isValidPassword(value)) {
            return ValidationResult
                    .error("Password must contain at least Uppercase letter, Lowercase letter and a number");
        }
        return result;
    }


    //Checking if the password meets the security requirements
    // at least 8 characters
    // at least 1 number
    // at least 1 upper case letter
    // at least 1 lower case letter
    // must not contain any spaces
    private boolean isValidPassword(String pass)
    {
        String pswrdRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        return pass.matches(pswrdRegEx);
    }
}