package cn.sixmillions.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class SecurityConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("对密码进行加密")
    public void encodePassword() {
        log.info("密码加密结果：{}", passwordEncoder.encode("admin"));
        log.info("密码加密结果：{}", passwordEncoder.encode("user"));
    }
}