package com.epage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/markany/*") // 보호할 경로를 설정
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 세션에서 사용자 정보 확인
        if (httpRequest.getSession(false) == null || httpRequest.getSession(false).getAttribute("user") == null) {
            // 세션이 없거나 인증되지 않은 경우 로그인 페이지로 리다이렉트
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        // 인증된 요청만 계속 진행
        chain.doFilter(request, response);
    }
}

