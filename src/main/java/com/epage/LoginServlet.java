package com.epage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        System.out.println("[e-Page Login] username  --> "+userId);
        System.out.println("[e-Page Login] password  --> "+password);

        if (UseDatabase.authenticateUser(userId, password)) {
            // 세션 생성 및 사용자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("user", userId);

            // 보호된 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/markany/protected.jsp");
        } else {
            // 로그인 실패 시 로그인 페이지로 리다이렉트
            System.out.println("[e-Page Login] login failed");
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=true");
        }
    }
}