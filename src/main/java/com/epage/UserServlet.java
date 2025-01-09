package com.epage;

import com.epage.Entity.UserEntity;
import com.epage.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // /{userId} 또는 null
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if (pathInfo == null || pathInfo.equals("/getAllUsers")) {
                System.out.println("[e-Page users] /users/getAllUsers");
                // 모든 사용자 조회
                List<UserEntity> users = UseDatabase.getAllUsers();
                String jsonResponse = objectMapper.writeValueAsString(users);
                response.getWriter().write(jsonResponse);

            } else if (pathInfo.equals("/getUser")) {
                System.out.println("[e-Page users] /users/getUser");
                // 특정 사용자 조회
                String userId = request.getParameter("userId");
                if (userId == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse("userId is required")));
                    return;
                }
                UserEntity user = UseDatabase.getUser(userId);
                if (user == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse("User not found")));
                } else {
                    String jsonResponse = objectMapper.writeValueAsString(user);
                    response.getWriter().write(jsonResponse);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse("Invalid API path")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse("An error occurred")));
        }
    }
}
