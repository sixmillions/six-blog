package cn.sixmillions.blog.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 将异常抛出到全局处理
 * 参考 zalando的proble：org.zalando.problem.spring.web.advice.security.SecurityProblemSupport
 * <a href="https://www.baeldung.com/spring-security-exceptionhandler">全局处理认证健全异常</a>
 *
 * @author six
 * @since 2023/07/02
 */
@Component
public class SecurityExceptionSupport implements AuthenticationEntryPoint, AuthenticationFailureHandler, AccessDeniedHandler {

    private final HandlerExceptionResolver resolver;

    public SecurityExceptionSupport(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) {
        resolver.resolveException(request, response, null, ex);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) {
        resolver.resolveException(request, response, null, ex);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) {
        resolver.resolveException(request, response, null, ex);
    }
}
