package controller.users.admin;

import controller.MainController;
import view.admin.UserManagementPanel;
import view.admin.AdminMainView;

import javax.swing.JOptionPane;

public class AdminController {

    private MainController main;
    private AdminMainView adminMainView;
    private UserManagementPanel userManagementPanel;
    
    // Sub-controller dinámico
    private UserManagementController userManagementController;

    public AdminController(MainController main, AdminMainView adminMainView) {
        this.main = main;
        this.adminMainView = adminMainView;
        init();
    }

    private void init() {
        
        // init admin panels
        userManagementPanel = new UserManagementPanel();
        
        // set panels
        adminMainView.getMainAdminPanel().add(userManagementPanel, "userManagement");

        // default panel
        adminMainView.getCardLayout().show(adminMainView.getMainAdminPanel(), "userManagement");
        if(userManagementController==null) {
            userManagementController = new UserManagementController(userManagementPanel);
        }

        setLateralMenuNavButtons();

    }

    public void setLateralMenuNavButtons() {

        adminMainView.getBtnUserManagement().addActionListener(e -> {
            adminMainView.getCardLayout().show(adminMainView.getMainAdminPanel(), "userManagement");
            
            // Inicializar UserManagementController solo cuando se accede al panel
            if (userManagementController == null) {
                userManagementController = new UserManagementController(userManagementPanel);
            }
        });
        
        // Agregar listener para Sign Out
        adminMainView.getBtnSignOut().addActionListener(e -> {
            // Confirmar sign out
            int confirm = JOptionPane.showConfirmDialog(
                adminMainView,
                "Are you sure you want to sign out?",
                "Confirm Sign Out",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Volver al menú principal
                main.showMenu();
            }
        });

    }
    
    /**
     * Método para limpiar recursos y liberar memoria
     */
    public void cleanup() {
        cleanupUserManagementController();
    }
    
    /**
     * Limpia el UserManagementController y libera memoria
     */
    private void cleanupUserManagementController() {
        if (userManagementController != null) {
            // Limpiar listeners y referencias si es necesario
            userManagementController = null;
            // Sugerir al garbage collector
            System.gc();
        }
    }

}