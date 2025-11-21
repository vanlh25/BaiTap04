package vn.iotstar.controller.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.service.Impl.UserServiceImpl;

@WebServlet("/auth/reset-password")
public class ResetPasswordController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    // Service xử lý nghiệp vụ liên quan đến user
    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Lấy session hiện tại nhưng không tạo mới
        HttpSession session = req.getSession(false);

        // Nếu không có email đặt lại mật khẩu thì quay lại trang quên mật khẩu
        if (session == null || session.getAttribute("resetEmail") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/forgot-password");
            return;
        }

        // Hiển thị trang reset password
        req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // Kiểm tra session hợp lệ
        if (session == null || session.getAttribute("resetEmail") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String email = (String) session.getAttribute("resetEmail");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String alertMsg = "";

        // Kiểm tra mật khẩu hợp lệ
        if (!password.equals(confirmPassword)) {
            alertMsg = "Password do not match!";
        } else if (password.length() < 8) {
            alertMsg = "Password must be at least 8 characters!";
        } else {
            // Cập nhật mật khẩu trong database
            boolean success = userService.editPassword(email, password);

            if (success) {
                alertMsg = "Reset password successfully!";
                session.removeAttribute("resetEmail"); // Xóa session reset email
                req.setAttribute("alert", alertMsg);
                
                // Trả về trang login sau khi thành công
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
                return;
            } else {
                alertMsg = "System error";
            }
        }

        // Nếu thất bại thì quay lại trang reset password
        req.setAttribute("alert", alertMsg);
        req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
    }
}
