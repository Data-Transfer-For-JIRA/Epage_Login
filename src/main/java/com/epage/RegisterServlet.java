package com.epage;


import com.epage.request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String VALID_ADMIN_CODE = "markanyadmin";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();
        UserRequest userRequest = objectMapper.readValue(request.getReader(), UserRequest.class);

        String role = userRequest.getRole();
        String username = userRequest.getUsername();
        String userId = userRequest.getUserId();
        String password = userRequest.getPassword();
        String adminCode  = userRequest.getAdminCode();

        System.out.println("[e-Page Register info] role -------> "+role);
        System.out.println("[e-Page Register info] username  --> "+username);
        System.out.println("[e-Page Register info] password  --> "+password);
        System.out.println("[e-Page Register info] adminCode --> "+adminCode);

        // 필수 필드 검증
        if (!isValid(role, username, userId, password)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing or invalid fields");
            throw new RuntimeException("필드 검중 중 에러 발생");
        }

        // 만료일 설정
        Date expireDate;
        if ("admin".equals(role)) {
            if (VALID_ADMIN_CODE.equals(adminCode)) {
                expireDate = Date.valueOf("2999-12-31"); // 관리자 계정 만료일
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Invalid admin code");
                return;
            }
        } else if ("user".equals(role)) {
            expireDate = Date.valueOf(LocalDate.now().plusDays(7)); // 일반 사용자 계정 만료일
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid role");
            return;
        }
        // 아이디 중복 검증
        boolean isDuplicated = UseDatabase.checkDuplication(userId);
        boolean isSaved = false;

        if (isDuplicated) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("ID already registered.");
        }else{
            // 데이터 저장
           isSaved = UseDatabase.saveUser(role, username, userId, password, expireDate);

        }

        if (isSaved) {
            String redirectPath = "/login.jsp?register=true";
            System.out.println("[e-Page Login] Redirecting to: " + redirectPath);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("User registered successfully");
            response.sendRedirect(redirectPath);

        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to register user");
            throw new RuntimeException("사용자 등록 중 오류 발생");
        }

    }

    private boolean isValid(String role, String username, String userId, String password) {
        return role != null && !role.isEmpty() &&
                username != null && !username.isEmpty() &&
                userId != null && !userId.isEmpty() &&
                password != null && !password.isEmpty();
    }

}
