package vn.iotstar.controller.admin.category;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.model.CategoryModel;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.Impl.CategoryServiceImpl;

@WebServlet(urlPatterns = { "/admin/category/list" })
public class CategoryListController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ICategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        // Lấy danh sách category từ service
        List<CategoryModel> cateList = cateService.findAll();
        System.out.print(cateList);
        
        // Gắn danh sách vào request attribute để JSP sử dụng
        req.setAttribute("cateList", cateList);

        // Chuyển tiếp đến trang listcategory.jsp
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/category/list-category.jsp");
        dispatcher.forward(req, resp);
    }
}
