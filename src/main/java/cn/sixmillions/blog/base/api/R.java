package cn.sixmillions.blog.base.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

/**
 * 统一返回结果封装
 *
 * @author six
 * @since 2023/06/30
 */
@Data
@EqualsAndHashCode
@SuppressWarnings("unused")
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回时间戳
     */
    private Long time;

    /**
     * 错误代码
     */
    private String errorCode;

    public R() {
    }

    /**
     * @param code      状态码
     * @param data      返回数据
     * @param success   是否成功
     * @param errorCode 错误代码
     * @param message   返回消息
     */
    private R(int code, T data, boolean success, String errorCode, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = success;
        this.errorCode = errorCode;
        this.time = Instant.now().getEpochSecond();
    }

    /**
     * @param code    状态码
     * @param data    返回数据
     * @param message 返回消息
     */
    private R(int code, T data, String message) {
        this(code, data, ResultCode.SUCCESS.getCode() == code, "", message);
    }

    /**
     * @param resultCode 返回状态码封装
     * @param data       返回数据
     * @param message    返回消息
     */
    private R(IResultCode resultCode, T data, String message) {
        this(resultCode.getCode(), data, message);
    }

    /**
     * @param resultCode 返回状态码封装
     */
    private R(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    /**
     * @param resultCode 返回状态码封装
     * @param message    返回消息
     */
    private R(IResultCode resultCode, String message) {
        this(resultCode, null, message);
    }

    /**
     * @param resultCode 返回状态码封装
     * @param data       返回数据
     */
    private R(T data, IResultCode resultCode) {
        this(resultCode, data, resultCode.getMessage());
    }

    /**
     * @param resultCode 返回状态码封装
     * @param data       返回数据
     * @param errorCode  错误代码
     * @param message    返回消息
     */
    private R(IResultCode resultCode, T data, String errorCode, String message) {
        this(resultCode.getCode(), data, ResultCode.SUCCESS.getCode() == resultCode.getCode(), errorCode, message);
    }

    /**
     * 设置返回内容：自定义返回码，返回内容，返回消息
     *
     * @param code 返回状态码
     * @param data 返回数据
     * @param msg  返回消息
     * @param <T>  返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> data(int code, T data, String msg) {
        return new R<>(code, data, msg);
    }

    /**
     * 成功返回：设置返回内容和返回消息
     *
     * @param data 返回数据
     * @param msg  返回消息
     * @param <T>  返回数据泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> data(T data, String msg) {
        return data(ResultCode.SUCCESS.getCode(), data, msg);
    }

    /**
     * 成功返回：设置返回内容
     *
     * @param data 返回数据
     * @param <T>  返回数据泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> data(T data) {
        return data(data, "success");
    }

    /**
     * 设置返回内容：自定义返回状态码
     *
     * @param data       返回数据
     * @param resultCode 返回状态码封装
     * @param <T>        返回数据泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> data(T data, IResultCode resultCode) {
        return new R<>(data, resultCode);
    }

    /**
     * 成功返回：无返回数据
     *
     * @param msg 返回消息
     * @param <T> 返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> success(String msg) {
        return new R<>(ResultCode.SUCCESS, msg);
    }

    /**
     * 自定义返回结果状态枚举
     *
     * @param resultCode 返回状态码封装
     * @param <T>        返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> success(IResultCode resultCode) {
        return new R<>(resultCode);
    }

    /**
     * 自定义返回结果状态码和返回消息
     *
     * @param resultCode 返回状态码封装
     * @param msg        返回消息
     * @param <T>        返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> success(IResultCode resultCode, String msg) {
        return new R<>(resultCode, msg);
    }

    /**
     * 失败返回
     *
     * @param msg 返回消息
     * @param <T> 返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> fail(String msg) {
        return new R<>(ResultCode.FAILURE, msg);
    }

    /**
     * 失败返回
     *
     * @param errorCode 错误代码
     * @param msg       错误描述
     * @param <T>       返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> fail(String errorCode, String msg) {
        return new R<>(ResultCode.FAILURE, null, errorCode, msg);
    }

    /**
     * 失败返回
     *
     * @param code 状态码
     * @param msg  错误描述
     * @param <T>  返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> fail(int code, String msg) {
        return new R<>(code, null, msg);
    }

    /**
     * 失败返回
     *
     * @param resultCode 返回结果枚举
     * @param <T>        返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> fail(IResultCode resultCode) {
        return new R<>(resultCode);
    }

    /**
     * 失败返回
     *
     * @param resultCode 返回结果枚举
     * @param msg        返回消息
     * @param <T>        返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> fail(IResultCode resultCode, String msg) {
        return new R<>(resultCode, msg);
    }

    /**
     * 失败返回：自定义返回状态码，错误代码，返回消息
     *
     * @param resultCode 返回结果枚举
     * @param errorCode  错误代码
     * @param msg        返回消息
     * @param <T>        返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> fail(IResultCode resultCode, String errorCode, String msg) {
        return new R<>(resultCode, null, errorCode, msg);
    }

    /**
     * 是否成功
     *
     * @param result R
     * @return 是否成功
     */
    public static boolean isSuccess(@Nullable R<?> result) {
        return Optional.ofNullable(result).map(x -> ResultCode.SUCCESS.getCode() == x.code).orElse(Boolean.FALSE);
    }

    /**
     * 不成功
     *
     * @param result R
     * @return 是否失败
     */
    public static boolean isFail(@Nullable R<?> result) {
        return !isSuccess(result);
    }

    /**
     * 返回成功R或者失败R
     *
     * @param flag 布尔
     * @param <T>  返回结果泛型
     * @return 统一封装的返回结果
     */
    public static <T> R<T> status(boolean flag) {
        return flag ? success("") : fail("操作失败");
    }
}