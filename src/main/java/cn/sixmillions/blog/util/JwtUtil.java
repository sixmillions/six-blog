package cn.sixmillions.blog.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author six
 * @since 2023/7/1
 */
@Component
public class JwtUtil {

    /**
     * 密钥
     * key的大小必须大于或等于256bit,也就是需要32位英文字符，
     * 一个英文字符为：8bit,一个中文字符为12bit
     * <a href="https://onlinerandomtools.com/generate-random-string">密钥在线生成</a>
     */
    private final Key key;

    /**
     * 加密字符串
     *
     * @param secret 加密字符串
     */
    public JwtUtil(@Value("${six.jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 创建JWT token
     *
     * @param sub    主题，这里我们放账号
     * @param claims 载体
     * @return token
     */
    public String createToken(String sub, Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                // 主题
                .setSubject(sub)
                // 负载（token携带的信息）
                .addClaims(claims)
                // 签发时间
                .setIssuedAt(Date.from(now))
                // 过期时间
                .setExpiration(Date.from(Instant.now().plus(5, ChronoUnit.MINUTES)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析token
     *
     * @param token token
     * @return 载体（body）
     */
    public Map<String, Object> parseClims(String token) {
        // parseClaimsJwt() 方法是解析没有进行签名的token,
        // 签名的token应该使用parseClaimsJws(String jws) 方法
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return claimsJws.getBody();
    }

}

