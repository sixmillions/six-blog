package cn.sixmillions.blog.base.api;

import java.io.Serializable;

/**
 * 结果码和消息提示接口
 *
 * @author six
 * @since 2023/06/30
 */
@SuppressWarnings("unused")
public interface IResultCode extends Serializable {

    /**
     * code
     *
     * @return 结果码
     */
    int getCode();

    /**
     * 消息
     *
     * @return 消息提示
     */
    String getMessage();

}
