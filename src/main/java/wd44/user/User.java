package wd44.user;

/** Abstract base for every account (Couple and Admin). Fields are private: encapsulation and information hiding. */
public abstract class User {
    private final String id, name, email, password, extra;   // extra = phone (Couple) or permissions (Admin)

    protected User(String id, String name, String email, String password, String extra) {
        this.id = id; this.name = name; this.email = email; this.password = password; this.extra = extra;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    protected String getExtra() { return extra; }
    public boolean checkPassword(String pw) { return PasswordUtil.verify(pw, password); }

    public abstract String getRole();                    // abstraction: each subclass defines its role
    public boolean isAdmin() { return false; }           // overridden by Admin (polymorphism)

    public String toLine() { return String.join("|", id, name, email, password, extra); }
}
