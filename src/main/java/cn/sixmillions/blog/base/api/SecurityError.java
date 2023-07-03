package cn.sixmillions.blog.base.api;

/**
 * 安全错误枚举
 *
 * @author six
 * @since 2023/7/3
 */
public enum SecurityError implements IErrorCode {

    /**
     * Token过期/无效
     */
    INVALID_TOKEN("401002", "Token过期/无效");

    private final String errorCode;

    private final String message;

    SecurityError(final String errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
