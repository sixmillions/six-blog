package cn.sixmillions.blog.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author six
 * @since 2023/06/30
 */
@Data
@Builder
@TableName("tm_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "主键id必填")
    private Long id;

    @TableField("user_name")
    @NotBlank(message = "用户名必填")
    @Size(max = 50, message = "用户名最大长度不超过50")
    private String name;

    @TableField("user_email")
    @NotBlank(message = "用户邮箱必填")
    @Size(max = 50, message = "用户邮箱最大长度不超过50")
    private String email;

    @TableField("user_password")
    @NotBlank(message = "用户密码必填")
    private String password;

    @Size(max = 100, message = "用户角色最大长度不超过100")
    private String roles;

    @Size(max = 20, message = "用户手机号最大长度不超过20")
    private String phoneNumber;

    @Size(max = 50, message = "用户昵称最大长度不超过50")
    private String nickname;

    @Size(max = 200, message = "用户头像最大长度不超过200")
    private String avatar;

    private String description;

    @Size(max = 50, message = "创建人最大长度不超过50")
    private String createdBy;

    private LocalDateTime createdAt;

    @Size(max = 50, message = "更新人最大长度不超过50")
    private String lastModifiedBy;

    private LocalDateTime lastModifiedAt;

}
