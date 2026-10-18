package wd44.store;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

/** Admin only: saves a photo for a vendor into the data/photos folder. */
@WebServlet("/upload-photo")
@MultipartConfig(maxFileSize = 5242880)
public class UploadPhotoServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException {
        try {
            String id = new String(rq.getPart("id").getInputStream().readAllBytes()).trim();
            Part photo = rq.getPart("photo");
            byte[] data;
            try (InputStream in = photo.getInputStream()) { data = in.readAllBytes(); }
            if (PhotoServlet.imageType(data) == null) {
                rs.sendError(400, "Please upload a JPG, PNG or WEBP image.");
                return;
            }
            Path file = PhotoServlet.photoFile(id);
            Files.createDirectories(file.getParent());
            Files.write(file, data);
            rs.sendRedirect(rq.getContextPath() + "/vendors?action=edit&id=" + id.replaceAll("[^A-Za-z0-9]", ""));
        } catch (IllegalStateException | javax.servlet.ServletException e) {
            rs.sendError(400, "The photo must be under 5 MB.");
        }
    }
}
