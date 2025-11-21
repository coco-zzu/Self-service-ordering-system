package zzu.group.www.selfserviceorderingsystem.mapper;

import org.apache.ibatis.annotations.*;
import zzu.group.www.selfserviceorderingsystem.javabean.User;
@Mapper
public interface UserMapper {
    // 插入新用户
    @Insert("insert into customer(username, password, points) values (#{username}, #{password}, #{points})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUserInCustomer(User user);
    @Insert("insert into merchant(username, password) values (#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUserInMerchant(User user);


    // 根据用户名查找用户
    @Select("select id, username, password from customer where username = #{username}")
    User findByUsernameInCustomer(String username);
    @Select("select id, username, password from merchant where username = #{username}")
    User findByUsernameInMerchant(String username);


    // 根据用户名和密码查找用户（用于登录验证）
    @Select("select id, username, password from customer where username = #{username} and password = #{password}")
    User findByUsernameAndPasswordInCustomer(@Param("username") String username, @Param("password") String password);
    @Select("select id, username, password from merchant where username = #{username} and password = #{password}")
    User findByUsernameAndPasswordInMerchant(@Param("username") String username, @Param("password") String password);
    
    // 更新用户积分
    @Update("update customer set points = #{points} where id = #{id}")
    void updateUserPoints(User user);
    
    // 获取包括积分在内的完整用户信息（当列存在时）
    @Select("select id, username, password, points from customer where username = #{username} and password = #{password}")
    User findCustomerWithPoints(@Param("username") String username, @Param("password") String password);
    
    // 根据用户ID查找用户（包括积分，当列存在时）
    @Select("select id, username, password, points from customer where id = #{id}")
    User findCustomerByIdWithPoints(Long id);
    
    // 根据用户ID查找用户（不包括积分）
    @Select("select id, username, password from customer where id = #{id}")
    User findCustomerById(Long id);
}