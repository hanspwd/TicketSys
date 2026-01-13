package model.enums;

public enum StatusTicket {

    OPEN("Abierto"),
    IN_PROGRESS("En progreso"),
    CLOSED("Cerrado");

    private final String name;

    StatusTicket(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
