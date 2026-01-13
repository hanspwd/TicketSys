package validation;

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

    public static void registerFieldValidator(String name, String email, String password, String confirmPassword) throws Exception {
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            throw new Exception("All fields are required");
        }

        if(name.length() < 3) {
            throw new Exception("Name should be at least 3 characters");
        }

        boolean okEmailFormat = emailValidator(email);

        if(!okEmailFormat) {
            throw new Exception("The email format is invalid");
        }

        if(password.length() < 6 ||  confirmPassword.length() < 6) {
            throw new Exception("Password must be at least 6 characters");
        }

        if (!password.equals(confirmPassword)) {
            throw new Exception("Passwords do not match");
        }

    }

    public static void loginFieldValidator(String email, String password) throws Exception {
        if (email.isEmpty() || password.isEmpty()) {
            throw new Exception("Email or password missing");
        }

        boolean okEmailFormat = emailValidator(email);

        if (!okEmailFormat) {
            throw new Exception("The  email format is invalid");
        }

        if (password.length() < 6) {
            throw new Exception("Password must be at least 6 characters");
        }
    }
}
