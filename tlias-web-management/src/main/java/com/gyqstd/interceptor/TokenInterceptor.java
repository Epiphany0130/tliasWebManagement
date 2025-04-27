package com.gyqstd.interceptor;

import com.gyqstd.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        // 1. 获取请求路径
//        String requestURI = request.getRequestURI();
//
//        // 2. 判断是否为登录请求
//        if(requestURI.contains("/login")) {
//            log.info("登录请求，放行");
//            return true;
//        }

        // 3. 获取请求头中的 token
        String token = request.getHeader("token");

        // 4. 判断 token 是否存在，如果不存在，说明用户没有登录，返回错误信息（响应 401）
        if(token == null || token.isEmpty()) {
            log.info("令牌为空，响应 401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 5. 如果 token 存在，校验令牌，如果校验失败，返回错误信息（响应 401）
        try {
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("令牌非法，响应 401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }


        // 6. 校验通过，放行
        log.info("令牌合法，放行");
        return true;
    }
}
