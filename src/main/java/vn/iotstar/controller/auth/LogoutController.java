package vn.iotstar.controller.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

@WebServlet(urlPatterns= "/auth/logout")
public class LogoutController extends HttpServlet {
	
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        // Xoá session
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Xoá cookie 
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userName".equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
        }

        // Chuyển hướng về trang login
        resp.sendRedirect(req.getContextPath() + "/auth/login");
    }
}
