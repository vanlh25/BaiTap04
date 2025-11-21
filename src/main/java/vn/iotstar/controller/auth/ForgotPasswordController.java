package vn.iotstar.controller.auth;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.Impl.UserServiceImpl;

@WebServlet("/auth/forgot-password")
public class ForgotPasswordController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		RequestDispatcher rd = req.getRequestDispatcher("/views/forgot-password.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String email = req.getParameter("email");
		String alertMsg = "";

		// kiểm tra email rỗng
		if (email == null || email.trim().isEmpty()) {
			alertMsg = "Email không được để trống.";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		// kiểm tra email tồn tại
		if (!userService.checkExistEmail(email)) {
			alertMsg = "Email không tồn tại trong hệ thống.";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		// nếu email hợp lệ, lưu vào session để qua bước reset password
		HttpSession session = req.getSession(true);
		session.setAttribute("resetEmail", email);

		resp.sendRedirect(req.getContextPath() + "/auth/reset-password");
	}
}
