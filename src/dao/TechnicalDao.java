package dao;

import config.Conexion;
import model.Technical;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TechnicalDao implements ICrudDao<Technical> {

    @Override
    public void create(Technical technical) throws SQLException {
        String insert = "INSERT INTO technical (id_person, id_specialty) VALUES (?, ?)";

        try (Connection con = new Conexion().connect()) {
            PreparedStatement ps = con.prepareStatement(insert);

            ps.setInt(1, technical.getId());
            ps.setInt(2, technical.getSpecialty().getSpecialityId());

            ps.executeUpdate();
        }
    }

    @Override
    public Technical readById(int id) throws SQLException {
        return find("SELECT * FROM technical WHERE id_person = ?", id);
    }

    @Override
    public Technical findByName(String name) throws SQLException {
        throw new UnsupportedOperationException("findByName not supported for Technical.");
    }

    @Override
    public Technical findByEmail(String email) throws SQLException {
        throw new UnsupportedOperationException("findByEmail not supported for Technical.");
    }

    private Technical find(String query, Object param) throws SQLException {
        try (Connection con = new Conexion().connect();
             PreparedStatement ps = con.prepareStatement(query)) {

            if (param instanceof String) {
                ps.setString(1, (String) param);
            } else if (param instanceof Integer) {
                ps.setInt(1, (Integer) param);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Technical.fromDb(
                        rs.getInt("id_person"),
                        rs.getInt("id_specialty")
                );
            }
            return null;
        }
    }

    @Override
    public List<Technical> readAll() throws SQLException {
        String query = "SELECT * FROM technical";
        List<Technical> technicals = new ArrayList<>();

        try (Connection con = new Conexion().connect();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                technicals.add(Technical.fromDb(
                        rs.getInt("id_person"),
                        rs.getInt("id_specialty")
                ));
            }
        }
        return technicals;
    }

    /**
     * New important method:
     * Retrieve a list of technicians with complete information from PERSON + TECHNICAL
     */
    public List<Technical> readAllFull() throws SQLException {
        String query =
                "SELECT p.id_person, p.name, p.email, t.id_specialty " +
                        "FROM technical t " +
                        "INNER JOIN person p ON p.id_person = t.id_person";

        List<Technical> technicals = new ArrayList<>();

        try (Connection con = new Conexion().connect();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Technical t = Technical.fromDbFull(
                        rs.getInt("id_person"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("id_specialty")
                );
                technicals.add(t);
            }
        }
        return technicals;
    }


    @Override
    public void update(Technical technical) throws SQLException {
        String update = "UPDATE technical SET id_specialty = ? WHERE id_person = ?";
        try (Connection con = new Conexion().connect();
             PreparedStatement ps = con.prepareStatement(update)) {
            ps.setInt(1, technical.getSpecialty().getSpecialityId());
            ps.setInt(2, technical.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String delete = "DELETE FROM technical WHERE id_person = ?";
        try (Connection con = new Conexion().connect();
             PreparedStatement ps = con.prepareStatement(delete)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

}
