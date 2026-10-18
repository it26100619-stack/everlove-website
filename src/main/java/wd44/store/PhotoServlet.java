package wd44.store;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.*;

/** Shows a vendor's uploaded photo. If there is none, it shows the category picture instead. */
@WebServlet("/photo")
public class PhotoServlet extends HttpServlet {
    public static Path photoFile(String id) {
        String safe = id == null ? "" : id.replaceAll("[^A-Za-z0-9]", "");
        return Paths.get(System.getProperty("wd44.data", "data"), "photos", safe);
    }

    /** Checks the first bytes of the file so only real images are accepted. */
    public static String imageType(byte[] b) {
        if (b.length > 3 && (b[0] & 0xFF) == 0x89 && b[1] == 'P') return "image/png";
        if (b.length > 2 && (b[0] & 0xFF) == 0xFF && (b[1] & 0xFF) == 0xD8) return "image/jpeg";
        if (b.length > 11 && b[0] == 'R' && b[1] == 'I' && b[2] == 'F' && b[3] == 'F') return "image/webp";
        return null;
    }

    @Override protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
        Path file = photoFile(rq.getParameter("id"));
        if (Files.exists(file)) {
            byte[] data = Files.readAllBytes(file);
            String type = imageType(data);
            if (type != null) {
                rs.setContentType(type);
                rs.setHeader("Cache-Control", "max-age=60");
                rs.getOutputStream().write(data);
                return;
            }
        }
        String cat = rq.getParameter("cat") == null ? "venue" : rq.getParameter("cat").replaceAll("[^a-z]", "");
        rs.sendRedirect(rq.getContextPath() + "/images/cat-" + cat + ".svg");
    }
}
