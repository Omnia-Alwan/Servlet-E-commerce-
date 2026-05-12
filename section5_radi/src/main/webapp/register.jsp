<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <style>
        body { font-family: Arial; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f4f4f4; }
        .container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); width: 300px; }
        input { width: 100%; padding: 8px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; }
    </style>
</head>
<body>
<div class="container">
    <h2>Create Account</h2>
    <form action="register" method="POST">
        <input type="text" name="username" placeholder="Full Name" required>
        <input type="email" name="email" placeholder="Email Address" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="password" name="confirmedPassword" placeholder="Confirm Password" required>
        <input type="text" name="phone" placeholder="Phone Number">
        <input type="text" name="address" placeholder="Address">
        <button type="submit">Sign Up</button>
    </form>
    <p style="font-size: 12px; text-align: center;">Already have an account? <a href="login.jsp">Login here</a></p>
</div>
</body>
</html>