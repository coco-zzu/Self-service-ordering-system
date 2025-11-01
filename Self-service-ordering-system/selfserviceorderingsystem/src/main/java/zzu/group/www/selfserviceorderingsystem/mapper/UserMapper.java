package zzu.group.www.selfserviceorderingsystem.mapper;

import org.apache.ibatis.annotations.*;
import zzu.group.www.selfserviceorderingsystem.javabean.User;
@Mapper
public interface UserMapper {
    // 插入新用户
    @Insert("insert into users(username, password, clearance) values (#{username}, #{password}, #{clearance})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);
    @Select("select id, username, password, clearance from users where username = #{username}")
    User findByUsername(String username);

    // 根据用户名和密码查找用户（用于登录验证）
    @Select("select id, username, password, clearance from users where username = #{username} and password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
