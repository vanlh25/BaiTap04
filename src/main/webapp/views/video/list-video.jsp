<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- BEGIN CONTENT -->
<div class="col-md-9 col-sm-9">
    <h1>Danh sách Video</h1>
    <div class="content-form-page">
        <div class="row">
            <!-- MAIN LIST VIDEO -->
            <div class="col-md-12 col-sm-12">
                <div style="margin-bottom: 15px;">
                    <a href="${pageContext.request.contextPath}/admin/video/add" class="btn btn-success">Thêm Video</a>
                </div>

                <form method="get" style="margin-bottom:15px;">
                    <div class="input-group mb-3" style="max-width:300px;">
                        <input type="text" class="form-control" name="search" placeholder="Tìm kiếm video..." 
                               value="${search}">
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary" type="submit">Tìm</button>
                        </div>
                    </div>
                </form>

                <c:choose>
                    <c:when test="${not empty videoList}">
                        <table class="table table-bordered table-striped">
                            <thead style="background-color:#f0f0f0;">
                                <tr>
                                    <th>#</th>
                                    <th>Tiêu đề</th>
                                    <th>Poster</th>
                                    <th>Mô tả</th>
                                    <th>Views</th>
                                    <th>Trạng thái</th>
                                    <th>Category</th>
                                    <th>Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="v" items="${videoList}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${v.title}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty v.poster}">
                                                    <img src="${pageContext.request.contextPath}/image?fname=${v.poster}" 
                                                         alt="Poster" style="width:60px;height:40px;object-fit:cover;border-radius:4px;">
                                                </c:when>
                                                <c:otherwise>Chưa có</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty v.description}">${v.description}</c:when>
                                                <c:otherwise>Chưa có</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${v.views}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${v.active == 1}">
                                                    <span class="text-success font-weight-bold">Hoạt động</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-danger font-weight-bold">Khóa</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty v.categoryName}">${v.categoryName}</c:when>
                                                <c:otherwise>Chưa có</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/video/edit?id=${v.videoId}" 
                                               class="btn btn-primary btn-sm">Sửa</a>
                                            <a href="${pageContext.request.contextPath}/admin/video/delete?id=${v.videoId}" 
                                               class="btn btn-danger btn-sm" 
                                               onclick="return confirm('Bạn có chắc muốn xóa ${v.title}?');">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p>Chưa có video nào!</p>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </div>
</div>
<!-- END CONTENT -->
