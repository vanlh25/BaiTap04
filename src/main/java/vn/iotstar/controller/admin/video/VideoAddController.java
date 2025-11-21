package vn.iotstar.controller.admin.video;

import vn.iotstar.util.Constant;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import vn.iotstar.model.VideoModel;
import vn.iotstar.service.IVideoService;
import vn.iotstar.service.Impl.VideoServiceImpl;

@WebServlet("/admin/video/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 50 * 1024 * 1024
)
public class VideoAddController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IVideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/views/video/add-video.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        int active = Integer.parseInt(req.getParameter("active")); // 0 hoặc 1

        VideoModel video = new VideoModel();
        video.setTitle(title);
        video.setDescription(description);
        video.setCategoryId(categoryId);
        video.setActive(active);
        video.setViews(0);

        // Upload poster
        Part part = req.getPart("poster");
        String posterFileName = "default.png";

        if (part != null && part.getSize() > 0) {
            String submittedFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            int dot = submittedFileName.lastIndexOf(".");
            String ext = (dot != -1) ? submittedFileName.substring(dot + 1) : "";

            posterFileName = System.currentTimeMillis() + (ext.isEmpty() ? "" : "." + ext);

            File uploadDir = new File(Constant.DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            part.write(Constant.DIR + "/" + posterFileName);
        }

        video.setPoster(posterFileName);
        videoService.insert(video);

        resp.sendRedirect(req.getContextPath() + "/admin/video/list");
    }
}
