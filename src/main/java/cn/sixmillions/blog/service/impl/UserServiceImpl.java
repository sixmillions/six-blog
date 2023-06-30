package cn.sixmillions.blog.service.impl;

import cn.sixmillions.blog.entity.User;
import cn.sixmillions.blog.mapper.UserMapper;
import cn.sixmillions.blog.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户 服务实现
 *
 * @author six
 * @since 2023/06/30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}