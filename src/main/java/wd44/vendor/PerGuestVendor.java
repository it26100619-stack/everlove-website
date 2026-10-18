package wd44.vendor;

public class PerGuestVendor extends Vendor {
    public PerGuestVendor(String[] p) { super(p); }
    @Override public double price(int guests) { return getBasePrice() * guests; }
    @Override public String getPricing() { return "Per guest"; }
}
