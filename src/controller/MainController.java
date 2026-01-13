package controller;

import util.FormUtils;
import view.*;

public class MainController {
    
    private MainView mainView;
    private RegisterPanel registerPanel;
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;

    public MainController() {
        init();
    }

    private void init() {

        // init panels
        mainView = new MainView();
        mainMenuPanel = new MainMenuPanel();
        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();

        // set panels
        mainView.getMainPanel().add(mainMenuPanel, "menu");
        mainView.getMainPanel().add(loginPanel, "login");
        mainView.getMainPanel().add(registerPanel, "register");
        
        // default panel
        mainView.getCardLayout().show(mainView.getMainPanel(), "menu");
        
        // sets
        mainView.setVisible(true);
        mainView.setTitle("Ticket System (Menu)");
        setMenuNavButtons();

        //init controllers
        initControllers();
    }

    private void setMenuNavButtons() {
        mainMenuPanel.getBtnSignIn().addActionListener((e) -> { showLoginMenu(); });
        mainMenuPanel.getBtnSignUp().addActionListener((e) -> { showRegisterMenu(); });
    }

    public void showLoginMenu() {
        mainView.getCardLayout().show(mainView.getMainPanel(), "login");
        mainView.setTitle("Ticket System (Login)");
    }

    public void showRegisterMenu() {
        mainView.getCardLayout().show(mainView.getMainPanel(), "register");
        mainView.setTitle("Ticket System (Register)");
    }

    public void showMenu() {
        mainView.getCardLayout().show(mainView.getMainPanel(), "menu");
        mainView.setTitle("Ticket System (Menu)");

        FormUtils.clearLoginInputs(loginPanel);
        FormUtils.clearRegisterInputs(registerPanel);
    }

    public void initControllers() {
        RegisterController registerController = new RegisterController(this, registerPanel);
        LoginController loginController = new LoginController(this, loginPanel);
    }

}
