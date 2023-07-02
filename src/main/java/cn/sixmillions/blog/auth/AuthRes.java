package cn.sixmillions.blog.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 认证返回
 *
 * @author six
 * @since 2023/07/01
 */
@Data
@Builder
@AllArgsConstructor
public class AuthRes {

    /**
     * 账号
     * 用户名，邮箱，手机号
     */
    private String code;

    /**
     * Jwt Token
     */
    private String token;
}
