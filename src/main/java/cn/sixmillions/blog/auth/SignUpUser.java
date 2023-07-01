package cn.sixmillions.blog.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户注册pojo
 *
 * @author six
 * @since 2023/7/1
 */
@Data
public class SignUpUser {

    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名最大长度不超过50")
    private String username;

    @NotBlank(message = "用户密码不能为空")
    private String password;

    @NotBlank(message = "用户邮箱不能为空")
    @Size(max = 50, message = "用户邮箱最大长度不超过50")
    private String email;

}
