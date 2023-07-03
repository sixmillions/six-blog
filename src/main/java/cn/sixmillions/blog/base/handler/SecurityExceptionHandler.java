package cn.sixmillions.blog.base.handler;

import cn.sixmillions.blog.base.api.R;
import cn.sixmillions.blog.base.api.SecurityError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static cn.sixmillions.blog.base.api.ResultCode.*;

/**
 * 认证授权相关异常处理
 * <a href="https://www.baeldung.com/spring-security-exceptionhandler">全局处理认证健全异常</a>
 *
 * @author six
 * @since 2023/07/01
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {

    /**
     * InsufficientAuthenticationException:登陆凭证不够充分而抛出的异常
     */
    @ExceptionHandler({InsufficientAuthenticationException.class})
    @ResponseStatus(HttpStatus.OK)
    public R<Object> insufficientAuthenticationExceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("url:{},| errMsg:{}", request.getRequestURI(), ex.getMessage(), ex);
        return R.fail(CLIENT_UN_AUTHORIZED, SecurityError.INVALID_TOKEN.getErrorCode(), ex.getMessage());
    }

    /**
     * BadCredentialsException: 登录凭证（密码）异常
     * UsernameNotFoundException: 用户名不存在异常
     */
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.OK)
    public R<Object> badCredentialsExceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("url:{},| errMsg:{}", request.getRequestURI(), ex.getMessage(), ex);
        return R.fail(CLIENT_UN_AUTHORIZED, ex.getMessage());
    }


    /**
     * 认证异常处理
     * AuthenticationException是认证异常的父类
     *
     * @param ex      异常
     * @param request 请求
     * @return 统一json
     */
    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R<Object> authenticationExceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("url:{},| errMsg:{}", request.getRequestURI(), ex.getMessage(), ex);
        return R.fail(UN_AUTHORIZED, "401001", ex.getMessage());
    }

    /**
     * 授权异常处理
     * AccessDeniedException是授权异常的父类
     *
     * @param ex      异常
     * @param request 请求
     * @return 统一json
     */
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R<Object> accessDeniedExceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("url:{},| errMsg:{}", request.getRequestURI(), ex.getMessage(), ex);
        return R.fail(REQ_REJECT, "403001", ex.getMessage());
    }

}
