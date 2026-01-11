package model;

import model.util.Role;

public class User extends Person{

    private Role role =  Role.USER;
    private String password;

    public User(int id, String name, String email) throws Exception {
        super(id, name, email);
    }

    // BYPASS CONSTRUCTOR WITHOUT VALIDATIONS FOR DATABASE WORK (DAO'S)
    protected User(int id, String name, String email, boolean fromDb) {
        super(id, name, email, fromDb);
    }

    // BYPASS METHOD WITHOUT VALIDATIONS FOR DATABASE WORK (DAO'S)
    public static User fromDb(int id, String name, String email, String password, Role role) {
        User u = new User(id, name, email, true);
        u.password = password;
        u.role = role;
        return u;
    }

    public static User fromDb(int id, String name, String email, Role role) {
        User u = new User(id, name, email, true);
        u.role = role;
        return u;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) throws Exception {
        if(!(role == null)){
            this.role = role;
        } else {
            throw new Exception("El rol no puede ser null");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if(!password.isBlank()) {
            this.password = password;
        } else {
            throw new Exception("La contraseña no puede estar vacía");
        }
    }
}
