package zzu.group.www.selfserviceorderingsystem.mapper;

import org.apache.ibatis.annotations.*;
import zzu.group.www.selfserviceorderingsystem.javabean.User;
@Mapper
public interface UserMapper {
    // 插入新用户
    @Insert("insert into customer(username, password) values (#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUserInCustomer(User user);
    @Insert("insert into merchant(username, password) values (#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUserInMerchant(User user);


    // 根据用户名查找用户
    @Select("select id, username, password from customer where username = #{username}")
    User findByUsernameInCustomer(String username);
    @Select("select id, username, password from merchant where username = #{username}")
    User findByUsernameInInMerchant(String username);


    // 根据用户名和密码查找用户（用于登录验证）
    @Select("select id, username, password from customer where username = #{username} and password = #{password}")
    User findByUsernameAndPasswordInCustomer(@Param("username") String username, @Param("password") String password);
    @Select("select id, username, password from merchant where username = #{username} and password = #{password}")
    User findByUsernameAndPasswordInMerchant(@Param("username") String username, @Param("password") String password);
}
