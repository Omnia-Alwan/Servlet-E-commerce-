<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Login</title>
</head>

<body>

<div style="height:100vh; display:flex; justify-content:center; align-items:center; flex-direction:column;">

  <h2>Login</h2>

  <form method="post" action="login">

    <input type="text" name="email" placeholder="email" required />
    <br><br>

    <input type="password" name="password" placeholder="password" required />
    <br><br>

    <button type="submit">Login</button>

  </form>

</div>

</body>
</html>