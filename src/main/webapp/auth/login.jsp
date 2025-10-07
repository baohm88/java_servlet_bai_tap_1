<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>

<%
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
    String username = (String) request.getAttribute("username");
    String loginError = (String) request.getAttribute("loginError");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - MyWebApp</title>
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
        }
        .form-control {
            border-radius: 10px;
            padding: 10px 14px;
        }
        .btn {
            border-radius: 10px;
            font-weight: 500;
        }
        .btn-primary {
            background-color: #0d6efd;
            border: none;
        }
        .btn-primary:hover {
            background-color: #0b5ed7;
        }
        h2 {
            color: #0d6efd;
            font-weight: 600;
        }
        .link-primary {
            font-weight: 500;
            text-decoration: none;
        }
        .link-primary:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>
<%@ include file="/includes/navbar.jsp" %>

<main>
    <div class="card p-4 w-100" style="max-width: 420px;">
        <div class="card-body">
            <h2 class="text-center mb-4">Welcome Back 👋</h2>

            <% if (loginError != null) { %>
            <div class="alert alert-danger text-center"><%= loginError %></div>
            <% } %>

            <form method="post" action="login" novalidate>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Username</label>
                    <input type="text" name="username" autofocus
                           class="form-control <%= (errors != null && errors.containsKey("username")) ? "is-invalid" : "" %>"
                           value="<%= username != null ? username : "" %>">
                    <% if (errors != null && errors.containsKey("username")) { %>
                    <div class="invalid-feedback"><%= errors.get("username") %></div>
                    <% } %>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Password</label>
                    <input type="password" name="password"
                           class="form-control <%= (errors != null && errors.containsKey("password")) ? "is-invalid" : "" %>">
                    <% if (errors != null && errors.containsKey("password")) { %>
                    <div class="invalid-feedback"><%= errors.get("password") %></div>
                    <% } %>
                </div>

                <div class="d-grid gap-2 mt-4">
                    <button type="submit" class="btn btn-primary btn-lg">Login</button>
                    <button type="reset" class="btn btn-outline-secondary">Reset</button>
                </div>
            </form>

            <div class="text-center mt-4">
                <small class="text-muted">Don’t have an account?</small>
                <a href="${pageContext.request.contextPath}/auth/register" class="link-primary">Register here</a>
            </div>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
