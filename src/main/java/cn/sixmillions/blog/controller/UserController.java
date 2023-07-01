package cn.sixmillions.blog.controller;

import cn.sixmillions.blog.base.api.R;
import cn.sixmillions.blog.dto.mapper.UserConverter;
import cn.sixmillions.blog.entity.User;
import cn.sixmillions.blog.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户 控制器
 *
 * @author six
 * @since 2023/07/01
 */
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    private final UserConverter userConverter;

    @GetMapping
    public R<List<User>> getUsers() {
        List<User> users = userService.list();
        return R.data(userConverter.removePassword(users));
    }

    @GetMapping("/{id}")
    public R<User> getUser(@PathVariable long id) {
        User user = userService.getById(id);
        return R.data(userConverter.removePassword(user));
    }

    @PostMapping("/")
    public R<Boolean> createUser(@RequestBody User user) {
        return R.data(userService.save(user));
    }

    @PutMapping("/{id}")
    public R<Boolean> updateUser(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        return R.data(userService.updateById(user));
    }

    @DeleteMapping("/{id}")
    public R<Boolean> deleteUser(@PathVariable long id) {
        return R.data(userService.removeById(id));
    }

}
