package vn.iotstar.controller.admin.video;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.service.IVideoService;
import vn.iotstar.service.Impl.VideoServiceImpl;

@WebServlet("/admin/video/delete")
public class VideoDeleteController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IVideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                videoService.delete(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin/video/list");
    }
}
