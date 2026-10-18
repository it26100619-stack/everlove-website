package wd44.booking;

/** A vendor booking. Fields are private; instances never change (withStatus returns a new object). */
public class Booking {
    private final String id;
    private final String coupleId;
    private final String vendorId;
    private final String date;
    private final int guests;
    private final double total;
    private final String status;

    public Booking(String id, String coupleId, String vendorId, String date, String guests, String total, String status) {
        this.id = id;
        this.coupleId = coupleId;
        this.vendorId = vendorId;
        this.date = date;
        this.guests = Integer.parseInt(guests.trim());
        this.total = Double.parseDouble(total.trim());
        this.status = status;
    }

    public static Booking fromLine(String line) {
        String[] p = line.split("\\|", -1);
        return new Booking(p[0], p[1], p[2], p[3], p[4], p[5], p[6]);
    }

    public String toLine() {
        return String.join("|", String.valueOf(id), String.valueOf(coupleId), String.valueOf(vendorId), String.valueOf(date), String.valueOf(guests), String.valueOf(total), String.valueOf(status));
    }

    public String getId() { return id; }
    public String getCoupleId() { return coupleId; }
    public String getVendorId() { return vendorId; }
    public String getDate() { return date; }
    public int getGuests() { return guests; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public Booking withStatus(String newStatus) {
        return new Booking(id, coupleId, vendorId, date, String.valueOf(guests), String.valueOf(total), newStatus);
    }
}
