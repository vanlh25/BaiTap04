package vn.iotstar.controller.admin;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/dashboard")
public class DashboardController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra session có account không
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            // Nếu chưa đăng nhập, chuyển về login
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        // Forward tới dashboard.jsp
        RequestDispatcher rd = req.getRequestDispatcher("/views/dashboard.jsp");
        rd.forward(req, resp);
    }
}
