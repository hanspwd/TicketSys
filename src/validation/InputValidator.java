package validation;

import view.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    public static boolean emailValidator(String email) {

        final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        final Pattern pattern = Pattern.compile(EMAIL_REGEX);

        if(email.isBlank()) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return  matcher.matches();
    }

    public static void registerFieldValidator(String name, String email, String password, String confirmPassword) {
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Alert.warning("WARNING", "All fields are required");
            return;
        }

        if(name.length() < 3) {
            Alert.warning("WARNING", "Name should be at least 3 characters");
            return;
        }

        boolean okEmailFormat = emailValidator(email);

        if(!okEmailFormat) {
            Alert.warning("WARNING", "The email format is invalid");
            return;
        }

        if(password.length() < 6 ||  confirmPassword.length() < 6) {
            Alert.warning("WARNING", "Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            Alert.warning("WARNING", "Passwords do not match");
        }

    }

}
