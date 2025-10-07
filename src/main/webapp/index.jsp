<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.t2404e.baitap1.entity.Account" %>

<%
    Account currentUser = (Account) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home - MyWebApp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #dbeafe, #eef2ff);
            font-family: 'Segoe UI', sans-serif;
            height: 100vh;
            display: flex;
            flex-direction: column;
        }
        main {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
        }
        h1 {
            color: #0d6efd;
            font-weight: 700;
        }
        .btn {
            border-radius: 10px;
            font-weight: 500;
            margin: 0 6px;
        }
    </style>
</head>

<body>
<%@ include file="/includes/navbar.jsp" %>

<main>
    <div>
        <% if (currentUser == null) { %>
        <h1 class="mb-4">Welcome to MyWebApp 🌐</h1>
        <p class="text-muted mb-4">Please login or create an account to continue.</p>
        <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-primary btn-lg">Login</a>
        <a href="${pageContext.request.contextPath}/auth/register" class="btn btn-outline-primary btn-lg">Register</a>
        <% } else { %>
        <h1 class="mb-4">Welcome back, <%= currentUser.getUsername() %> 👋</h1>
        <a href="${pageContext.request.contextPath}/user/profile" class="btn btn-success btn-lg">View Profile</a>
        <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-outline-danger btn-lg">Logout</a>
        <% } %>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
