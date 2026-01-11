package controller;

import view.Alert;
import view.LoginPanel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


// PRIMERO HACER REGISTER LUEGO LOGIN
public class LoginController {

    private final MainController main;
    private final LoginPanel loginPanel;

    public LoginController(MainController main, LoginPanel loginPanel) {
        this.main = main;
        this.loginPanel = loginPanel;
        init();
    }

    private void init() {
        loginPanel.getBackBtn().addActionListener((e) -> {
            main.showMenu();
        });
        loginPanel.getBtnLogin().addActionListener((e) -> {
            doLogin();
        });
    }

    private void doLogin() {
        String email = loginPanel.getTxtEmail().getText();
        String password = loginPanel.getTxtPassword().getText();

        loginFieldValidator(email, password);

        boolean valid = true; // validate with the LoginService

        if (valid) {
            // si el logeo es valido se procede a mostrar el menu correspondiente (dependiendo del rol del logeado)
        } else {
            // si el logeo no es valido entonces lanza un warning de credenciales invalidas
        }


    }

    private boolean emailValidator(String email) {
        final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        final Pattern pattern = Pattern.compile(EMAIL_REGEX);

        if (email.isBlank()) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void loginFieldValidator(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Alert.warning("WARNING", "Email or password missing");
            return;
        }

        boolean okEmail = emailValidator(email);
        if (!okEmail) {
            Alert.warning("WARNING", "The email format is invalid");
            return;
        }

        if (password.length() < 6) {
            Alert.warning("WARNING", "The password is very short");
        }
    }

}
