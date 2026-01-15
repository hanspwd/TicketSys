package model;

import model.enums.Role;

public class User extends Person{

    private Role role = Role.USER;
    private String password;

    public User(int id, String name, String email) throws Exception {
        super(id, name, email);
    }

    public User(String name, String email, String password) throws Exception {
        super(name, email);
        this.setPassword(password);
    }

    // CONSTRUCTORS FOR DB

    // BYPASS CONSTRUCTOR WITHOUT VALIDATIONS FOR DATABASE WORK (DAO'S) -> FROM PERSON
    protected User(int id, String name, String email, boolean fromDb) {
        super(id, name, email, fromDb);
    }

    // FOR GET USER (id, email, name, etc) -> DAO
    public static User fromDb(int id, String name, String email, String password, Role role) {
        User u = new User(id, name, email, true);
        u.password = password;
        u.role = role;
        return u;
    }

    // FOR LIST ALL STRUCTURE - DAO
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
            throw new Exception("Rol can't be null");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if(!password.isBlank()) {
            this.password = password;
        } else {
            throw new Exception("The password can't be empty");
        }
    }
}
