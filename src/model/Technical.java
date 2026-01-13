package model;

import model.enums.Role;
import model.enums.Specialty;

public class Technical extends Person{

    private Specialty specialty;
    private Role role = Role.USER;
    private String password;

    public Technical(int id, String name, String email, Specialty specialty) throws Exception{
        super(id, name, email);
        this.setSpecialty(specialty);
    }

    public Technical(int id, String name, String email, Specialty specialty, String password) throws Exception {
        super(id, name, email);
        this.setSpecialty(specialty);
        this.setPassword(password);
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) throws Exception {
        if(specialty != null) {
            this.specialty = specialty;
        } else  {
            throw new Exception("La especialidad no puede estar null");
        }
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
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
