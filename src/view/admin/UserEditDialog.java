package view.admin;

import model.User;
import model.enums.Role;

import javax.swing.*;
import java.awt.*;

public class UserEditDialog extends JDialog {

    private User user;
    private boolean saved = false;

    private JTextField txtName;
    private JTextField txtEmail;
    private JComboBox<Role> cmbRole;
    private JButton btnSave;
    private JButton btnCancel;

    public UserEditDialog(Frame parent, User user) {
        super(parent, "Edit User", true);
        this.user = user;
        initializeComponents();
        layoutComponents();
        setupEventListeners();
        loadUserData();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        txtName = new JTextField(20);
        txtEmail = new JTextField(20);
        cmbRole = new JComboBox<>(Role.values());
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(cmbRole, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventListeners() {
        btnSave.addActionListener(e -> handleSave());
        btnCancel.addActionListener(e -> handleCancel());
    }

    private void loadUserData() {
        if (user != null) {
            txtName.setText(user.getName());
            txtEmail.setText(user.getEmail());
            cmbRole.setSelectedItem(user.getRole());
        }
    }

    private void handleSave() {
        try {
            // Validar campos
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            Role selectedRole = (Role) cmbRole.getSelectedItem();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, "Invalid email format", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar usuario
            user.setName(name);
            user.setEmail(email);
            user.setRole(selectedRole);

            saved = true;
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancel() {
        saved = false;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }

    public User getUser() {
        return user;
    }
}