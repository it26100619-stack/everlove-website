package wd44.booking;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
import wd44.vendor.Vendor;
import wd44.vendor.VendorFileHandler;

/** Controller for the Bookings pages: create, read, update and delete. */
@WebServlet("/bookings")
public class BookingServlet extends HttpServlet {
    private final BookingFileHandler files = new BookingFileHandler();
    private static final String VIEWS = "/WEB-INF/views/booking/";

    @Override protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        String action = rq.getParameter("action");
        if (action == null) action = "list";
        String id = rq.getParameter("id");
        String base = rq.getContextPath() + "/bookings";

        if (action.equals("new")) {                       // CREATE form
            rq.getRequestDispatcher(VIEWS + "add.jsp").forward(rq, rs);
            return;
        }
        if (action.equals("edit")) {                      // UPDATE form
            rq.setAttribute("item", files.findById(id));
            rq.getRequestDispatcher(VIEWS + "edit.jsp").forward(rq, rs);
            return;
        }

        // READ and search
        String q = rq.getParameter("q");
        if (q == null) q = "";
        q = q.trim();
        rq.setAttribute("items", files.search(q));
        rq.setAttribute("q", q);
        rq.getRequestDispatcher(VIEWS + "list.jsp").forward(rq, rs);
    }

    @Override protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
        rq.setCharacterEncoding("UTF-8");
        String id = rq.getParameter("id");
        if ("delete".equals(rq.getParameter("action"))) {            // DELETE (POST only)
            files.delete(id);
            rs.sendRedirect(rq.getContextPath() + "/bookings");
            return;
        }
        if ("status".equals(rq.getParameter("action"))) {            // confirm or cancel (POST only)
            Booking b = files.findById(id);
            String value = rq.getParameter("value");
            if (b != null && value != null && (value.equals("Confirmed") || value.equals("Cancelled") || value.equals("Pending"))) files.update(b.withStatus(value));
            String back = "/bookings";
            if ("home".equals(rq.getParameter("back"))) back = "/home";
            rs.sendRedirect(rq.getContextPath() + back);
            return;
        }
        boolean isNew = (id == null || id.isEmpty());
        String[] p = new String[7];
        if (isNew) p[0] = files.newId(); else p[0] = id;
        for (int i = 1; i < p.length; i++) p[i] = clean(rq.getParameter("f" + i));
        Vendor v = new VendorFileHandler().findById(p[2]);                 // polymorphism: price() depends on the vendor type
        double total = 0;
        if (v != null) total = v.price(Integer.parseInt(p[4]));
        p[5] = String.format("%.2f", total);
        if (p[6].isEmpty()) p[6] = "Pending";
        Booking item = Booking.fromLine(String.join("|", p));
        if (isNew) files.add(item); else files.update(item);
        rs.sendRedirect(rq.getContextPath() + "/bookings");
    }

    private static String clean(String s) {
        if (s == null) return "";
        return s.replace("|", "/").replace("\r", " ").replace("\n", " ").trim();
    }
}
