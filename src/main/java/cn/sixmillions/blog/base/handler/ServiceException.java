package cn.sixmillions.blog.base.handler;

import cn.sixmillions.blog.base.api.IResultCode;
import cn.sixmillions.blog.base.api.ResultCode;

import java.io.Serial;

/**
 * 自定义异常处理
 *
 * @author six
 * @since 2023/06/30
 */
public class ServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2359767895161832954L;

    private final IResultCode resultCode;

    private String errorCode;

    public IResultCode getResultCode() {
        return this.resultCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public ServiceException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }

    public ServiceException(IResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public ServiceException(IResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    public ServiceException(String errorCode, String message) {
        super(message);
        this.resultCode = ResultCode.FAILURE;
        this.errorCode = errorCode;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }

}
