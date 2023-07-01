package cn.sixmillions.blog.auth;

import cn.sixmillions.blog.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SpringSecurity用到的用户认证信息
 *
 * @author six
 * @since 2023/07/01
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuthUser implements UserDetails {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "用户密码不能为空")
    private String password;

    private Set<GrantedAuthority> roles;

    public AuthUser(User user) {
        this.username = user.getName();
        this.password = user.getPassword();
        if (StringUtils.isBlank(user.getRoles())) {
            // 默认 ROLE_USER
            this.roles = Set.of(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            this.roles = Arrays.stream(user.getRoles().split(","))
                    .filter(item -> item != null && item.length() > 0)
                    .map(item -> item.trim().toUpperCase())
                    //用户需要 ROLE_ 开头
                    .map(item -> item.startsWith("ROLE_") ? item : "ROLE_" + item)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

