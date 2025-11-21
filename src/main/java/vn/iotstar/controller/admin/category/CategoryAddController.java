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

@WebServlet("/admin/category/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1MB
        maxFileSize = 10 * 1024 * 1024,       // 10MB
        maxRequestSize = 50 * 1024 * 1024     // 50MB
)
public class CategoryAddController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/views/category/add-category.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        String name = req.getParameter("categoryName");
        String code = req.getParameter("categoryCode");
        int status = Integer.parseInt(req.getParameter("status"));
        String userName = req.getParameter("userName");

        // Tạo model
        CategoryModel category = new CategoryModel();
        category.setCategoryName(name);
        category.setCategoryCode(code);
        category.setStatus(status);
        category.setUserName(userName);

        // Upload file
        String fileNameSaved = "";
        File uploadDir = new File(Constant.DIR);
        if (!uploadDir.exists()) uploadDir.mkdir();

        try {
            Part part = req.getPart("images");
            if (part != null && part.getSize() > 0) {
                String submittedFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

                // Lấy extension file
                int dotIndex = submittedFileName.lastIndexOf(".");
                String ext = (dotIndex != -1) ? submittedFileName.substring(dotIndex + 1) : "";

                // Tạo tên file duy nhất
                fileNameSaved = System.currentTimeMillis() + (ext.isEmpty() ? "" : "." + ext);

                // Upload file
                part.write(Constant.DIR + "/" + fileNameSaved);

                // Ghi vào model
                category.setImages(fileNameSaved);

            } else {
                category.setImages("default.png"); // file mặc định nếu không upload
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Lưu vào DB
        categoryService.insert(category);

        // Chuyển hướng về list
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}
