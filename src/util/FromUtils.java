package util;

import view.RegisterPanel;

public class FromUtils {

    public static void clearRegisterInputs(RegisterPanel registerPanel) {
        registerPanel.getTxtName().setText("");
        registerPanel.getTxtEmail().setText("");
        registerPanel.getTxtPassword().setText("");
        registerPanel.getTxtConfirmPassword().setText("");
    }
}
