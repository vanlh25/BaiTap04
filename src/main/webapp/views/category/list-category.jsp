<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- BEGIN CONTENT -->
<div class="col-md-9 col-sm-9">
    <h1>Danh sách danh mục</h1>
    <div class="content-form-page">
        <div class="row">
            <!-- MAIN LIST CATEGORY -->
            <div class="col-md-12 col-sm-12">
                <div style="margin-bottom: 15px;">
                    <a href="${pageContext.request.contextPath}/admin/category/add" class="btn btn-success">Thêm danh mục</a>
                </div>

                <table class="table table-bordered table-striped">
                    <thead style="background-color:#f0f0f0;">
                        <tr>
                            <th>#</th>
                            <th>Tên danh mục</th>
                            <th>Mã danh mục</th>
                            <th>Hình ảnh</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cat" items="${cateList}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${cat.categoryName}</td>
                                <td>${cat.categoryCode}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty cat.images}">
                                            <img src="${pageContext.request.contextPath}/image?fname=${cat.images}" 
                                                 alt="${cat.categoryName}" 
                                                 style="width:40px;height:40px;object-fit:cover;border-radius:4px;">
                                        </c:when>
                                        <c:otherwise>Không có hình</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${cat.status == 1}">
                                            <span class="text-success font-weight-bold">Hoạt động</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-danger font-weight-bold">Khóa</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/category/edit?id=${cat.categoryId}" 
                                       class="btn btn-primary btn-sm">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/admin/category/delete?id=${cat.categoryId}" 
                                       class="btn btn-danger btn-sm" 
                                       onclick="return confirm('Bạn có chắc muốn xóa ${cat.categoryName}?');">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty cateList}">
                            <tr>
                                <td colspan="6" style="text-align:center;">Chưa có danh mục nào!</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- END CONTENT -->
