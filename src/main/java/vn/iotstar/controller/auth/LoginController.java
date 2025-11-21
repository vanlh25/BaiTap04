package vn.iotstar.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.model.UserModel;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.Impl.UserServiceImpl;

@WebServlet(urlPatterns ="/auth/login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static final String COOKIE_REMEMBER = "userName";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            // Nếu đã login, chuyển tới dashboard
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            return;
        }

        // Kiểm tra cookie remember me
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_REMEMBER.equals(cookie.getName())) {
                    if (session == null || session.getAttribute("account") == null) {
                        session = req.getSession(true);
                        // Lấy username từ cookie
                        String username = cookie.getValue();
                        IUserService userService = new UserServiceImpl();
                        UserModel user = userService.findById(username);
                        if (user != null) {
                            session.setAttribute("account", user);
                            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                            return;
                        }
                    }
                }
            }
        }

        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        boolean isRememberMe = "on".equals(req.getParameter("remember"));

        String alertMsg = "";

        if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
            alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        IUserService userService = new UserServiceImpl();
        UserModel user = userService.login(userName, password);
        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("account", user);

            if (isRememberMe) {
                saveRememberMe(resp, userName);
            }

            boolean isAdmin = userService.checkRoleAdmin(userName);
            if (isAdmin) {
                // Chuyển tới Dashboard thay vì danh sách category
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                alertMsg = "Bạn không có quyền truy cập!";
                req.setAttribute("alert", alertMsg);
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }
        } else {
            alertMsg = "Tài khoản hoặc mật khẩu không đúng";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

    private void saveRememberMe(HttpServletResponse response, String userName) {
        Cookie cookie = new Cookie(COOKIE_REMEMBER, userName);
        cookie.setMaxAge(30 * 60); // 30 phút
        response.addCookie(cookie);
    }
}
