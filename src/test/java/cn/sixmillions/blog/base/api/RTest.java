package cn.sixmillions.blog.base.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static cn.sixmillions.blog.base.api.ResultCode.FAILURE;
import static cn.sixmillions.blog.base.api.ResultCode.SUCCESS;

/**
 * 统一返回测试
 *
 * @author six
 * @since 2023/06/30
 */
@Slf4j
public class RTest {

    @Test
    public void test_return_1() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // 返回：具体数据，code=200 和 success=true
        R<String> r1 = R.data(200, "数据内容", "自定义Message信息");
        log.info("r1: {}", mapper.writeValueAsString(r1));
        R<String> r2 = R.data("数据内容", "自定义Message信息");
        log.info("r2: {}", mapper.writeValueAsString(r2));
        R<String> r3 = R.data("数据内容");
        log.info("r3: {}", mapper.writeValueAsString(r3));
        R<String> r4 = R.data("数据内容", SUCCESS);
        log.info("r4: {}", mapper.writeValueAsString(r4));
    }

    @Test
    public void test_return_2() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // 返回，code=200 和 success=true
        R<String> r1 = R.success("自定义成功信息");
        log.info("r1: {}", mapper.writeValueAsString(r1));
        R<String> r2 = R.success(SUCCESS);
        log.info("r2: {}", mapper.writeValueAsString(r2));
        R<String> r3 = R.success(SUCCESS, "自定义成功信息");
        log.info("r3: {}", mapper.writeValueAsString(r3));
    }

    @Test
    public void test_return_3() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // 返回，code=400 和 success=false
        R<String> r1 = R.fail("自定义错误信息");
        log.info("r1: {}", mapper.writeValueAsString(r1));
        R<String> r2 = R.fail(FAILURE);
        log.info("r2: {}", mapper.writeValueAsString(r2));
        R<String> r3 = R.fail(FAILURE, "自定义错误信息");
        log.info("r3: {}", mapper.writeValueAsString(r3));
        R<String> r4 = R.fail(400, "自定义错误信息");
        log.info("r4: {}", mapper.writeValueAsString(r4));
    }

    @Test
    public void test_return_4() {
        R<String> r1 = R.fail("自定义错误信息");
        log.info("r1: {}, {}", R.isSuccess(r1), R.isFail(r1));
        R<String> r2 = R.success("自定义成功信息");
        log.info("r2: {}, {}", R.isSuccess(r2), R.isFail(r2));

        R<Object> s1 = R.status(true);
        log.info("s1: {}", s1.isSuccess());

        R<Object> s2 = R.status(false);
        log.info("s1: {}", s2.isSuccess());
    }

}