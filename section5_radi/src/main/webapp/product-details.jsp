<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Details - ${product.name}</title>
    <style>
        body {
            font-family: sans-serif;
            background: #fafafa;
            padding: 40px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
        }
        .details-card {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            display: flex;
            gap: 20px;
            margin-bottom: 30px;
        }
        .product-image {
            width: 250px;
            height: 250px;
            object-fit: cover;
            border-radius: 8px;
            border: 1px solid #eee;
        }
        .info h1 {
            margin: 0 0 10px 0;
            color: #222;
        }
        .price {
            font-size: 24px;
            color: #28a745;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #007bff;
        }

        /* Reviews section styles */
        .reviews-section {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            margin-bottom: 30px;
        }
        .review {
            border-bottom: 1px solid #eee;
            padding: 15px 0;
        }
        .review:last-child {
            border-bottom: none;
        }
        .review-username {
            font-weight: bold;
            color: #333;
        }
        .review-stars {
            color: #ffc107;
            margin-left: 10px;
        }
        .review-body {
            margin: 10px 0;
            color: #666;
        }
        .review-date {
            font-size: 12px;
            color: #999;
        }
        .no-reviews {
            color: #666;
            text-align: center;
            padding: 20px;
        }

        /* Add review form styles */
        .add-review-form {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        select, textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-family: inherit;
        }
        textarea {
            resize: vertical;
        }
        .submit-btn {
            padding: 10px 20px;
            background: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .submit-btn:hover {
            background: #218838;
        }
        .login-prompt {
            text-align: center;
            padding: 20px;
            background: white;
            border-radius: 10px;
        }
        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
        }
        .success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>

<div class="container">
    <!-- Display messages -->
    <c:if test="${not empty param.message}">
        <div class="message success">${param.message}</div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="message error">${param.error}</div>
    </c:if>

    <!-- Product Details Card -->
    <div class="details-card">
        <img src="${product.image}" alt="${product.name}" class="product-image">
        <div class="info">
            <small>Product ID: #${product.id}</small>
            <h1>${product.name}</h1>
            <p class="price">$${product.price}</p>
            <p style="color: #666;">
                This is a premium product selected for our E-commerce platform.
                High quality and durability guaranteed.
            </p>
            <button style="padding: 10px 20px; background: #333; color: white; border: none; border-radius: 5px; cursor: pointer;">
                Add to Cart
            </button>
        </div>
    </div>

    <!-- Reviews Section -->
    <div class="reviews-section">
        <h3>Customer Reviews</h3>

        <c:choose>
            <c:when test="${empty reviews}">
                <div class="no-reviews">
                    No reviews yet. Be the first to review this product!
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="review" items="${reviews}">
                    <div class="review">
                        <div>
                            <span class="review-username">${review.username}</span>
                            <span class="review-stars">
                                <c:forEach begin="1" end="${review.stars}">⭐</c:forEach>
                                <c:forEach begin="${review.stars + 1}" end="5">☆</c:forEach>
                                (${review.stars}/5)
                            </span>
                        </div>
                        <div class="review-body">${review.body}</div>
                        <div class="review-date">${review.createdAt}</div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Add Review Form (only show if user is logged in) -->
    <c:choose>
        <c:when test="${not empty email}">
            <div class="add-review-form">
                <h3>Write a Review</h3>
                <form action="${pageContext.request.contextPath}/add-review" method="post">
                    <input type="hidden" name="productId" value="${product.id}">

                    <div class="form-group">
                        <label for="stars">Rating:</label>
                        <select name="stars" id="stars" required>
                            <option value="5">5 - Excellent</option>
                            <option value="4">4 - Good</option>
                            <option value="3">3 - Average</option>
                            <option value="2">2 - Poor</option>
                            <option value="1">1 - Terrible</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="body">Your Review:</label>
                        <textarea name="body" id="body" rows="4" required
                                  placeholder="What do you think about this product?"></textarea>
                    </div>

                    <button type="submit" class="submit-btn">Submit Review</button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div class="login-prompt">
                <p><a href="${pageContext.request.contextPath}/login.jsp">Login</a> to write a review</p>
            </div>
        </c:otherwise>
    </c:choose>

    <!-- Back Link -->
    <div align="center">
        <a href="${pageContext.request.contextPath}/ProductsMain" class="back-link">← Back to Shopping</a>
    </div>
</div>

</body>
</html>