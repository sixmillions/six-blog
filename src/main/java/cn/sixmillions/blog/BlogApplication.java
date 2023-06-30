package cn.sixmillions.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * App启动类
 * 运行的时候需要先指定 .env 文件位置
 *
 * @author six
 * @since 2023/06/30
 */
@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
