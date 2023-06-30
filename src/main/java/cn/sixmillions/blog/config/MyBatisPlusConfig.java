package cn.sixmillions.blog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisPlus配置
 *
 * @author six
 * @since 2023/06/30
 */
@Configuration
@MapperScan("cn.sixmillions.blog.mapper")
public class MyBatisPlusConfig {

}
