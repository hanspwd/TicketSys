package model.util;

public enum Role {

    USER(1),
    TECHNICAL(2);

    private final int roleId;

    Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public static Role fromId(int id) {
        for (Role role : Role.values()) {
            if (role.getRoleId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Role id " + id + " not found");
    }
}
