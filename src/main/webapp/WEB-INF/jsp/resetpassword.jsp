<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="./header.jsp" />
    <title>Reset Password</title>
</head>
<body>
<jsp:include page="./navbar.jsp"></jsp:include>
    <h1>Reset Password</h1>
    <form:form method="post" action="/resetpassword">
        <div>
            <label for="oldPassword">Old Password:</label>
            <input type="password" id="oldPassword" name="oldPassword" required>
        </div>
        <div>
            <label for="newPassword">New Password:</label>
            <input type="password" id="newPassword" name="newPassword" required>
        </div>
        <div>
            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>
        <div>
            <button type="submit">Reset Password</button>
        </div>
    </form:form>
    <jsp:include page="./footer.jsp" />
</body>
</html>
