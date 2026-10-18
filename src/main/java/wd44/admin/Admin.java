package wd44.admin;

import wd44.user.User;

/** An administrator. Inherits from User and overrides isAdmin (polymorphism). */
public class Admin extends User {
    public Admin(String id, String name, String email, String password, String permissions) {
        super(id, name, email, password, permissions);
    }
    public static Admin fromLine(String line) {
        String[] p = line.split("\\|", -1);
        return new Admin(p[0], p[1], p[2], p[3], p[4]);
    }
    public String getPermissions() { return getExtra(); }
    @Override public String getRole() { return "Admin"; }
    @Override public boolean isAdmin() { return true; }
}
