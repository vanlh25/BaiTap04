package vn.iotstar.controller.admin.category;

import vn.iotstar.util.Constant;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import vn.iotstar.model.CategoryModel;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.Impl.CategoryServiceImpl;

@WebServlet("/admin/category/edit")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1MB
        maxFileSize = 10 * 1024 * 1024,       // 10MB
        maxRequestSize = 50 * 1024 * 1024     // 50MB
)
public class CategoryEditController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        CategoryModel category = categoryService.findById(id);

        req.setAttribute("category", category);
        RequestDispatcher rd = req.getRequestDispatcher("/views/category/edit-category.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        int id = Integer.parseInt(req.getParameter("categoryId"));
        String categoryName = req.getParameter("categoryName");
        String categoryCode = req.getParameter("categoryCode");
        int status = Integer.parseInt(req.getParameter("status"));
        String userName = req.getParameter("userName");
        String oldImage = req.getParameter("oldImage"); // ẩn trong form

        // Lấy file mới nếu có
        Part part = req.getPart("images");
        String finalFileName = oldImage;

        if (part != null && part.getSize() > 0) {
            String submittedFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            int dot = submittedFileName.lastIndexOf(".");
            String ext = (dot != -1) ? submittedFileName.substring(dot + 1) : "";
            finalFileName = System.currentTimeMillis() + (ext.isEmpty() ? "" : "." + ext);

            File uploadDir = new File(Constant.DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            part.write(Constant.DIR + "/" + finalFileName);
        }

        // Gán dữ liệu vào model
        CategoryModel category = new CategoryModel();
        category.setCategoryId(id);
        category.setCategoryName(categoryName);
        category.setCategoryCode(categoryCode);
        category.setStatus(status);
        category.setUserName(userName);
        category.setImages(finalFileName);

        // Cập nhật vào DB
        categoryService.update(category);

        // Chuyển hướng về danh sách
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}
