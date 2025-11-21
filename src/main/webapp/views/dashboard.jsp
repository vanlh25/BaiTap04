<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- BEGIN CONTENT -->
<div class="col-md-9 col-sm-9">
    <h1>Dashboard</h1>
    <p>Welcome, <strong>${sessionScope.account.userName}</strong>!</p>

    <div class="dashboard-buttons" style="margin-top:30px;">
        <button type="button" class="btn btn-primary btn-lg"
                onclick="location.href='${pageContext.request.contextPath}/admin/user/profile'">
            View User (Profile)
        </button>

        <button type="button" class="btn btn-success btn-lg"
                onclick="location.href='${pageContext.request.contextPath}/admin/category/list'">
            View Category
        </button>

        <button type="button" class="btn btn-info btn-lg"
                onclick="location.href='${pageContext.request.contextPath}/admin/video/list'">
            View Video
        </button>
    </div>
</div>
<!-- END CONTENT -->
