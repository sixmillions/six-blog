package cn.sixmillions.blog.auth;

import cn.sixmillions.blog.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 校验token，成功则认证通过
 *
 * @author six
 * @since 2023/7/1
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var token = getToken(request);
        var claims = jwtUtil.parseClims(token);
        // 没有异常，则认证通过
        SecurityContextHolder.getContext().setAuthentication(createAuthentication(claims));
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authenticationHeader == null || authenticationHeader.startsWith("/auth");
    }

    private Authentication createAuthentication(Map<String, Object> claims) throws JsonProcessingException {
        List<String> roleList = objectMapper.readValue(
                objectMapper.writeValueAsString(claims.get("roles")),
                new TypeReference<>() {
                }
        );
        var roles = roleList
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        // 认证通过：通过这个三个参数的方法放行
        return new UsernamePasswordAuthenticationToken(claims.get(Claims.SUBJECT), null, roles);
    }

    /**
     * 从request中获取token，token在request中一般让前端放入header中，格式如下
     * Authorization: Bearer xxxtokenxxx
     *
     * @param request 请求
     * @return jwt token
     */
    private String getToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(auth -> auth.startsWith("Bearer "))
                .map(auth -> auth.replace("Bearer ", ""))
                .orElseThrow(() -> new BadCredentialsException("无效Token"));
    }

}


