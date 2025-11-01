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
//            if(userMapper.findByUsername(user.getUsername()) == null) {
//                if(user.getRole()==1) {
//                    int result = userMapper.insertUserInMerchant(user);
//                }
//                else {
//                    int result = userMapper.insertUserInCustomer(user);
//                }
//                return result > 0;
            if(user.getRole()==0)
            {
                if(userMapper.findByUsernameInCustomer(user.getUsername()) == null)
                {
                    int result = userMapper.insertUserInCustomer(user);
                    return result > 0;
                }
                else return false;
            }
            else
            {
                if (userMapper.findByUsernameInInMerchant(user.getUsername()) == null)
                {
                    int result = userMapper.insertUserInMerchant(user);
                    return result > 0;
                }
                else return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 用户登录验证
    public User login(User user) {
        if(user.getRole()==0) {
            User user1 = userMapper.findByUsernameInCustomer(user.getUsername());
            user1.setRole(0);
            return user1;
        }
        else
        {
            User user1 = userMapper.findByUsernameAndPasswordInMerchant(user.getUsername(), user.getPassword());
            user1.setRole(1);
            return user1;
        }
    }

    // 根据用户名查找用户
//    public User findByUsername(String username) {
//        return userMapper.findByUsername(username);
//    }
}
