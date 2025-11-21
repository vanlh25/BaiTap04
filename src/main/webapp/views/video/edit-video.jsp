<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-9 col-sm-9">
    <h2>Chỉnh sửa Video</h2>
    <div class="content-form-page">
        <div class="row">
            <div class="col-md-7 col-sm-7">
                <form action="${pageContext.request.contextPath}/admin/video/edit" 
                      method="post" enctype="multipart/form-data">
                    
                   
                    <input type="hidden" name="id" value="${video.videoId}" />

                    <div class="form-group">
                        <label for="title">Tiêu đề:</label>
                        <input type="text" class="form-control" id="title" name="title" 
                               value="${video.title}" required>
                    </div>

                    <div class="form-group">
                        <label for="description">Mô tả:</label>
                        <textarea class="form-control" id="description" name="description" 
                                  rows="5" required>${video.description}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="categoryId">ID Danh mục:</label>
                        <input type="number" class="form-control" id="categoryId" name="categoryId" 
                               value="${video.categoryId}" required>
                    </div>

                    <div class="form-group">
                        <label for="active">Trạng thái:</label>
                        <select class="form-control" id="active" name="active">
                            <option value="1" ${video.active == 1 ? 'selected' : ''}>Hoạt động</option>
                            <option value="0" ${video.active == 0 ? 'selected' : ''}>Khóa</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Poster hiện tại:</label><br>
                        <c:choose>
                            <c:when test="${not empty video.poster}">
                                <img id="preview" src="${pageContext.request.contextPath}/image?fname=${video.poster}" 
                                     alt="Poster" style="width:120px;height:80px;object-fit:cover;border-radius:4px;">
                            </c:when>
                            <c:otherwise>
                                <img id="preview" src="#" alt="Poster" 
                                     style="display:none;width:120px;height:80px;object-fit:cover;border-radius:4px;">
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="form-group">
                        <label for="poster">Chọn Poster mới (nếu muốn update):</label>
                        <input type="file" class="form-control-file" id="poster" name="poster" 
                               accept="image/*" onchange="previewNewPoster(event)">
                    </div>

                    <button type="submit" class="btn btn-primary">Cập nhật Video</button>
                    <a href="${pageContext.request.contextPath}/admin/video/list" class="btn btn-secondary">Hủy</a>

                </form>
            </div>
        </div>
    </div>
</div>

<script>
function previewNewPoster(event) {
    const preview = document.getElementById('preview');
    const file = event.target.files[0];
    if(file) {
        preview.src = URL.createObjectURL(file);
        preview.style.display = 'block';
    }
}
</script>
