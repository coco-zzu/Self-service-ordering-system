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
            if(user.getRole()==0)
            {
                if(userMapper.findByUsernameInCustomer(user.getUsername()) == null)
                {
                    // Set default points to 0 for new customers
                    user.setPoints(0);
                    int result = userMapper.insertUserInCustomer(user);
                    return result > 0;
                }
                else return false;
            }
            else
            {
                if (userMapper.findByUsernameInMerchant(user.getUsername()) == null)
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
        try {
            if(user.getRole()==0) {
                // Use the new method that doesn't select points to avoid the error
                User user1 = userMapper.findByUsernameAndPasswordInCustomer(user.getUsername(), user.getPassword());
                if (user1 != null) {
                    user1.setRole(0);
                    // Set default points value since we didn't select it
                    user1.setPoints(0);
                }
                return user1;
            }
            else
            {
                User user1 = userMapper.findByUsernameAndPasswordInMerchant(user.getUsername(), user.getPassword());
                if (user1 != null) {
                    user1.setRole(1);
                }
                return user1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get user with points information
    public User getUserWithPoints(String username, String password) {
        try {
            return userMapper.findCustomerWithPoints(username, password);
        } catch (Exception e) {
            // If the points column doesn't exist, fall back to basic user info
            try {
                User user = userMapper.findByUsernameAndPasswordInCustomer(username, password);
                if (user != null) {
                    user.setPoints(0); // Set default points
                }
                return user;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
    
    // Get user by ID with points information
    public User getUserByIdWithPoints(Long userId) {
        try {
            return userMapper.findCustomerByIdWithPoints(userId);
        } catch (Exception e) {
            // If the points column doesn't exist, fall back to basic user info
            try {
                User user = userMapper.findCustomerById(userId);
                if (user != null) {
                    user.setPoints(0); // Set default points
                }
                return user;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
    
    // Update user points
    public boolean updateUserPoints(User user) {
        try {
            if (user.getRole() == 0) { // Only update points for customers
                userMapper.updateUserPoints(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Failed to update user points: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Deduct points from user
    public boolean deductUserPoints(Long userId, int pointsToDeduct) {
        try {
            User user = getUserByIdWithPoints(userId);
            if (user != null && user.getPoints() >= pointsToDeduct) {
                user.setPoints(user.getPoints() - pointsToDeduct);
                return updateUserPoints(user);
            }
            return false;
        } catch (Exception e) {
            System.err.println("Failed to deduct user points: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 根据用户名查找用户
//    public User findByUsername(String username) {
//        return userMapper.findByUsername(username);
//    }
}