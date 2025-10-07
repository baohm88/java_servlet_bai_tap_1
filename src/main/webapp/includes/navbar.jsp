<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.t2404e.baitap1.entity.Account" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
    <div class="container-fluid">
        <a class="navbar-brand fw-semibold" href="${pageContext.request.contextPath}/index.jsp">
            MyWebApp
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <% if (session.getAttribute("user") == null) { %>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/auth/login">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/auth/register">Register</a>
                </li>
                <% } else {
                    Account user = (Account) session.getAttribute("user");
                %>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/profile">
                        Hello, <%= user.getUsername() %> 👋
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
                </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>
