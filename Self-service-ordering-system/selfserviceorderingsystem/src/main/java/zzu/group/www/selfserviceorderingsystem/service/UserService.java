package zzu.group.www.selfserviceorderingsystem.service;

import org.springframework.stereotype.Service;
import zzu.group.www.selfserviceorderingsystem.javabean.User;
import zzu.group.www.selfserviceorderingsystem.mapper.UserMapper;
@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 保存用户（注册）
    public boolean save(User user) {
        try {
            if(userMapper.findByUsername(user.getUsername()) == null) {
                int result = userMapper.insertUser(user);
                return result > 0;
            }
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 用户登录验证
    public User login(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, password);
    }

    // 根据用户名查找用户
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
