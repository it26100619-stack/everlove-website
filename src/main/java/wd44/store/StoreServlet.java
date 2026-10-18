package wd44.store;

import wd44.booking.Booking;
import wd44.booking.BookingFileHandler;
import wd44.plan.PlanFileHandler;
import wd44.plan.WeddingPlan;
import wd44.review.Review;
import wd44.review.ReviewFileHandler;
import wd44.user.Couple;
import wd44.user.User;
import wd44.user.UserFileHandler;
import wd44.vendor.Vendor;
import wd44.vendor.VendorFileHandler;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/** The customer website: home, vendor catalog, vendor page, booking, my bookings and my wedding. */
@WebServlet({"/site", "/catalog", "/vendor", "/book", "/my-bookings", "/my-wedding"})
public class StoreServlet extends HttpServlet {
    private final VendorFileHandler vendors = new VendorFileHandler();
    private final BookingFileHandler bookings = new BookingFileHandler();
    private final ReviewFileHandler reviews = new ReviewFileHandler();
    private final UserFileHandler users = new UserFileHandler();
    private final PlanFileHandler plans = new PlanFileHandler();

    @Override protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        String path = rq.getServletPath();
        addRatings(rq);

        if (path.equals("/catalog")) {
            String category = param(rq, "category"), q = param(rq, "q");
            List<Vendor> list = new ArrayList<>();
            for (Vendor v : vendors.search(q)) {
                if (category.isEmpty() || v.getCategory().equalsIgnoreCase(category)) list.add(v);
            }
            rq.setAttribute("items", list);
            rq.setAttribute("category", category);
            rq.setAttribute("q", q);
            show(rq, rs, "catalog");
            return;
        }
        if (path.equals("/vendor")) {
            Vendor v = vendors.findById(param(rq, "id"));
            if (v == null) { rs.sendRedirect(rq.getContextPath() + "/catalog"); return; }
            List<String[]> list = new ArrayList<>();
            for (Review r : reviews.readAll()) {
                if (!r.getVendorId().equals(v.getId())) continue;
                Couple c = users.findById(r.getCoupleId());
                String who = "Happy couple";
                if (c != null) who = c.getName();
                list.add(new String[]{"\u2605\u2605\u2605\u2605\u2605".substring(0, r.getRating()), r.getComment(), who});
            }
            rq.setAttribute("vendor", v);
            rq.setAttribute("about", blurb(v.getCategory()));
            rq.setAttribute("reviewList", list);
            show(rq, rs, "vendor");
            return;
        }
        if (path.equals("/book")) {
            Vendor v = vendors.findById(param(rq, "vendor"));
            if (v == null) { rs.sendRedirect(rq.getContextPath() + "/catalog"); return; }
            rq.setAttribute("vendor", v);
            show(rq, rs, "book");
            return;
        }
        if (path.equals("/my-bookings")) {
            User u = (User) rq.getSession().getAttribute("user");
            List<String[]> rows = new ArrayList<>();
            for (Booking b : bookings.readAll()) {
                if (!b.getCoupleId().equals(u.getId())) continue;
                Vendor v = vendors.findById(b.getVendorId());
                String name = "(removed vendor)";
                if (v != null) name = v.getName();
                String reviewed = "no";
                if (findReview(u.getId(), b.getVendorId()) != null) reviewed = "yes";
                rows.add(new String[]{b.getId(), name, b.getDate(), String.valueOf(b.getGuests()), String.format("%,.2f", b.getTotal()), b.getStatus(), b.getVendorId(), reviewed});
            }
            rq.setAttribute("rows", rows);
            show(rq, rs, "my");
            return;
        }
        if (path.equals("/my-wedding")) {
            showWedding(rq, rs, (User) rq.getSession().getAttribute("user"));
            return;
        }
        // home page: featured vendors, numbers and testimonials
        List<Vendor> all = vendors.readAll();
        List<Vendor> featured = new ArrayList<>();
        for (int i = 0; i < all.size() && i < 6; i++) featured.add(all.get(i));
        List<String[]> quotes = new ArrayList<>();
        for (Review r : reviews.readAll()) {
            if (r.getRating() < 4 || quotes.size() >= 3) continue;
            Couple c = users.findById(r.getCoupleId());
            Vendor v = vendors.findById(r.getVendorId());
            String who = "Happy couple", what = "";
            if (c != null) who = c.getName();
            if (v != null) what = v.getName();
            quotes.add(new String[]{r.getComment(), "\u2605\u2605\u2605\u2605\u2605".substring(0, r.getRating()), who, what});
        }
        rq.setAttribute("featured", featured);
        rq.setAttribute("quotes", quotes);
        rq.setAttribute("nVendors", all.size());
        rq.setAttribute("nBookings", bookings.readAll().size());
        show(rq, rs, "home");
    }

    @Override protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        rq.setCharacterEncoding("UTF-8");
        User u = (User) rq.getSession().getAttribute("user");
        String base = rq.getContextPath();
        String path = rq.getServletPath();

        if (path.equals("/my-wedding")) {                              // save my wedding details
            String date = param(rq, "date"), venue = param(rq, "venue").replace("|", "/");
            double budget = -1;
            int guests = 0;
            try { budget = Double.parseDouble(param(rq, "budget")); } catch (NumberFormatException e) { budget = -1; }
            try { guests = Integer.parseInt(param(rq, "guests")); } catch (NumberFormatException e) { guests = 0; }
            if (date.isEmpty() || budget < 0 || guests < 1) {
                rq.setAttribute("error", "Please fill in a date, a budget and the number of guests.");
                showWedding(rq, rs, u);
                return;
            }
            WeddingPlan old = findPlan(u.getId());
            String id = plans.newId();
            if (old != null) id = old.getId();
            WeddingPlan p = new WeddingPlan(id, u.getId(), date, venue, String.valueOf(budget), String.valueOf(guests));
            if (old == null) plans.add(p); else plans.update(p);
            rs.sendRedirect(base + "/my-wedding?saved=1");
            return;
        }
        if (path.equals("/my-bookings")) {
            if ("review".equals(param(rq, "action"))) {                // write or change a review
                String vid = param(rq, "vendor");
                boolean allowed = false;
                for (Booking b : bookings.readAll()) {
                    if (b.getCoupleId().equals(u.getId()) && b.getVendorId().equals(vid) && b.getStatus().equals("Confirmed")) allowed = true;
                }
                if (allowed) {
                    int rating = 5;
                    try { rating = Integer.parseInt(param(rq, "rating")); } catch (NumberFormatException e) { rating = 5; }
                    String comment = param(rq, "comment").replace("|", "/");
                    Review old = findReview(u.getId(), vid);
                    String id = reviews.newId();
                    if (old != null) id = old.getId();
                    Review r = new Review(id, vid, u.getId(), String.valueOf(rating), comment);
                    if (old == null) reviews.add(r); else reviews.update(r);
                }
                rs.sendRedirect(base + "/my-bookings?reviewed=1");
                return;
            }
            Booking b = bookings.findById(param(rq, "id"));            // cancel one of my bookings
            if (b != null && b.getCoupleId().equals(u.getId())) bookings.update(b.withStatus("Cancelled"));
            rs.sendRedirect(base + "/my-bookings");
            return;
        }
        // place a booking request
        Vendor v = vendors.findById(param(rq, "vendor"));
        if (v == null) { rs.sendRedirect(base + "/catalog"); return; }
        String date = param(rq, "date");
        int guests = 0;
        try { guests = Integer.parseInt(param(rq, "guests")); } catch (NumberFormatException e) { guests = 0; }
        String error = "";
        if (date.isEmpty() || date.compareTo(LocalDate.now().toString()) <= 0) error = "Please choose a wedding date in the future.";
        else if (guests < 1) error = "Please enter the number of guests.";
        else {
            for (Booking b : bookings.readAll()) {
                if (b.getVendorId().equals(v.getId()) && b.getDate().equals(date) && !b.getStatus().equals("Cancelled"))
                    error = "Sorry, " + v.getName() + " is already booked on that date. Please pick another date.";
            }
        }
        if (!error.isEmpty()) {
            addRatings(rq);
            rq.setAttribute("vendor", v);
            rq.setAttribute("error", error);
            rq.setAttribute("date", date);
            rq.setAttribute("guests", param(rq, "guests"));
            show(rq, rs, "book");
            return;
        }
        double total = v.price(guests);                          // polymorphism: depends on the vendor type
        String id = bookings.newId();
        bookings.add(new Booking(id, u.getId(), v.getId(), date, String.valueOf(guests), String.format("%.2f", total), "Pending"));
        rs.sendRedirect(base + "/my-bookings?ref=" + id);
    }

    /** My wedding page: details, countdown and budget tracker. */
    private void showWedding(HttpServletRequest rq, HttpServletResponse rs, User u) throws ServletException, IOException {
        WeddingPlan plan = findPlan(u.getId());
        double committed = 0;
        for (Booking b : bookings.readAll()) {
            if (b.getCoupleId().equals(u.getId()) && !b.getStatus().equals("Cancelled")) committed += b.getTotal();
        }
        rq.setAttribute("plan", plan);
        rq.setAttribute("committed", committed);
        if (plan != null) {
            double budget = plan.getBudget();
            int pct = 0;
            if (budget > 0) pct = (int) Math.min(100, committed * 100 / budget);
            rq.setAttribute("remaining", budget - committed);
            rq.setAttribute("pct", pct);
            try { rq.setAttribute("daysLeft", ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(plan.getDate()))); }
            catch (Exception e) { rq.setAttribute("daysLeft", null); }
        }
        show(rq, rs, "wedding");
    }

    private WeddingPlan findPlan(String coupleId) {
        for (WeddingPlan p : plans.readAll()) if (p.getCoupleId().equals(coupleId)) return p;
        return null;
    }

    private Review findReview(String coupleId, String vendorId) {
        for (Review r : reviews.readAll()) if (r.getCoupleId().equals(coupleId) && r.getVendorId().equals(vendorId)) return r;
        return null;
    }

    private static String blurb(String category) {
        if (category.equalsIgnoreCase("Photographer")) return "Professional wedding photography that captures your ceremony, your family and the small moments in between.";
        if (category.equalsIgnoreCase("Caterer")) return "Wedding catering for your guests. The price is per person, so you only pay for the guests who attend.";
        if (category.equalsIgnoreCase("Florist")) return "Bouquets, centrepieces and flowers chosen to match the style and colours of your wedding.";
        if (category.equalsIgnoreCase("Venue")) return "A venue for your ceremony and reception. Check the date, enter your guest count and send a booking request.";
        if (category.equalsIgnoreCase("DJ")) return "Music and sound to keep your guests on the dance floor from the first dance to the last song.";
        return "Stage, table and venue decoration designed around your wedding theme.";
    }

    /** Average rating and number of reviews for each vendor, used on the cards. */
    private void addRatings(HttpServletRequest rq) {
        Map<String, Double> avg = new HashMap<>();
        Map<String, Integer> cnt = new HashMap<>();
        for (Review r : reviews.readAll()) {
            String v = r.getVendorId();
            avg.put(v, avg.getOrDefault(v, 0.0) + r.getRating());
            cnt.put(v, cnt.getOrDefault(v, 0) + 1);
        }
        for (String v : avg.keySet()) avg.put(v, avg.get(v) / cnt.get(v));
        rq.setAttribute("avg", avg);
        rq.setAttribute("cnt", cnt);
    }

    private static String param(HttpServletRequest rq, String name) {
        String v = rq.getParameter(name);
        if (v == null) return "";
        return v.trim();
    }

    private void show(HttpServletRequest rq, HttpServletResponse rs, String page) throws ServletException, IOException {
        rq.getRequestDispatcher("/WEB-INF/views/store/" + page + ".jsp").forward(rq, rs);
    }
}
