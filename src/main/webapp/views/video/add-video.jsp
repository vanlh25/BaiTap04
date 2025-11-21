<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- BEGIN CONTENT -->
<div class="col-md-9 col-sm-9">
    <h2>Thêm Video Mới</h2>
    <div class="content-form-page">
        <div class="row">
            <div class="col-md-7 col-sm-7">
                <form action="${pageContext.request.contextPath}/admin/video/add" 
                      method="post" enctype="multipart/form-data">

                    <div class="form-group">
                        <label for="title">Tiêu đề:</label>
                        <input type="text" class="form-control" id="title" name="title" required>
                    </div>

                    <div class="form-group">
                        <label for="description">Mô tả:</label>
                        <textarea class="form-control" id="description" name="description" rows="5" required></textarea>
                    </div>

                    <div class="form-group">
                        <label for="categoryId">ID Danh mục:</label>
                        <input type="number" class="form-control" id="categoryId" name="categoryId" required>
                    </div>

                    <div class="form-group">
                        <label for="active">Trạng thái:</label>
                        <select class="form-control" id="active" name="active">
                            <option value="1">Hoạt động</option>
                            <option value="0">Khóa</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="poster">Poster:</label>
                        <input type="file" class="form-control-file" id="poster" name="poster" 
                               accept="image/*" onchange="previewPoster(event)">
                        <img id="preview" src="#" alt="Preview Poster" 
                             style="display:none;width:120px;height:80px;object-fit:cover;border-radius:4px;margin-top:10px;">
                    </div>

                    <button type="submit" class="btn btn-success">Thêm Video</button>
                    <a href="${pageContext.request.contextPath}/admin/video/list" class="btn btn-secondary">Hủy</a>

                </form>
            </div>
        </div>
    </div>
</div>

<script>
function previewPoster(event) {
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
<!-- END CONTENT -->
