package model;

import model.enums.Role;
import model.enums.Specialty;

public class Technical extends Person{

    private Specialty specialty;
    private Role role = Role.TECHNICAL;
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

    // CONSTRUCTORS FOR DB

    protected Technical(int id, boolean fromDb) {
        super(id, fromDb);
    }

    protected Technical(int id, String name, String email,  boolean fromDb) {
        super(id, name, email, fromDb);
    }

    public static Technical fromDbFull(int idPerson, String name, String email, int idSpecialty) {
        Technical t = new Technical(idPerson, name, email, true);

        for (Specialty specialty : Specialty.values()) {
            if(idSpecialty == specialty.getSpecialityId()) {
                t.specialty = specialty;
            }
        }
        return t;
    }

    public static Technical fromDb(int id, int id_specialty) {
        Technical t = new Technical(id, true);

        for (Specialty specialty : Specialty.values()) {
            if(id_specialty == specialty.getSpecialityId()) {
                t.specialty = specialty;
            }
        }

        return t;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) throws Exception {
        if(specialty != null) {
            this.specialty = specialty;
        } else  {
            throw new Exception("Specialty can't be null");
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
            throw new Exception("The password can't be empty");
        }
    }
}
