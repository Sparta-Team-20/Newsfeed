package com.example.newsfeed.common.config;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    private static final String[] WHITE_LIST = {"*/auths/login", "*/users/register"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // 회원가입과 로그인은 인증 처리에서 제외
        if (isWhileList(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        String token = resolveToken(httpRequest);

        if (token == null) {
            throw new CustomExceptionHandler(ErrorCode.NOT_LOGIN);
        }

        if (jwtUtil.validateToken(token) != null) {
            Long userId = jwtUtil.extractUserId(token);
            request.setAttribute("LOGIN_USER", userId);
        } else {
            throw new CustomExceptionHandler(ErrorCode.INVALID_TOKEN);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean isWhileList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
