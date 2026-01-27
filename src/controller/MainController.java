package controller;

import controller.auth.LoginController;
import controller.auth.RegisterController;
import controller.users.admin.AdminController;
import view.user.UserMainView;
import view.technical.TechnicalMainView;
import view.layout.MainView;
import view.common.MainMenuPanel;
import view.admin.AdminMainView;
import view.auth.LoginPanel;
import view.auth.RegisterPanel;
import util.FormUtils;
import javax.swing.JOptionPane;

public class MainController {
    
    private MainView mainView;
    private RegisterPanel registerPanel;
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;
    private UserMainView userPanel; // cambiar a userMainView
    private TechnicalMainView technicalPanel; // cambiar a technicalMainView
    private AdminMainView adminMainView;
    
    // Sub-controllers dinámicos
    private LoginController loginController;
    private RegisterController registerController;
    private AdminController adminController;

    public MainController() {
        init();
    }

    private void init() {

        // init panels
        mainView = new MainView();
        mainMenuPanel = new MainMenuPanel();
        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
        userPanel = new UserMainView();
        technicalPanel = new TechnicalMainView();
        adminMainView = new AdminMainView();

        // set panels
        mainView.getMainPanel().add(mainMenuPanel, "menu");
        mainView.getMainPanel().add(loginPanel, "login");
        mainView.getMainPanel().add(registerPanel, "register");
        mainView.getMainPanel().add(userPanel, "user");
        mainView.getMainPanel().add(technicalPanel, "technical");
        mainView.getMainPanel().add(adminMainView, "admin");
        
        // default panel
        mainView.getCardLayout().show(mainView.getMainPanel(), "menu");
        
        // sets
        mainView.setVisible(true);
        mainView.setTitle("Ticket System (Menu)");
        setMenuNavButtons();

        // Controllers se inicializarán dinámicamente cuando se necesiten
    }

    private void setMenuNavButtons() {
        mainMenuPanel.getBtnSignIn().addActionListener((e) -> { showLoginMenu(); });
        mainMenuPanel.getBtnSignUp().addActionListener((e) -> { showRegisterMenu(); });
    }

    public void showLoginMenu() {
        // Inicializar LoginController solo cuando se accede al login
        if (loginController == null) {
            loginController = new LoginController(this, loginPanel);
        }
        
        // Limpiar otros controllers
        cleanupControllersExcept("login");
        
        mainView.getCardLayout().show(mainView.getMainPanel(), "login");
        mainView.setTitle("Ticket System (Login)");
    }

    public void showRegisterMenu() {
        // Inicializar RegisterController solo cuando se accede al register
        if (registerController == null) {
            registerController = new RegisterController(this, registerPanel);
        }
        
        // Limpiar otros controllers
        cleanupControllersExcept("register");
        
        mainView.getCardLayout().show(mainView.getMainPanel(), "register");
        mainView.setTitle("Ticket System (Register)");
    }

    public void showUserPanel() {
        // Limpiar otros controllers (UserController podría tener sub-controllers en el futuro)
        cleanupControllersExcept("user");
        
        mainView.getCardLayout().show(mainView.getMainPanel(), "user");
        mainView.setTitle("Ticket System (User)");
        
        // Agregar listener para Sign Out si no existe
        setupUserPanelSignOut();
    }

    public void showTechnicalPanel() {
        // Limpiar otros controllers (TechnicalController podría tener sub-controllers en el futuro)
        cleanupControllersExcept("technical");
        
        mainView.getCardLayout().show(mainView.getMainPanel(), "technical");
        mainView.setTitle("Ticket System (Technical)");
        
        // Agregar listener para Sign Out si no existe
        setupTechnicalPanelSignOut();
    }

    public void showAdminPanel() {
        // Inicializar AdminController solo cuando se accede al admin
        if (adminController == null) {
            adminController = new AdminController(this, adminMainView);
        }
        
        // Limpiar otros controllers
        cleanupControllersExcept("admin");
        
        mainView.getCardLayout().show(mainView.getMainPanel(), "admin");
        mainView.setTitle("Ticket System (Admin)");
    }

    public void showMenu() {
        // Limpiar todos los controllers al volver al menú
        cleanupAllControllers();
        
        mainView.getCardLayout().show(mainView.getMainPanel(), "menu");
        mainView.setTitle("Ticket System (Menu)");

        FormUtils.clearLoginInputs(loginPanel);
        FormUtils.clearRegisterInputs(registerPanel);
    }

    // Métodos de gestión dinámica de controllers
    
    /**
     * Limpia todos los controllers excepto el especificado
     * @param activePanel Panel que debe mantener su controller activo
     */
    private void cleanupControllersExcept(String activePanel) {
        switch (activePanel) {
            case "login":
                cleanupRegisterController();
                cleanupAdminController();
                break;
            case "register":
                cleanupLoginController();
                cleanupAdminController();
                break;
            case "admin":
                cleanupLoginController();
                cleanupRegisterController();
                break;
            case "user":
            case "technical":
                cleanupLoginController();
                cleanupRegisterController();
                cleanupAdminController();
                break;
        }
    }
    
    /**
     * Limpia todos los controllers
     */
    private void cleanupAllControllers() {
        cleanupLoginController();
        cleanupRegisterController();
        cleanupAdminController();
    }
    
    /**
     * Limpia el LoginController y libera memoria
     */
    private void cleanupLoginController() {
        if (loginController != null) {
            // Limpiar listeners y referencias si es necesario
            loginController = null;
            // Sugerir al garbage collector
            System.gc();
        }
    }
    
    /**
     * Limpia el RegisterController y libera memoria
     */
    private void cleanupRegisterController() {
        if (registerController != null) {
            // Limpiar listeners y referencias si es necesario
            registerController = null;
            // Sugerir al garbage collector
            System.gc();
        }
    }
    
    /**
     * Limpia el AdminController y libera memoria
     */
    private void cleanupAdminController() {
        if (adminController != null) {
            // Limpiar listeners y referencias si es necesario
            adminController.cleanup();
            adminController = null;
            // Sugerir al garbage collector
            System.gc();
        }
    }
    
    /**
     * Configura el botón Sign Out para UserMainView
     */
    private void setupUserPanelSignOut() {
        // Evitar múltiples listeners
        userPanel.getBtnSignOut().removeActionListener(userSignOutListener);
        userPanel.getBtnSignOut().addActionListener(userSignOutListener);
    }
    
    /**
     * Configura el botón Sign Out para TechnicalMainView
     */
    private void setupTechnicalPanelSignOut() {
        // Evitar múltiples listeners
        technicalPanel.getBtnSignOut().removeActionListener(technicalSignOutListener);
        technicalPanel.getBtnSignOut().addActionListener(technicalSignOutListener);
    }
    
    // Listeners para Sign Out
    private final java.awt.event.ActionListener userSignOutListener = e -> {
        int confirm = JOptionPane.showConfirmDialog(
            mainView,
            "Are you sure you want to sign out?",
            "Confirm Sign Out",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            showMenu();
        }
    };
    
    private final java.awt.event.ActionListener technicalSignOutListener = e -> {
        int confirm = JOptionPane.showConfirmDialog(
            mainView,
            "Are you sure you want to sign out?",
            "Confirm Sign Out",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            showMenu();
        }
    };

}