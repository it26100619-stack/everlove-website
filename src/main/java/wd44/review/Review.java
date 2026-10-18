package wd44.review;

/** A rating and comment for a vendor. The constructor keeps the rating between 1 and 5 (information hiding). */
public class Review {
    private final String id;
    private final String vendorId;
    private final String coupleId;
    private final int rating;
    private final String comment;

    public Review(String id, String vendorId, String coupleId, String rating, String comment) {
        this.id = id;
        this.vendorId = vendorId;
        this.coupleId = coupleId;
        this.rating = Math.max(1, Math.min(5, Integer.parseInt(rating.trim())));
        this.comment = comment;
    }

    public static Review fromLine(String line) {
        String[] p = line.split("\\|", -1);
        return new Review(p[0], p[1], p[2], p[3], p[4]);
    }

    public String toLine() {
        return String.join("|", String.valueOf(id), String.valueOf(vendorId), String.valueOf(coupleId), String.valueOf(rating), String.valueOf(comment));
    }

    public String getId() { return id; }
    public String getVendorId() { return vendorId; }
    public String getCoupleId() { return coupleId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
}
