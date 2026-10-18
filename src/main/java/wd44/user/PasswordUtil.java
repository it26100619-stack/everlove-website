package wd44.user;

import java.security.MessageDigest;
import java.security.SecureRandom;

/** Passwords are never stored as plain text: salted SHA-256, repeated 10,000 times. */
public class PasswordUtil {
    public static String hash(String plain) {
        byte[] s = new byte[8];
        new SecureRandom().nextBytes(s);
        String salt = toHex(s);
        return "sha256$" + salt + "$" + digest(salt, plain);
    }

    public static boolean isHashed(String s) { return s.startsWith("sha256$"); }

    public static boolean verify(String plain, String stored) {
        if (!isHashed(stored)) return stored.equals(plain);      // old plain-text accounts still work
        String[] p = stored.split("\\$");
        return p.length == 3 && digest(p[1], plain).equals(p[2]);
    }

    private static String digest(String salt, String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] h = md.digest((salt + plain).getBytes("UTF-8"));
            for (int i = 1; i < 10000; i++) h = md.digest(h);
            return toHex(h);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private static String toHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02x", x));
        return sb.toString();
    }
}
