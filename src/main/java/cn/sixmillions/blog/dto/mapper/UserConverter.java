package cn.sixmillions.blog.dto.mapper;

import cn.sixmillions.blog.auth.SignUpUser;
import cn.sixmillions.blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * user 对象转换
 * map-struct
 *
 * @author six
 * @since 2023/7/1
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    /**
     * 注册用户转user实体类
     *
     * @param signUpUser 注册用户pojo
     * @return user实体类
     */
    @Mapping(source = "username", target = "name")
    User toUser(SignUpUser signUpUser);

    /**
     * 忽略密码
     *
     * @param user user实体类
     * @return 无密码的user实体类
     */
    @Mapping(target = "password", ignore = true)
    User removePassword(User user);

    /**
     * 忽略密码
     *
     * @param users user实体类list
     * @return 无密码的user实体类list
     */
    @Mapping(target = "password", ignore = true)
    List<User> removePassword(List<User> users);
}
