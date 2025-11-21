package zzu.group.www.selfserviceorderingsystem.mapper;

import org.apache.ibatis.annotations.*;
import zzu.group.www.selfserviceorderingsystem.javabean.Product;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM products")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
        @Result(property = "price", column = "price"),
        @Result(property = "status", column = "status")
    })
    List<Product> findAll();
    
    @Select("SELECT * FROM products WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
        @Result(property = "price", column = "price"),
        @Result(property = "status", column = "status")
    })
    Product findById(Long id);
    
    @Insert("INSERT INTO products(name, description, price, status) VALUES(#{name}, #{description}, #{price}, #{status})")
    void insert(Product product);
    
    @Update("UPDATE products SET name=#{name}, description=#{description}, price=#{price}, status=#{status} WHERE id=#{id}")
    void update(Product product);
    
    @Delete("DELETE FROM products WHERE id = #{id}")
    void deleteById(Long id);
    
    @Update("UPDATE products SET status=#{status} WHERE id=#{id}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
}