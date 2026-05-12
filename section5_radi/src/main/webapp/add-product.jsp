<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Product</title>
    <style>
        .msg { color: green; font-weight: bold; }
        .err { color: red; font-weight: bold; }
        form { width: 300px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; }
    </style>
</head>
<body>
    <div align="center">
        <h2>Add New Product</h2>


        <p class="msg">${param.message}</p>
        <p class="err">${param.error}</p>

        <form action="${pageContext.request.contextPath}/admin/add-product" method="POST">
            <input type="text" name="name" placeholder="Product Name" required><br><br>
            <input type="text" name="price" placeholder="Price (e.g. 99.99)" required><br><br>
            <input type="text" name="image" placeholder="Image URL or Name" required><br><br>
            <button type="submit">Save Product</button>
        </form>
        <br>
        <a href="ProductsMain">Back to Products List</a>
    </div>
</body>
</html>