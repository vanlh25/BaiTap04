package vn.iotstar.controller.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.Impl.UserServiceImpl;

@WebServlet(urlPatterns = "/auth/register")
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static final String COOKIE_REMEMBER = "userName";
    public static final String REGISTER = "/views/register.jsp";

    private IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Kiểm tra session
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            return;
        }

        // Kiểm tra cookie
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_REMEMBER.equals(cookie.getName())) {
                    session = req.getSession(true);
                    resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                    return;
                }
            }
        }

        req.getRequestDispatcher(REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String fullName = req.getParameter("fullName"); 
        String phone = req.getParameter("phone");

        String alertMsg = "";

        // Kiểm tra trùng lặp email, username, phone
        if ( userService.checkExistEmail(email)) {
            alertMsg = "Email đã tồn tại!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(REGISTER).forward(req, resp);
            return;
        }

        if ( userService.checkExistUsername(userName)) {
            alertMsg = "Tài khoản đã tồn tại!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(REGISTER).forward(req, resp);
            return;
        }

        if ( userService.checkExistPhone(phone)) {
            alertMsg = "Số điện thoại đã tồn tại!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(REGISTER).forward(req, resp);
            return;
        }

        // Thực hiện đăng ký
        boolean isSuccess =  userService.register(email, userName, fullName, password, phone);
        if (isSuccess) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        } else {
            alertMsg = "System error!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(REGISTER).forward(req, resp);
        }
    }
}
