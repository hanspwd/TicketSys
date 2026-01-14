package controller;

import service.auth.AuthService;
import service.auth.LoginResult;
import view.Alert;
import view.LoginPanel;

import java.sql.SQLException;

import static model.enums.Role.TECHNICAL;
import static model.enums.Role.USER;
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

        loginFieldValidator(email, password);

        LoginResult result = AuthService.login(email, password);

        if(result.getAuthStatus() == SUCCESS) {
            // LOGIC TO MENU (UserPanel???? || TechnicalPanel????)
            Alert.info("Successful operation", "Login successful");

            if(result.getUser().getRole().equals(USER)) {
                main.showUserPanel();
            }

            if(result.getUser().getRole().equals(TECHNICAL)) {
                main.showTechnicalPanel();
            }
        }

    }
}
