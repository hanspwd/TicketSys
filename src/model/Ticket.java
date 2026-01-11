package model;

import model.util.StatusTicket;

import java.time.LocalDateTime;

public final class Ticket {

    private int id;
    private String title;
    private String description;
    private StatusTicket status;
    private LocalDateTime creationDate;
    private User user;
    private Technical assignedTechnical;

    // Cuando el usuario apenas cree el ticket se utilizará este constructor
    // porque aún no tiene un técnico asignado.
    public Ticket(String title, String description, User user) throws Exception {
        this.setTitle(title);
        this.setDescription(description);
        this.setUser(user);
        this.setStatus(StatusTicket.OPEN);
    }

    // Este constructor lo utilizará el DAO al traer los datos desde la base de datos.
    // No usa setters porque se confía en los datos entregados de la base de datos (Buena práctica).
    public Ticket(int id, String title, String description, StatusTicket status, LocalDateTime creationDate, User user) {
        this.id = id; // Lo maneja la db, es auto incremental.
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate; // Lo maneja la db, agarra la fecha y hora del servidor.
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws Exception {
        if(!title.isBlank()) {
            this.title = title;
        } else  {
            throw new Exception("El titulo del ticket no puede estar vacío");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws Exception {
        if(!description.isBlank()) {
            this.description = description;
        } else {
            throw new Exception("La descripción del ticket no puede estar vacia");
        }
    }

    public StatusTicket getStatus() {
        return status;
    }

    public void setStatus(StatusTicket status) throws Exception{
        if (status != null) {
            this.status = status;
        } else {
            throw new Exception("El status del ticket no puede ser null");
        }
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws Exception {
        if(user != null) {
            this.user = user;
        } else {
            throw new Exception("El usuario a asignar al ticket no puede ser null");
        }
    }

    public Technical getAssignedTechnical() {
        return assignedTechnical;
    }

    // Cuando el técnico quiera autoasignarse un ticket se utilizara setAssignedTechnical en el service
    public void setAssignedTechnical(Technical assignedTechnical) throws Exception {
        if(assignedTechnical != null) {
            this.assignedTechnical = assignedTechnical;
        } else {
            throw new Exception("El técnico a asignar al ticket no puede ser null");
        }
    }
}
