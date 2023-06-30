package cn.sixmillions.blog.service;

import cn.sixmillions.blog.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * User接口测试
 * 运行的时候需要指定 .env 文件位置
 *
 * @author six
 * @since 2023/06/30
 */
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class IUserServiceTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("获取所有用户")
    @Order(1)
    public void test_get_all_user() throws JsonProcessingException {
        List<User> list = userService.list();
        List<User> admin = list.stream().filter(item -> item.getName().equals("admin")).toList();
        log.info("admin用户：{}", objectMapper.writeValueAsString(admin));
        Assertions.assertEquals(1, admin.size());
    }

}