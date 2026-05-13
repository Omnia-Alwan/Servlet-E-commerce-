<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <style>
        body { font-family: Arial; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f4f4f4; }
        .container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); width: 300px; }
        input { width: 100%; padding: 8px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .error { color: red; font-size: 12px; margin-top: -8px; margin-bottom: 10px; }
        .success { color: green; font-size: 12px; text-align: center; margin-top: 10px; }
        .message { padding: 10px; border-radius: 4px; margin-bottom: 10px; }
        .error-message { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .success-message { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
    </style>
    <script>
        function validatePasswords() {
            var password = document.getElementById("password").value;
            var confirmedPassword = document.getElementById("confirmedPassword").value;
            var errorSpan = document.getElementById("passwordError");
            
            if (password !== confirmedPassword) {
                errorSpan.innerHTML = "Passwords do not match";
                return false;
            }
            errorSpan.innerHTML = "";
            return true;
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Create Account</h2>

    <c:if test="${not empty param.error}">
        <div class="message error-message">
            <c:choose>
                <c:when test="${param.error == 'empty_fields'}">All fields are required</c:when>
                <c:when test="${param.error == 'password_mismatch'}">Passwords do not match</c:when>
                <c:when test="${param.error == 'email_exists'}">Email already registered</c:when>
                <c:when test="${param.error == 'invalid_email'}">Invalid email address</c:when>
                <c:otherwise>Registration failed. Please try again.</c:otherwise>
            </c:choose>
        </div>
    </c:if>
    
    <form action="${pageContext.request.contextPath}/register" method="POST" onsubmit="return validatePasswords()">
        <input type="text" name="username" placeholder="Full Name" value="${param.username}" required>
        <input type="email" name="email" placeholder="Email Address" value="${param.email}" required>
        <input type="password" id="password" name="password" placeholder="Password" required>
        <input type="password" id="confirmedPassword" name="confirmedPassword" placeholder="Confirm Password" required>
        <span id="passwordError" class="error"></span>
        <input type="text" name="phone" placeholder="Phone Number" value="${param.phone}">
        <input type="text" name="address" placeholder="Address" value="${param.address}">
        <button type="submit">Sign Up</button>
    </form>
    <p style="font-size: 12px; text-align: center;">Already have an account? <a href="login.jsp">Login here</a></p>
</div>
</body>
</html>