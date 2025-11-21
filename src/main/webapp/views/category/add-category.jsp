<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-9 col-sm-9">
    <h2>Thêm danh mục mới</h2>
    <div class="content-form-page">
        <div class="row">
            <div class="col-md-7 col-sm-7">
                <form action="<c:url value='/admin/category/add'/>" method="post" enctype="multipart/form-data">
                    
                    <div class="form-group">
                        <label for="categoryName">Tên danh mục:</label>
                        <input type="text" class="form-control" name="categoryName" id="categoryName" required>
                    </div>

                    <div class="form-group">
                        <label for="categoryCode">Mã danh mục:</label>
                        <input type="text" class="form-control" name="categoryCode" id="categoryCode" required>
                    </div>

                    <div class="form-group">
                        <label for="images">Ảnh danh mục:</label>
                        <input type="file" class="form-control-file" name="images" id="images" accept="image/*" onchange="previewImage(event)">
                        <img id="preview" src="#" alt="Preview" 
                             style="display:none; margin-top:10px; width:80px; height:80px; object-fit:cover; border-radius:4px;">
                    </div>

                    <div class="form-group">
                        <label for="status">Trạng thái:</label>
                        <select name="status" class="form-control" id="status">
                            <option value="1">Hoạt động</option>
                            <option value="0">Khóa</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="userName">Người quản lý:</label>
                        <input type="text" class="form-control" name="userName" id="userName" placeholder="Tên user" required>
                    </div>

                    <button type="submit" class="btn btn-primary">Thêm mới</button>
                    <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-secondary">Hủy</a>

                </form>
            </div>
        </div>
    </div>
</div>

<script>
function previewImage(event) {
    const preview = document.getElementById('preview');
    const file = event.target.files[0];
    if(file) {
        preview.src = URL.createObjectURL(file);
        preview.style.display = 'block';
    } else {
        preview.src = '#';
        preview.style.display = 'none';
    }
}
</script>
