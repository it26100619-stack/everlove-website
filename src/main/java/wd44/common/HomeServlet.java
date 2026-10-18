package wd44.common;

import wd44.admin.AdminFileHandler;
import wd44.booking.Booking;
import wd44.booking.BookingFileHandler;
import wd44.plan.PlanFileHandler;
import wd44.review.ReviewFileHandler;
import wd44.user.UserFileHandler;
import wd44.vendor.Vendor;
import wd44.vendor.VendorFileHandler;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

/** Admin dashboard: live counts read from every data file. */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        rq.setAttribute("nUsers", new UserFileHandler().readAll().size());
        rq.setAttribute("nVendors", new VendorFileHandler().readAll().size());
        rq.setAttribute("nBookings", new BookingFileHandler().readAll().size());
        rq.setAttribute("nPlans", new PlanFileHandler().readAll().size());
        rq.setAttribute("nAdmins", new AdminFileHandler().readAll().size());
        rq.setAttribute("nReviews", new ReviewFileHandler().readAll().size());
        double sum = 0;
        for (Booking b : new BookingFileHandler().readAll()) sum += b.getTotal();
        rq.setAttribute("revenue", String.format("%,.2f", sum));
        List<String[]> pending = new ArrayList<>();                 // bookings waiting for an admin
        VendorFileHandler vendors = new VendorFileHandler();
        for (Booking b : new BookingFileHandler().readAll()) {
            if (!b.getStatus().equals("Pending")) continue;
            Vendor v = vendors.findById(b.getVendorId());
            String name = "(removed vendor)";
            if (v != null) name = v.getName();
            pending.add(new String[]{b.getId(), name, b.getDate(), String.valueOf(b.getGuests()), String.format("%,.2f", b.getTotal())});
        }
        rq.setAttribute("pending", pending);
        rq.getRequestDispatcher("/WEB-INF/views/common/home.jsp").forward(rq, rs);
    }
}
