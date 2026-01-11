package dao;

import config.Conexion;
import model.User;
import model.util.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements ICrudDao<User> {

    private final Conexion cnx;

    public UserDao(Conexion cnx) {
        this.cnx = cnx;
    }

    // FOR REGISTER
    @Override
    public void create(User user) throws SQLException {
        String insert = "INSERT INTO `person` (`id_person`, `name`, `email`, `hash_pass`, `id_rol`) VALUES (NULL, ?, ?, ?, ?)";

        try (Connection con = cnx.conectar()) {
            PreparedStatement ps = con.prepareStatement(insert);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getRoleId()); // Default rol (1 = User) (2 = Technical)

            ps.executeUpdate();
        }
    }

    @Override
    public User readById(int id) throws SQLException {
        String select = "SELECT * FROM `person` WHERE `id_person` = ?";
        return getUser(cnx, select, id);
    }

    @Override
    public User findByName(String name) throws SQLException {
        String select = "SELECT * FROM `person` WHERE `name` = ?";
        return getUser(cnx, select, name);
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        String select = "SELECT * FROM `person` WHERE `email` = ?";
        return getUser(cnx, select, email);
    }

    @Override
    public List<User> readAll() throws SQLException {
        String select = "SELECT * FROM `person`";
        List<User> users = new ArrayList<>();

        try (Connection con = cnx.conectar()) {
            PreparedStatement ps = con.prepareStatement(select);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("id_person");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Role role = Role.fromId(rs.getInt("id_rol"));

                users.add(User.fromDb(userId, name, email, role));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    // Si el usuario decide actualizar algún aspecto de su perfil.
    @Override
    public void update(User user) throws SQLException {
        String update = "UPDATE person SET name=?, email=? WHERE id_person=?;";

        try(Connection con = cnx.conectar()) {
            PreparedStatement ps = con.prepareStatement(update);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getId());
            ps.executeUpdate();
        }
    }

    // IMPLEMENTAR LUEGO UPDATE PASSWORD PARA LOS USUARIOS.

    // IMPLEMENTAR LUEGO UPDATE ROLE PARA ADMINISTRADORES.

    @Override
    public void delete(int id) throws SQLException {
        String delete = "DELETE FROM person WHERE id_person=?;";
        try(Connection con = cnx.conectar()) {
            PreparedStatement ps = con.prepareStatement(delete);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private User getUser(Conexion cnx, String select, Object o) {
        try (Connection con = cnx.conectar()) {
            PreparedStatement ps = con.prepareStatement(select);

            if (o instanceof String) {
                ps.setString(1, (String) o);
            } else if (o instanceof Integer) {
                ps.setInt(1, (Integer) o);
            } else {
                throw new IllegalArgumentException("Unsupported parameter type");
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idUser = rs.getInt("id_person");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("hash_pass");
                Role role = Role.fromId(rs.getInt("id_rol"));

                return User.fromDb(idUser, name, email, password, role);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
