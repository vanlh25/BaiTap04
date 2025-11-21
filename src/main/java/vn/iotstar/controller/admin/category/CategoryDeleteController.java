package vn.iotstar.controller.admin.category;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.Impl.CategoryServiceImpl;

@WebServlet("/admin/category/delete")
public class CategoryDeleteController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ICategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                cateService.delete(id);  // Xóa category theo id
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Có thể thêm thông báo lỗi nếu id không hợp lệ
            }
        }

        // Chuyển hướng về danh sách category sau khi xóa
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}
