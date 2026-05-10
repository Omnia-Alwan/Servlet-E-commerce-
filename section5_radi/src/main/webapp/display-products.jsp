<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Products Display</title>
</head>
<body>
<c:forEach var="item" items="${products}">
    <div align="center">${item.id}</div>
    <div align="center">${item.name}</div>
    <div align="center">${item.price}</div>
    <div align="center">${item.image}</div>
    <br/>
</c:forEach>
</body>
</html>