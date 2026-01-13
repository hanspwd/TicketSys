package controller;

import service.AuthService;
import view.Alert;
import view.RegisterPanel;

import static util.FromUtils.clearRegisterInputs;
import static validation.InputValidator.registerFieldValidator;

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

        registerPanel.getBtnClear().addActionListener(e -> {
            clearRegisterInputs(registerPanel);
        });

        registerPanel.getBtnRegister().addActionListener(e -> {
            try {
                doRegister();
                clearRegisterInputs(registerPanel);
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
}
