package wd44.vendor;

/** Abstract vendor. Each subclass prices differently: abstraction and polymorphism. Fields are private. */
public abstract class Vendor {
    private final String id, name, category, city;
    private final double basePrice;

    protected Vendor(String[] p) {
        id = p[0]; name = p[1]; category = p[2]; city = p[3];
        basePrice = Double.parseDouble(p[4].trim());
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getCity() { return city; }
    public double getBasePrice() { return basePrice; }

    public abstract double price(int guests);            // polymorphic price calculation
    public abstract String getPricing();                 // polymorphic display text

    public String toLine() { return String.join("|", id, name, category, city, String.valueOf(basePrice)); }

    /** Factory: caterers are priced per guest, every other category is a flat rate. */
    public static Vendor fromLine(String line) {
        String[] p = line.split("\\|", -1);
        if (p[2].equalsIgnoreCase("Caterer")) return new PerGuestVendor(p);
        return new FlatRateVendor(p);
    }
}
