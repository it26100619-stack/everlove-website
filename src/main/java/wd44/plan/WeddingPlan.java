package wd44.plan;

/** A couple's wedding plan. Fields are private; numbers are stored as real int and double values. */
public class WeddingPlan {
    private final String id;
    private final String coupleId;
    private final String date;
    private final String venue;
    private final double budget;
    private final int guests;

    public WeddingPlan(String id, String coupleId, String date, String venue, String budget, String guests) {
        this.id = id;
        this.coupleId = coupleId;
        this.date = date;
        this.venue = venue;
        this.budget = Double.parseDouble(budget.trim());
        this.guests = Integer.parseInt(guests.trim());
    }

    public static WeddingPlan fromLine(String line) {
        String[] p = line.split("\\|", -1);
        return new WeddingPlan(p[0], p[1], p[2], p[3], p[4], p[5]);
    }

    public String toLine() {
        return String.join("|", String.valueOf(id), String.valueOf(coupleId), String.valueOf(date), String.valueOf(venue), String.valueOf(budget), String.valueOf(guests));
    }

    public String getId() { return id; }
    public String getCoupleId() { return coupleId; }
    public String getDate() { return date; }
    public String getVenue() { return venue; }
    public double getBudget() { return budget; }
    public int getGuests() { return guests; }
}
