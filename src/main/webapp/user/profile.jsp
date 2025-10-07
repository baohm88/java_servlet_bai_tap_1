<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.t2404e.baitap1.entity.Account" %>

<%
    Account currentUser = (Account) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile - MyWebApp</title>
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
        }
        .card {
            border: none;
            border-radius: 20px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.08);
            background: white;
            padding: 30px;
            max-width: 480px;
            width: 100%;
        }
        h2 {
            color: #0d6efd;
            font-weight: 600;
        }
        .info-label {
            font-weight: 600;
        }
    </style>
</head>

<body>
<%@ include file="/includes/navbar.jsp" %>

<main>
    <div class="card">
        <h2 class="text-center mb-4">Your Profile 👤</h2>

        <p><span class="info-label">Username:</span> <%= currentUser.getUsername() %></p>
        <p><span class="info-label">Email:</span> <%= currentUser.getEmail() %></p>
        <p><span class="info-label">Status:</span> <%= currentUser.getStatus() %></p>
        <p><span class="info-label">Created At:</span> <%= currentUser.getCreatedAt() %></p>


        <!-- Ảnh avatar -->
        <% if (currentUser.getAvatarUrl() != null && !currentUser.getAvatarUrl().isEmpty()) { %>
        <img src="<%= currentUser.getAvatarUrl() %>" alt="avatar" style="max-width:140px;border-radius:12px;">
        <% } %>

        <!-- Bio dưới dạng HTML -->
        <div class="mt-3">
            <% if (currentUser.getBio() != null && !currentUser.getBio().isEmpty()) { %>
            <%= currentUser.getBio() %>  <!-- render HTML -->
            <% } else { %>
            <em>No bio yet.</em>
            <% } %>
        </div>

        <div class="d-grid gap-2 mt-4">
            <a href="${pageContext.request.contextPath}/user/edit" class="btn btn-primary btn-lg">Edit Profile</a>
            <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-outline-danger btn-lg">Logout</a>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
