package model.util;

public enum Role {

    USER(1),
    TECHNICAL(2);

    final int roleId;

    Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
