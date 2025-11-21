package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//annotation
@WebServlet("/home")  // URL /home sẽ được xử lý bởi servlet này
public class HomeController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException{
		// Chuyển tiếp tới trang home.jsp
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
	}
}
