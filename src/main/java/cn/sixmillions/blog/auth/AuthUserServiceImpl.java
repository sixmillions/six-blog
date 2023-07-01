package cn.sixmillions.blog.auth;

import cn.sixmillions.blog.entity.User;
import cn.sixmillions.blog.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * SpringSecurity使用的服务类，获取用户认证信息
 * 实现 UserDetailsService 接口，像容器中注入 UserDetailsService
 *
 * @author six
 * @since 2023/07/01
 */
@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements UserDetailsService {

    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getName, username));
        return Optional.ofNullable(one).map(AuthUser::new).orElseThrow(() -> new UsernameNotFoundException("用户名密码不正确"));
    }

}
