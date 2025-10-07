package com.t2404e.baitap1.controller;

import com.t2404e.baitap1.entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user/profile")
public class ProfileServlet extends HttpServlet {

    private static final String PROFILE_PAGE = "/user/profile.jsp";
    private static final String LOGIN_PAGE = "/auth/login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false); // false → không tạo mới nếu chưa có
        Account loggedInUser = null;

        if (session != null) {
            loggedInUser = (Account) session.getAttribute("user");
        }

        if (loggedInUser == null) {
            // chưa đăng nhập → quay lại trang login
            req.setAttribute("error", "Please login first!");
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            return;
        }

        // có user trong session → truyền qua JSP để hiển thị
        req.setAttribute("user", loggedInUser);
        req.getRequestDispatcher(PROFILE_PAGE).forward(req, resp);
    }
}
