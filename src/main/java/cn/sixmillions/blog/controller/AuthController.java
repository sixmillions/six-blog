package cn.sixmillions.blog.controller;

import cn.sixmillions.blog.auth.AuthRes;
import cn.sixmillions.blog.auth.AuthUser;
import cn.sixmillions.blog.auth.SignUpUser;
import cn.sixmillions.blog.base.api.R;
import cn.sixmillions.blog.dto.mapper.UserConverter;
import cn.sixmillions.blog.service.IUserService;
import cn.sixmillions.blog.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 认证相关接口
 *
 * @author six
 * @since 2023/07/01
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

    private final UserConverter userConverter;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public R<AuthRes> authByPassword(@RequestBody AuthUser auth) {
        // 认证
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
        // 获取认证的信息
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        // 生成jwt
        String token = jwtUtil.createToken(auth.getUsername(), Map.of("roles", AuthorityUtils.authorityListToSet(userDetails.getAuthorities())));
        return R.data(new AuthRes(auth.getUsername(), token));
    }

    /**
     * 用户注册接口
     *
     * @param user 注册信息
     * @return 注册结果
     */
    @PostMapping("/signup")
    public R<Object> signup(@RequestBody @Valid SignUpUser user) {
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // TODO 检验phoneNumber的唯一性
        boolean save = userService.save(userConverter.toUser(user));
        return R.status(save);
    }

}
