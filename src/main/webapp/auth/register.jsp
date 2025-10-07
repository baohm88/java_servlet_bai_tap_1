<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>

<%
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
    String username = (String) request.getAttribute("username");
    String email = (String) request.getAttribute("email");
    String success = (String) request.getAttribute("success");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - MyWebApp</title>
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
    <div class="card p-4 w-100" style="max-width: 460px;">
        <div class="card-body">
            <h2 class="text-center mb-4">Create Account ✨</h2>

            <% if (success != null) { %>
            <div class="alert alert-success text-center"><%= success %></div>
            <% } %>

            <form method="post" action="register" novalidate>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Username</label>
                    <input type="text" name="username"
                           class="form-control <%= (errors != null && errors.containsKey("username")) ? "is-invalid" : "" %>"
                           value="<%= username != null ? username : "" %>">
                    <% if (errors != null && errors.containsKey("username")) { %>
                    <div class="invalid-feedback"><%= errors.get("username") %></div>
                    <% } %>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Email</label>
                    <input type="email" name="email"
                           class="form-control <%= (errors != null && errors.containsKey("email")) ? "is-invalid" : "" %>"
                           value="<%= email != null ? email : "" %>">
                    <% if (errors != null && errors.containsKey("email")) { %>
                    <div class="invalid-feedback"><%= errors.get("email") %></div>
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

                <div class="mb-3">
                    <label class="form-label fw-semibold">Confirm Password</label>
                    <input type="password" name="confirmPassword"
                           class="form-control <%= (errors != null && errors.containsKey("confirmPassword")) ? "is-invalid" : "" %>">
                    <% if (errors != null && errors.containsKey("confirmPassword")) { %>
                    <div class="invalid-feedback"><%= errors.get("confirmPassword") %></div>
                    <% } %>
                </div>

                <!-- Avatar -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Avatar</label>

                    <input type="hidden" id="avatar-url" name="avatarUrl"
                           value="<%= request.getAttribute("avatarUrl") != null ? request.getAttribute("avatarUrl") : "" %>">

                    <img id="avatar-preview"
                         src="<%= request.getAttribute("avatarUrl") != null ? request.getAttribute("avatarUrl") : "" %>"
                         style="<%= request.getAttribute("avatarUrl") != null ? "display:block;max-width:120px;border-radius:10px;" : "display:none;" %>"
                         class="mb-2" alt="avatar preview">

                    <button type="button" id="avatar_upload_btn" class="btn btn-outline-primary btn-sm">
                        Upload Avatar
                    </button>

                    <% if (errors != null && errors.containsKey("avatarUrl")) { %>
                    <div class="text-danger small mt-1"><%= errors.get("avatarUrl") %></div>
                    <% } %>
                </div>

                <!-- Bio (HTML) -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Bio</label>
                    <textarea id="bio" name="bio"><%= request.getAttribute("bio") != null ? request.getAttribute("bio") : "" %></textarea>
                    <% if (errors != null && errors.containsKey("bio")) { %>
                    <div class="text-danger small mt-1"><%= errors.get("bio") %></div>
                    <% } %>
                </div>

                <div class="d-grid gap-2 mt-4">
                    <button type="submit" class="btn btn-primary btn-lg">Register</button>
                    <button type="reset" class="btn btn-outline-secondary">Reset</button>
                </div>
            </form>

            <div class="text-center mt-4">
                <small class="text-muted">Already have an account?</small>
                <a href="${pageContext.request.contextPath}/auth/login" class="link-primary">Login here</a>
            </div>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- CKEditor & Cloudinary scripts -->
<script src="https://cdn.ckeditor.com/ckeditor5/41.1.0/classic/ckeditor.js"></script>
<script src="https://upload-widget.cloudinary.com/latest/global/all.js" type="text/javascript"></script>
<script>
    // CKEditor
    ClassicEditor.create(document.querySelector('#bio')).catch(console.error);

    // Cloudinary Upload Widget
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
