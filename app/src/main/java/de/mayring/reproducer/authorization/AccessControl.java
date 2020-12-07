package de.mayring.reproducer.authorization;

public enum AccessControl {
    READ, EDIT, ADMIN;

	public static AccessControl get(String roleStr) {
        for (AccessControl accessControl : AccessControl.values()) {
            if (accessControl.toString().equals(roleStr)) return accessControl;
        }
        return null;
    }

}
