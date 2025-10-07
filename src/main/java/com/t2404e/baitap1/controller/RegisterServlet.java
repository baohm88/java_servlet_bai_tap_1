package com.t2404e.baitap1.controller;

import com.t2404e.baitap1.entity.Account;
import com.t2404e.baitap1.entity.helper.AccountStatusEnum;
import com.t2404e.baitap1.helpers.InputValidator;
import com.t2404e.baitap1.helpers.PasswordUtils;
import com.t2404e.baitap1.repository.AccountRepository;
import com.t2404e.baitap1.repository.MySQLAccountRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/auth/register")
public class RegisterServlet extends HttpServlet {
    private static final String REGISTER_PATH = "/auth/register.jsp";
    private static final String LOGIN_PATH = "/auth/login.jsp";

    private final AccountRepository repo = new MySQLAccountRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(REGISTER_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String avatarUrl = req.getParameter("avatarUrl");
        String bio = req.getParameter("bio");

        InputValidator validator = new InputValidator(repo);
        Map<String, String> errors = validator.validateRegisterInput(username, email, password, confirmPassword, avatarUrl, bio);

        // Map errors
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.getRequestDispatcher(REGISTER_PATH).forward(req, resp);
            return;
        }

        // Hash password + save
        String hash = PasswordUtils.hash(password);

        Account acc = new Account();
        acc.setUsername(username);
        acc.setEmail(email);
        acc.setPasswordHash(hash);
        acc.setStatus(AccountStatusEnum.ACTIVE);
        acc.setAvatarUrl(isBlank(avatarUrl) ? null : avatarUrl);
        acc.setBio(InputValidator.sanitizeBio(bio));

        repo.save(acc);

        req.setAttribute("success", "Registration successful! Please login.");
        req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
    }

    private boolean isBlank(String string) {
        return string == null || string.trim().length() == 0;
    }
}
