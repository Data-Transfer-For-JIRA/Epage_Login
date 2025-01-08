package com.epage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 세션 무효화
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        // 로그인 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
