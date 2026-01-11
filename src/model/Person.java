package model;

public abstract class Person {

    private int id;
    private String name;
    private String email;

    public Person(int id, String name, String email) throws Exception {
        this.id = id;
        this.setName(name);
        this.setEmail(email);
    }

    // FOR DB
    public Person(int id, String name, String email, boolean fromDb) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (!name.isBlank())  {
            this.name = name;
        } else  {
            throw new Exception("El nombre no puede estar vacío");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if (!email.isBlank()) {
            this.email = email;
        } else {
            throw new Exception("El email no puede estar vacío");
        }
    }
}
