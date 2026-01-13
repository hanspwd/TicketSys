package util;

import view.LoginPanel;
import view.RegisterPanel;

public class FormUtils {

    public static void clearRegisterInputs(RegisterPanel registerPanel) {
        registerPanel.getTxtName().setText("");
        registerPanel.getTxtEmail().setText("");
        registerPanel.getTxtPassword().setText("");
        registerPanel.getTxtConfirmPassword().setText("");
    }

    public static void clearLoginInputs(LoginPanel loginPanel) {
        loginPanel.getTxtEmail().setText("");
        loginPanel.getTxtPassword().setText("");
    }
}
