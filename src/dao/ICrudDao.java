package dao;

import java.sql.SQLException;
import java.util.List;

public interface ICrudDao<T> {

    void create(T t) throws SQLException;
    T readById(int id) throws SQLException;
    T findByName(String name) throws SQLException;
    T findByEmail(String email) throws SQLException;
    List<T> readAll() throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;

}
