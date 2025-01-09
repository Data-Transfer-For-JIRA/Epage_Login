package com.epage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/markany/*") // 보호할 경로를 설정
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 현재 요청 URI 가져오기
        String requestURI = httpRequest.getRequestURI();

        // 예외 처리할 경로 (로그인 및 회원가입 페이지)
        if (requestURI.equals(httpRequest.getContextPath() + "/markany/login.jsp") ||
                requestURI.equals(httpRequest.getContextPath() + "/markany/register.jsp")) {
            // 예외 경로는 필터를 통과
            chain.doFilter(request, response);
            return;
        }

        // 세션에서 사용자 정보 확인
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // 세션이 없거나 인증되지 않은 경우 로그인 페이지로 리다이렉트
            httpResponse.sendRedirect("https://epage.markany.com/markany/login.jsp");
            return;
        }

        // 인증된 요청만 계속 진행
        chain.doFilter(request, response);
    }
}

