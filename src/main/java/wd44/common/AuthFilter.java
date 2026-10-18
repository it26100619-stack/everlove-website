package wd44.common;

import wd44.user.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

/** Public pages are open, booking pages need a login, and the admin panel is for admins only. */
@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final String[] ADMIN = {"/home", "/vendors", "/bookings", "/plans", "/reviews", "/users", "/admins", "/upload-photo"};

    @Override public void init(FilterConfig config) { }
    @Override public void destroy() { }

    @Override public void doFilter(ServletRequest a, ServletResponse b, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) a;
        HttpServletResponse rs = (HttpServletResponse) b;
        rs.setHeader("X-Frame-Options", "SAMEORIGIN");
        rs.setHeader("X-Content-Type-Options", "nosniff");
        rs.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        String p = rq.getServletPath();
        boolean open = p.equals("/") || p.equals("/index.jsp") || p.equals("/site") || p.equals("/catalog") || p.equals("/photo") || p.equals("/vendor")
                || p.equals("/login") || p.equals("/register")
                || p.startsWith("/css") || p.startsWith("/images") || p.startsWith("/js");
        if (open) { chain.doFilter(a, b); return; }
        HttpSession s = rq.getSession(false);
        User u = null;
        if (s != null) u = (User) s.getAttribute("user");
        if (u == null) { rs.sendRedirect(rq.getContextPath() + "/login"); return; }
        for (String path : ADMIN) {
            if (p.equals(path) && !u.isAdmin()) { rs.sendRedirect(rq.getContextPath() + "/site"); return; }
        }
        chain.doFilter(a, b);
    }
}
