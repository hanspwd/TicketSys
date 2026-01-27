package controller.users.admin;

import model.User;
import service.users.UserManagementService;
import view.admin.UserEditDialog;
import view.admin.UserManagementPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Frame;
import java.sql.SQLException;
import java.util.List;

public class UserManagementController {
    
    private final UserManagementPanel view;
    private final UserManagementService service;
    
    public UserManagementController(UserManagementPanel view) {
        this.view = view;
        this.service = new UserManagementService();
        init();
    }
    
    public void init() {
        setupEventListeners();
        clearFields();
        loadAllUsers();
    }
    
    private void setupEventListeners() {
        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getBtnListAll().addActionListener(e -> handleListAll());
        view.getBtnReset().addActionListener(e -> handleReset());
        view.getBtnDelete().addActionListener(e -> handleDelete());
        view.getBtnModify().addActionListener(e -> handleModify());
        
        view.getTxtSearchByID().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                handleSearchFieldChange("ID");
            }
        });
        
        view.getTxtSearchByName().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                handleSearchFieldChange("NAME");
            }
        });
        
        view.getTxtSearchByEmail().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                handleSearchFieldChange("EMAIL");
            }
        });
    }
    
    private void handleSearchFieldChange(String activeField) {
        switch (activeField) {
            case "ID":
                view.getTxtSearchByName().setEnabled(false);
                view.getTxtSearchByEmail().setEnabled(false);
                break;
            case "NAME":
                view.getTxtSearchByID().setEnabled(false);
                view.getTxtSearchByEmail().setEnabled(false);
                break;
            case "EMAIL":
                view.getTxtSearchByID().setEnabled(false);
                view.getTxtSearchByName().setEnabled(false);
                break;
        }
    }
    
    private void handleSearch() {
        try {
            String idText = view.getTxtSearchByID().getText().trim();
            String nameText = view.getTxtSearchByName().getText().trim();
            String emailText = view.getTxtSearchByEmail().getText().trim();
            
            User user = null;
            String searchCriteria = "";
            
            if (!idText.isEmpty()) {
                searchCriteria = "ID: " + idText;
                UserManagementService.SearchType searchType = UserManagementService.SearchType.ID;
                user = service.searchUser(idText, searchType);
                displaySearchResult(user, searchCriteria);
            } else if (!nameText.isEmpty()) {
                searchCriteria = "Name: " + nameText;
                UserManagementService.SearchResult result = service.searchUsersByName(nameText);
                displayNameSearchResult(result, nameText);
            } else if (!emailText.isEmpty()) {
                searchCriteria = "Email: " + emailText;
                UserManagementService.SearchType searchType = UserManagementService.SearchType.EMAIL;
                user = service.searchUser(emailText, searchType);
                displaySearchResult(user, searchCriteria);
            } else {
                JOptionPane.showMessageDialog(view, "Please enter search criteria in one of the fields", "Search Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Unexpected error during search: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void displaySearchResult(User user, String searchCriteria) {
        DefaultTableModel model = (DefaultTableModel) view.getTblUsers().getModel();
        model.setRowCount(0);
        
        if (user != null) {
            model.addRow(new Object[]{
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
            });
            // Mensaje de éxito con información del usuario encontrado
            JOptionPane.showMessageDialog(view, 
                "User found for " + searchCriteria + ":\n" +
                "Name: " + user.getName() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Role: " + user.getRole().name(),
                "Search Result - User Found", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, 
                "No user found for " + searchCriteria, 
                "Search Result - Not Found", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void displayNameSearchResult(UserManagementService.SearchResult result, String searchName) {
        DefaultTableModel model = (DefaultTableModel) view.getTblUsers().getModel();
        model.setRowCount(0);
        
        if (!result.hasResults()) {
            JOptionPane.showMessageDialog(view, 
                "No users found with name: " + searchName, 
                "Search Result - Not Found", 
                JOptionPane.WARNING_MESSAGE);
        } else if (result.isSingle()) {
            // Un único resultado - mostrar diálogo con info
            User user = result.getSingleUser();
            model.addRow(new Object[]{
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
            });
            
            JOptionPane.showMessageDialog(view, 
                "User found with name '" + searchName + "':\n\n" +
                "Name: " + user.getName() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Role: " + user.getRole().name(),
                "Search Result - Unique Match", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Múltiples resultados - mostrar todos en la tabla
            List<User> users = result.getMultipleUsers();
            displayUsersInTable(users);
            
            JOptionPane.showMessageDialog(view, 
                "Found " + users.size() + " users with name containing '" + searchName + "'.\n" +
                "All matching users have been displayed in the table below.",
                "Search Result - Multiple Matches", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void handleListAll() {
        try {
            List<User> users = service.getAllUsers();
            displayUsersInTable(users);
            
            // Mostrar información de cuántos usuarios se cargaron
            JOptionPane.showMessageDialog(view, 
                "Successfully loaded " + users.size() + " users from database", 
                "List All Users", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error loading users: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadAllUsers() {
        try {
            List<User> users = service.getAllUsers();
            displayUsersInTable(users);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error loading users: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void displayUsersInTable(List<User> users) {
        DefaultTableModel model = (DefaultTableModel) view.getTblUsers().getModel();
        model.setRowCount(0);
        
        for (User user : users) {
            model.addRow(new Object[]{
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
            });
        }
    }
    
    private void handleReset() {
        clearFields();
        enableAllSearchFields();
        
        // Limpiar selección en la tabla
        view.getTblUsers().clearSelection();
        
        // Mostrar mensaje de confirmación
        JOptionPane.showMessageDialog(view, 
            "All fields have been reset and search fields enabled", 
            "Reset Complete", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void clearFields() {
        view.getTxtSearchByID().setText("");
        view.getTxtSearchByName().setText("");
        view.getTxtSearchByEmail().setText("");
    }
    
    private void enableAllSearchFields() {
        view.getTxtSearchByID().setEnabled(true);
        view.getTxtSearchByName().setEnabled(true);
        view.getTxtSearchByEmail().setEnabled(true);
    }
    
    private void handleDelete() {
        int selectedRow = view.getTblUsers().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a user to delete", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int userId = (int) view.getTblUsers().getValueAt(selectedRow, 0);
            String userName = (String) view.getTblUsers().getValueAt(selectedRow, 1);
            String userEmail = (String) view.getTblUsers().getValueAt(selectedRow, 2);
            
            // Confirmación más detallada
            int confirm = JOptionPane.showConfirmDialog(
                view,
                "<html><b>Confirm Delete User</b><br><br>" +
                "Name: " + userName + "<br>" +
                "Email: " + userEmail + "<br>" +
                "ID: " + userId + "<br><br>" +
                "This action cannot be undone. Are you sure?</html>",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteUser(userId);
                JOptionPane.showMessageDialog(view, "User deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAllUsers();
                clearFields(); // Limpiar campos después de eliminar
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error deleting user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleModify() {
        int selectedRow = view.getTblUsers().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a user to modify", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Obtener datos del usuario seleccionado
            int userId = (int) view.getTblUsers().getValueAt(selectedRow, 0);
            User user = service.searchUserById(userId);
            
            if (user == null) {
                JOptionPane.showMessageDialog(view, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear y mostrar diálogo de edición
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(view);
            UserEditDialog editDialog = new UserEditDialog(parentFrame, user);
            editDialog.setVisible(true);
            
            if (editDialog.isSaved()) {
                // Actualizar usuario en la base de datos
                User updatedUser = editDialog.getUser();
                service.updateUser(updatedUser);
                
                JOptionPane.showMessageDialog(view, "User updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Recargar la tabla
                loadAllUsers();
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error modifying user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}