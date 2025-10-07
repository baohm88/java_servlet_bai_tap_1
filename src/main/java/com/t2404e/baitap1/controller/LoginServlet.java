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

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private static final String LOGIN_PAGE = "/auth/login.jsp";
    private static final String PROFILE_PAGE = "/user/profile";

    private final AccountRepository repo = new MySQLAccountRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Nếu user đã login rồi → chuyển sang profile luôn
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + PROFILE_PAGE);
            return;
        }

        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // --- Input validation ---
        InputValidator validator = new InputValidator(repo);
        Map<String, String> errors = validator.validateLoginInput(username, password);

        // Nếu có lỗi input → quay lại trang login
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("username", username);
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            return;
        }

        // --- Kiểm tra user trong DB ---
        Account acc = repo.findByUsername(username);

        if (acc == null || !PasswordUtils.verify(password, acc.getPasswordHash())) {
            req.setAttribute("loginError", "Invalid username and/or password");
            req.setAttribute("username", username);
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            return;
        }

        // --- Tạo session và lưu user ---
        HttpSession session = req.getSession(true);
        session.setAttribute("user", acc);
        session.setMaxInactiveInterval(30 * 60); // 30 phút timeout session

        // --- Redirect sang trang profile ---
        resp.sendRedirect(req.getContextPath() + PROFILE_PAGE);
    }
}
