package vn.iotstar.controller.admin.video;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.model.VideoModel;
import vn.iotstar.service.IVideoService;
import vn.iotstar.service.Impl.VideoServiceImpl;

@WebServlet("/admin/video/list")
public class VideoListController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IVideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String search = req.getParameter("search");
        List<VideoModel> videos;

        if (search != null && !search.trim().isEmpty()) {
            // Tìm kiếm theo title
            videos = videoService.search(search.trim());
            req.setAttribute("search", search.trim());
        } else {
            // Hiển thị tất cả
            videos = videoService.findAll();
            req.setAttribute("search", ""); 
        }

        req.setAttribute("videoList", videos);

        RequestDispatcher rd = req.getRequestDispatcher("/views/video/list-video.jsp");
        rd.forward(req, resp);
    }
}
