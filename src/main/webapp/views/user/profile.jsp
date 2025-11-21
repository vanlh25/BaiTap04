<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="col-md-9 col-sm-9">
    <h2>Profile của bạn</h2>
    <div class="content-form-page">
        <div class="row">
            <div class="col-md-7 col-sm-7">
                <c:if test="${not empty message}">
                    <div class="alert alert-success">${message}</div>
                </c:if>

                <form action="<c:url value='/admin/user/profile'/>" method="post" enctype="multipart/form-data">
                    
                    <div class="form-group">
                        <label for="fullName">Họ và tên:</label>
                        <input type="text" class="form-control" id="fullName" name="fullName" 
                               value="${user.fullName}" required>
                    </div>

                    <div class="form-group">
                        <label for="phone">Số điện thoại:</label>
                        <input type="text" class="form-control" id="phone" name="phone" 
                               value="${user.phone}" required>
                    </div>

                    <div class="form-group">
                        <label>Ảnh hiện tại:</label>
                        <c:choose>
                            <c:when test="${not empty user.images}">
                                <img id="preview" src="<c:url value='/image?fname=${user.images}'/>" 
                                     alt="Avatar" style="width:80px; height:80px; object-fit:cover; border-radius:4px; display:block; margin-top:10px;">
                            </c:when>
                            <c:otherwise>
                                <img id="preview" src="<c:url value='/image?fname=default.png'/>" 
                                     alt="Avatar" style="width:80px; height:80px; object-fit:cover; border-radius:4px; display:block; margin-top:10px;">
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="form-group">
                        <label for="images">Thay đổi ảnh:</label>
                        <input type="file" class="form-control-file" id="images" name="images" accept="image/*" onchange="previewNewImage(event)">
                        <input type="hidden" name="oldImage" value="${user.images}">
                    </div>

                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">Hủy</a>

                </form>
            </div>
        </div>
    </div>
</div>

<script>
function previewNewImage(event) {
    const preview = document.getElementById('preview');
    const file = event.target.files[0];
    if(file) {
        preview.src = URL.createObjectURL(file);
    }
}
</script>
