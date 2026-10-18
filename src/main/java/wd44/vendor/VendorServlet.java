package wd44.vendor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

/** Controller for the Vendors pages: create, read, update and delete. */
@WebServlet("/vendors")
public class VendorServlet extends HttpServlet {
    private final VendorFileHandler files = new VendorFileHandler();
    private static final String VIEWS = "/WEB-INF/views/vendor/";

    @Override protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        String action = rq.getParameter("action");
        if (action == null) action = "list";
        String id = rq.getParameter("id");
        String base = rq.getContextPath() + "/vendors";

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
            rs.sendRedirect(rq.getContextPath() + "/vendors");
            return;
        }
        boolean isNew = (id == null || id.isEmpty());
        String[] p = new String[5];
        if (isNew) p[0] = files.newId(); else p[0] = id;
        for (int i = 1; i < p.length; i++) p[i] = clean(rq.getParameter("f" + i));
        Vendor item = Vendor.fromLine(String.join("|", p));
        if (isNew) files.add(item); else files.update(item);
        rs.sendRedirect(rq.getContextPath() + "/vendors");
    }

    private static String clean(String s) {
        if (s == null) return "";
        return s.replace("|", "/").replace("\r", " ").replace("\n", " ").trim();
    }
}
