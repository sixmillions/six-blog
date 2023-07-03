package cn.sixmillions.blog.base.api;

import java.io.Serializable;

/**
 * 结果码和消息提示接口
 *
 * @author six
 * @since 2023/07/03
 */
@SuppressWarnings("unused")
public interface IErrorCode extends Serializable {

    /**
     * code
     *
     * @return 结果码
     */
    String getErrorCode();

    /**
     * 消息
     *
     * @return 消息提示
     */
    String getMessage();

}