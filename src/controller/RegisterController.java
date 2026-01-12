package controller;

import service.AuthService;
import view.Alert;
import view.RegisterPanel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    private final MainController main;
    private final RegisterPanel registerPanel;

    public RegisterController(MainController main, RegisterPanel registerPanel) {
        this.main = main;
        this.registerPanel = registerPanel;
        init();
    }

    private void init() {
        registerPanel.getBackBtn().addActionListener(e -> {
            main.showMenu();
        });
        registerPanel.getBtnRegister().addActionListener(e -> {
            try {
                doRegister();
            } catch (Exception ex) {
                Alert.error("ERROR", ex.getMessage());
            }
        });

    }

    private void doRegister() throws Exception {
        String name = registerPanel.getTxtName().getText();
        String email = registerPanel.getTxtEmail().getText();
        String password = registerPanel.getTxtPassword().getText();
        String confirmPassword = registerPanel.getTxtConfirmPassword().getText();

        registerFieldValidator(name, email, password, confirmPassword);

        boolean ok = AuthService.register(name, email, confirmPassword);

        if (ok) {
            Alert.info("Successful operation", "Register successful");
        } else  {
            Alert.info("Failed operation", "Register failed, try again");
        }

    }

    private boolean emailValidator(String email) {

        final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        final Pattern pattern = Pattern.compile(EMAIL_REGEX);

        if(email.isBlank()) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return  matcher.matches();
    }

    private void registerFieldValidator(String name, String email, String password, String confirmPassword) {
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Alert.warning("WARNING", "All fields are required");
            return;
        }

        if(name.length() < 3) {
            Alert.warning("WARNING", "Name should be at least 3 characters");
            return;
        }

        boolean okEmail = emailValidator(email);
        if(!okEmail) {
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
