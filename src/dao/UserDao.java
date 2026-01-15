package dao;

import config.Conexion;
import model.User;
import model.enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements ICrudDao<User> {

    @Override
    public void create(User user) throws SQLException {
        String insert = "INSERT INTO person (name, email, hash_pass, id_rol) VALUES (?, ?, ?, ?)";

        try (Connection con = new Conexion().connect();
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getRoleId());

            ps.executeUpdate();
        }
    }

    @Override
    public User readById(int id) throws SQLException {
        return find("SELECT * FROM person WHERE id_person = ?", id);
    }

    @Override
    public User findByName(String name) throws SQLException {
        return find("SELECT * FROM person WHERE name = ?", name);
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        return find("SELECT * FROM person WHERE email = ?", email);
    }

    private User find(String query, Object param) throws SQLException {
        try (Connection con = new Conexion().connect();
             PreparedStatement ps = con.prepareStatement(query)) {

            if (param instanceof String) {
                ps.setString(1, (String) param);
            } else if (param instanceof Integer) {
                ps.setInt(1, (Integer) param);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return User.fromDb(
                        rs.getInt("id_person"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("hash_pass"),
                        Role.fromId(rs.getInt("id_rol"))
                );
            }
            return null;
        }
    }

    @Override
    public List<User> readAll() throws SQLException {
        String query = "SELECT * FROM person";
        List<User> users = new ArrayList<>();

        try (Connection con = new Conexion().connect();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(User.fromDb(
                        rs.getInt("id_person"),
                        rs.getString("name"),
                        rs.getString("email"),
                        Role.fromId(rs.getInt("id_rol"))
                ));
            }
        }
        return users;
    }

    @Override
    public void update(User user) throws SQLException {
        String update = "UPDATE person SET name = ?, email = ? WHERE id_person = ?";

        try (Connection con = new Conexion().connect();
             PreparedStatement ps = con.prepareStatement(update)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getId());
            ps.executeUpdate();
        }
    }

    /*
    * Se implementara cuando este listo el panel de User, Technical y Admin.
    *
    public void changePassword(int id, String newPassword) throws SQLException {
        String updatePassword = "UPDATE person SET hash_pass = ? WHERE id_person = ?";
        try (Connection con = new Conexion().connect();) {
            PreparedStatement ps = con.prepareStatement(updatePassword);
            ps.setString(1, newPassword);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
    */


    // Funcionalidad para admin, capacidad de cambiar rol a los usuarios.
    public void updateRole(int id, int roleId) throws SQLException {
        String updateRole = "UPDATE person SET id_rol = ? WHERE id_person = ?";
        try (Connection con = new Conexion().connect();) {
            PreparedStatement ps = con.prepareStatement(updateRole);
            ps.setInt(1, roleId);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String delete = "DELETE FROM person WHERE id_person = ?";

        try (Connection con = new Conexion().connect();
             PreparedStatement ps = con.prepareStatement(delete)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
