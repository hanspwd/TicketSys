package controller.auth;

import controller.MainController;
import model.enums.Role;
import service.auth.LoginResult;
import service.users.TechnicalService;
import service.auth.LoginService;
import view.common.Alert;
import view.auth.LoginPanel;

import java.sql.SQLException;

import static service.auth.AuthStatus.*;
import static util.FormUtils.clearLoginInputs;
import static validation.InputValidator.loginFieldValidator;

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

        loginPanel.getBtnClear().addActionListener((e) -> {
            clearLoginInputs(loginPanel);
        });

        loginPanel.getBtnLogin().addActionListener((e) -> {
            try {
                doLogin();
                clearLoginInputs(loginPanel);
            } catch (SQLException ex) {
                Alert.error("ERROR", ex.getMessage());
            } catch (Exception ex) {
                Alert.error("Failed operation", ex.getMessage());
            }

        });
    }

    private void doLogin() throws Exception {
        String email = loginPanel.getTxtEmail().getText();
        String password = loginPanel.getTxtPassword().getText();

        // Validación mejorada para campos vacíos, nulos o con espacios
        if (email == null || email.trim().isEmpty()) {
            throw new Exception("Email is required");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new Exception("Password is required");
        }

        // Validar formato de email y longitud de contraseña
        loginFieldValidator(email.trim(), password.trim());

        LoginResult result = LoginService.login(email.trim(), password.trim());

        if (result.getAuthStatus() == SUCCESS) {

            switch (result.getUser().getRole()) {
                case USER:
                    Alert.info("Successful operation", "Login successful");
                    main.showUserPanel();
                    break;
                case TECHNICAL:
                    if (TechnicalService.existTechnicalInDatabase(result.getUser().getId())) {
                        Alert.info("Successful operation", "Login successful");
                        main.showTechnicalPanel();
                    } else {
                        Alert.error("ERROR", "Your credentials are correct and you have Technical role" + "\n" +
                                "but you don't actually have permissions to operate, contact your administrator.");
                    }
                    break;
                case ADMIN:
                    Alert.info("Successful operation", "Login successful, you are logged like administrator");
                    main.showAdminPanel();
                    break;
            }
        }
    }
}