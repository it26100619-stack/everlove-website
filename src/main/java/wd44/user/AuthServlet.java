package wd44.user;

import wd44.admin.Admin;
import wd44.admin.AdminFileHandler;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

/** Login, logout and couple self-registration. */
@WebServlet({"/login", "/logout", "/register"})
public class AuthServlet extends HttpServlet {
    private final UserFileHandler users = new UserFileHandler();
    private final AdminFileHandler admins = new AdminFileHandler();

    @Override protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        String path = rq.getServletPath();
        if (path.equals("/logout")) {
            rq.getSession().invalidate();
            rs.sendRedirect(rq.getContextPath() + "/site");
        } else {
            rq.getRequestDispatcher("/WEB-INF/views/user/" + (path.equals("/register") ? "register" : "login") + ".jsp").forward(rq, rs);
        }
    }

    @Override protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        rq.setCharacterEncoding("UTF-8");
        String email = clean(rq.getParameter("email")), pw = clean(rq.getParameter("password"));
        if (rq.getServletPath().equals("/register")) {
            String error = "";
            if (pw.length() < 6) error = "Your password must have at least 6 characters.";
            else if (emailTaken(email)) error = "That email is already registered. Please log in instead.";
            if (!error.isEmpty()) {
                rq.setAttribute("error", error);
                rq.getRequestDispatcher("/WEB-INF/views/user/register.jsp").forward(rq, rs);
                return;
            }
            users.add(new Couple(users.newId(), clean(rq.getParameter("name")), email, PasswordUtil.hash(pw), clean(rq.getParameter("phone"))));
            rs.sendRedirect(rq.getContextPath() + "/login?registered=1");
            return;
        }
        List<User> all = new ArrayList<User>(admins.readAll());
        all.addAll(users.readAll());
        for (User u : all) {
            if (u.getEmail().equalsIgnoreCase(email) && u.checkPassword(pw)) {
                rq.getSession().setAttribute("user", u);
                if (u.isAdmin()) rs.sendRedirect(rq.getContextPath() + "/home");
                else rs.sendRedirect(rq.getContextPath() + "/site");
                return;
            }
        }
        rq.setAttribute("error", "Wrong email or password");
        rq.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(rq, rs);
    }

    private boolean emailTaken(String email) {
        for (User u : users.readAll()) if (u.getEmail().equalsIgnoreCase(email)) return true;
        for (User u : admins.readAll()) if (u.getEmail().equalsIgnoreCase(email)) return true;
        return false;
    }

    private static String clean(String s) { return s == null ? "" : s.replace("|", "/").trim(); }
}
