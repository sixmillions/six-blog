package cn.sixmillions.blog.base.handler;

import cn.sixmillions.blog.base.api.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static cn.sixmillions.blog.base.api.ResultCode.REQ_REJECT;

/**
 * 认证相关异常处理
 * <a href="https://www.baeldung.com/spring-security-exceptionhandler">全局处理认证健全异常</a>
 *
 * @author six
 * @since 2023/07/01
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {

    // TODO 具体异常处理
    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R<Object> exceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("url:{},| errMsg:{}", request.getRequestURI(), ex.getMessage(), ex);
        return R.fail(REQ_REJECT, "40301", ex.getMessage());
    }

}
