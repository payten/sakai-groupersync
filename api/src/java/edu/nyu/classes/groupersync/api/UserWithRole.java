package edu.nyu.classes.groupersync.api;

public class UserWithRole {
    private final String username;
    private final String role;

    public UserWithRole(String username, String role) {
        this.username = username;
        this.role = normalizeRole(role);
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String toString() {
        return String.format("#<%s [%s]>", username, role);
    }

    private String hashKey() {
        return username + "_" + role;
    }

    public int hashCode() {
        return hashKey().hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof UserWithRole)) {
            return false;
        }

        return ((UserWithRole) other).hashKey().equals(hashKey());
    }

    private String normalizeRole(String role) {
        if ("manager".equals(role) || "viewer".equals(role)) {
            // Already fine!
            return role;
        }

        if (role == null) {
            return "viewer";
        }

        if (role.toLowerCase().startsWith("i") || role.toLowerCase().equals("maintain")) {
            // startsWith here because the CM API uses "I" and Sakai uses "Instructor".
            return "manager";
        } else {
            return "viewer";
        }
    }

    public boolean isMorePowerfulThan(UserWithRole other) {
        return "manager".equals(role) && !"manager".equals(other.getRole());
    }
}
