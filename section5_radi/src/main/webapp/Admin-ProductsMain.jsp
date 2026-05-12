<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>E-Shop Products</title>
    <style>
        .product-card { border: 1px solid #ddd; padding: 10px; margin: 10px; display: inline-block; width: 200px; text-align: center; }
        .delete-btn { color: red; cursor: pointer; text-decoration: underline; background: none; border: none; }
    </style>
</head>
<body>
    <h2 align="center">Our Products</h2>
    <div align="center">
        <a href="add-product.jsp">Add New Product (Admin)</a> |
        <a href="logout">Logout</a>

    </div>
    <form action="delete" method="POST" onsubmit="return confirm('Are you sure you want to delete this account?')">
        <input type="hidden" name="_method" value="DELETE">
        <button type="submit" class="delete-btn">Delete My Account</button>
    </form>
    <hr>

    <div style="display: flex; flex-wrap: wrap; justify-content: center;">
        <c:forEach var="item" items="${products}">
            <div class="product-card">
                <img src="${item.image}" alt="${item.name}" style="width:100px; height:100px;"><br>
                <strong>${item.name}</strong><br>
                Price: $${item.price}<br>

                <a href="product-details?id=${item.id}">View Details</a>
                <br><br>


                <form action="${pageContext.request.contextPath}/admin/delete-product" method="POST" onsubmit="return confirm('Are you sure?')">
                    <input type="hidden" name="id" value="${item.id}">
                    <input type="hidden" name="_method" value="DELETE">
                    <button type="submit" class="delete-btn">Delete</button>
                </form>
            </div>
        </c:forEach>
    </div>
</body>
</html>