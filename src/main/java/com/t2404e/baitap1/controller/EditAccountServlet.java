package com.t2404e.baitap1.controller;

import com.t2404e.baitap1.entity.Account;
import com.t2404e.baitap1.helpers.InputValidator;
import com.t2404e.baitap1.helpers.PasswordUtils;
import com.t2404e.baitap1.repository.AccountRepository;
import com.t2404e.baitap1.repository.MySQLAccountRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/edit")
public class EditAccountServlet extends HttpServlet {
    private static final String EDIT_PAGE = "/user/edit.jsp";
    private static final String PROFILE_PAGE = "/user/profile";

    private final AccountRepository repo = new MySQLAccountRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        Account user = (Account) session.getAttribute("user");
        req.setAttribute("account", user);
        req.getRequestDispatcher(EDIT_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        Account currentUser = (Account) session.getAttribute("user");

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String avatarUrl = req.getParameter("avatarUrl");
        String bio = req.getParameter("bio");

        InputValidator validator = new InputValidator(repo);
        Map<String, String> errors = validator.validateEditInput(currentUser, username, email, password, avatarUrl, bio);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("account", currentUser);
            req.getRequestDispatcher(EDIT_PAGE).forward(req, resp);
            return;
        }

        // Hash password nếu người dùng nhập mới
        String newPasswordHash = currentUser.getPasswordHash();
        if (password != null && !password.trim().isEmpty()) {
            newPasswordHash = PasswordUtils.hash(password);
        }

        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setPasswordHash(newPasswordHash);
        currentUser.setAvatarUrl(isBlank(avatarUrl) ? null : avatarUrl);
        currentUser.setBio(InputValidator.sanitizeBio(bio));

        // Update DB
        repo.update(currentUser.getId(), currentUser);

        // Cập nhật session user
        session.setAttribute("user", currentUser);

        req.setAttribute("success", "✅ Account updated successfully!");
        req.setAttribute("account", currentUser);
        resp.sendRedirect(req.getContextPath() + PROFILE_PAGE);
    }

    private boolean isBlank(String string) {
        return string == null || string.trim().length() == 0;
    }
}
