package wd44.user;

/** A couple who plans a wedding. Inherits everything from User. */
public class Couple extends User {
    public Couple(String id, String name, String email, String password, String phone) {
        super(id, name, email, password, phone);
    }
    public static Couple fromLine(String line) {
        String[] p = line.split("\\|", -1);
        return new Couple(p[0], p[1], p[2], p[3], p[4]);
    }
    public String getPhone() { return getExtra(); }
    @Override public String getRole() { return "Couple"; }
}
