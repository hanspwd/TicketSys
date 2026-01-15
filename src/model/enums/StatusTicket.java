package model.enums;

public enum StatusTicket {

    OPEN("OPEN"),
    IN_PROGRESS("IN PROGRESS"),
    CLOSED("CLOSED");

    private final String name;

    StatusTicket(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
