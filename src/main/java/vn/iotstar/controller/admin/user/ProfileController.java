package vn.iotstar.controller.admin.user;

import vn.iotstar.util.Constant;
import vn.iotstar.model.UserModel;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.Impl.UserServiceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/admin/user/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 50 * 1024 * 1024
)
public class ProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserModel user = (UserModel) session.getAttribute("account");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        req.setAttribute("user", user);
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        UserModel user = (UserModel) session.getAttribute("account");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");
        String oldImage = req.getParameter("oldImage");

        user.setFullName(fullName);
        user.setPhone(phone);

        //UPLOAD ẢNH
        Part part = req.getPart("images");
        String finalFileName = oldImage;

        if (part != null && part.getSize() > 0) {

            String submittedFileName = Paths.get(part.getSubmittedFileName())
                    .getFileName().toString();

            int dot = submittedFileName.lastIndexOf(".");
            String ext = (dot != -1) ? submittedFileName.substring(dot) : "";

            finalFileName = System.currentTimeMillis() + ext;

            File uploadDir = new File(Constant.DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            part.write(Constant.DIR + "/" + finalFileName);
        }

        user.setImages(finalFileName);

        // UPDATE DATABASE
        userService.update(user);

        // UPDATE SESSION
        session.setAttribute("account", user);

        resp.sendRedirect(req.getContextPath() + "/admin/dashboard");

    }
}
