package cn.sixmillions.blog.base.handler;


import cn.sixmillions.blog.base.api.R;
import cn.sixmillions.blog.base.api.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Set;

import static cn.sixmillions.blog.base.api.ResultCode.FAILURE;
import static cn.sixmillions.blog.base.api.ResultCode.INTERNAL_SERVER_ERROR;


/**
 * 全局异常处理类
 *
 * @author six
 * @since 2023/06/30
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 总异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Object> exceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("url:{},| errMsg:{}", request.getRequestURI(), ex.getMessage(), ex);
        return R.fail(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /**
     * 自定义系统异常异常捕捉处理
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<Object> serviceExceptionHandler(ServiceException ex, HttpServletRequest request) {
        log.error("url:{},| errMsg:{}", request.getRequestURI(), ex.getMessage());
        return R.fail(FAILURE, ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.OK)
    public R<Object> handleError(MissingServletRequestParameterException e) {
        log.warn("缺少请求参数: {}", e.getMessage(), e);
        String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
        return R.fail(ResultCode.PARAM_MISS, message);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.OK)
    public R<Object> handleError(MethodArgumentTypeMismatchException e) {
        log.warn("请求参数格式错误: {}", e.getMessage(), e);
        String message = String.format("请求参数格式错误: %s", e.getName());
        return R.fail(ResultCode.PARAM_TYPE_ERROR, message);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public R<Object> handleError(MethodArgumentNotValidException e) {
        log.warn("参数验证失败: {}", e.getMessage(), e);
        return this.handleError(e.getBindingResult());
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.OK)
    public R<Object> handleError(BindException e) {
        log.warn("参数绑定失败: {}", e.getMessage(), e);
        return this.handleError(e.getBindingResult());
    }

    private R<Object> handleError(BindingResult result) {
        List<FieldError> fieldErrorList = result.getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder("参数绑定或验证失败 ");
        for (FieldError error : fieldErrorList) {
            String message = String.format("%s:%s;", error.getField(), error.getDefaultMessage());
            stringBuilder.append(message);
        }
        return R.fail(ResultCode.PARAM_BIND_ERROR, stringBuilder.toString());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    public R<Object> handleError(ConstraintViolationException e) {
        log.warn("参数验证失败: {}", e.getMessage(), e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = String.format("参数验证失败 %s:%s", path, violation.getMessage());
        return R.fail(ResultCode.PARAM_VALID_ERROR, message);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R<Object> handleError(NoHandlerFoundException e) {
        log.error("404没找到请求:{}", e.getMessage(), e);
        return R.fail(ResultCode.NOT_FOUND);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    public R<Object> handleError(HttpMessageNotReadableException e) {
        log.error("消息不能读取:{}", e.getMessage(), e);
        return R.fail(ResultCode.MSG_NOT_READABLE);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R<Object> handleError(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法:{}", e.getMessage(), e);
        return R.fail(ResultCode.METHOD_NOT_SUPPORTED);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class})
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public R<Object> handleError(HttpMediaTypeNotSupportedException e) {
        log.error("不支持当前媒体类型:{}", e.getMessage(), e);
        return R.fail(ResultCode.MEDIA_TYPE_NOT_SUPPORTED);
    }

}
