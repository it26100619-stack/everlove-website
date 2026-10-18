package wd44.vendor;

public class FlatRateVendor extends Vendor {
    public FlatRateVendor(String[] p) { super(p); }
    @Override public double price(int guests) { return getBasePrice(); }
    @Override public String getPricing() { return "Flat rate"; }
}
