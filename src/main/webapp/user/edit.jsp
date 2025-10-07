<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.t2404e.baitap1.entity.Account" %>

<%
    Account currentUser = (Account) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login");
        return;
    }

    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
    String success = (String) request.getAttribute("success");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Account - MyWebApp</title>
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
        .form-control {
            border-radius: 10px;
            padding: 10px 14px;
        }
    </style>
</head>

<body>
<%@ include file="/includes/navbar.jsp" %>

<main>
    <div class="card">
        <h2 class="text-center mb-4">Edit Your Profile ✏️</h2>

        <% if (success != null) { %>
        <div class="alert alert-success text-center"><%= success %></div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/user/edit" novalidate>
            <div class="mb-3">
                <label class="form-label fw-semibold">Username</label>
                <input type="text" name="username" value="<%= currentUser.getUsername() %>"
                       class="form-control <%= (errors != null && errors.containsKey("username")) ? "is-invalid" : "" %>">
                <% if (errors != null && errors.containsKey("username")) { %>
                <div class="invalid-feedback"><%= errors.get("username") %></div>
                <% } %>
            </div>

            <div class="mb-3">
                <label class="form-label fw-semibold">Email</label>
                <input type="email" name="email" value="<%= currentUser.getEmail() %>"
                       class="form-control <%= (errors != null && errors.containsKey("email")) ? "is-invalid" : "" %>">
                <% if (errors != null && errors.containsKey("email")) { %>
                <div class="invalid-feedback"><%= errors.get("email") %></div>
                <% } %>
            </div>

            <div class="mb-3">
                <label class="form-label fw-semibold">New Password (optional)</label>
                <input type="password" name="password"
                       class="form-control <%= (errors != null && errors.containsKey("password")) ? "is-invalid" : "" %>">
                <% if (errors != null && errors.containsKey("password")) { %>
                <div class="invalid-feedback"><%= errors.get("password") %></div>
                <% } %>
            </div>


            <!-- Avatar -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Avatar</label>
                <input type="hidden" id="avatar-url" name="avatarUrl" value="<%= currentUser.getAvatarUrl() != null ? currentUser.getAvatarUrl() : "" %>">
                <img id="avatar-preview"
                     src="<%= currentUser.getAvatarUrl() != null ? currentUser.getAvatarUrl() : "" %>"
                     style="<%= currentUser.getAvatarUrl() != null ? "display:block;max-width:120px;border-radius:10px;" : "display:none;" %>"
                     class="mb-2" alt="avatar preview">
                <button type="button" id="avatar_upload_btn" class="btn btn-outline-primary btn-sm">Upload Avatar</button>
                <% if (errors != null && errors.containsKey("avatarUrl")) { %>
                <div class="text-danger small mt-1"><%= errors.get("avatarUrl") %></div>
                <% } %>
            </div>

            <!-- Bio -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Bio</label>
                <textarea id="bio" name="bio"><%= currentUser.getBio() != null ? currentUser.getBio() : "" %></textarea>
                <% if (errors != null && errors.containsKey("bio")) { %>
                <div class="text-danger small mt-1"><%= errors.get("bio") %></div>
                <% } %>
            </div>

            <div class="d-grid gap-2 mt-4">
                <button type="submit" class="btn btn-primary btn-lg">Save Changes</button>
                <a href="${pageContext.request.contextPath}/user/profile" class="btn btn-outline-secondary">Cancel</a>
            </div>
        </form>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/41.1.0/classic/ckeditor.js"></script>
<script src="https://upload-widget.cloudinary.com/latest/global/all.js" type="text/javascript"></script>
<script>
    ClassicEditor.create(document.querySelector('#bio')).catch(console.error);

    const avatarWidget = cloudinary.createUploadWidget({
        cloudName: 'xuanhung2401',
        uploadPreset: 'ufx1h3qp'
    }, (error, result) => {
        if (!error && result && result.event === "success") {
            const url = result.info.secure_url;
            document.getElementById("avatar-url").value = url;
            const img = document.getElementById("avatar-preview");
            img.src = url;
            img.style.display = "block";
        }
    });

    document.getElementById("avatar_upload_btn").addEventListener("click", function() {
        avatarWidget.open();
    }, false);
</script>
</body>
</html>
