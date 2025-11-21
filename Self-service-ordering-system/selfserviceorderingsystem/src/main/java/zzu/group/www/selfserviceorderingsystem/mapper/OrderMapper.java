package zzu.group.www.selfserviceorderingsystem.mapper;

import org.apache.ibatis.annotations.*;
import zzu.group.www.selfserviceorderingsystem.javabean.Order;
import zzu.group.www.selfserviceorderingsystem.javabean.OrderItem;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("SELECT * FROM orders")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "totalAmount", column = "total_amount"),
        @Result(property = "status", column = "status"),
        @Result(property = "createTime", column = "create_time")
    })
    List<Order> findAll();
    
    @Select("SELECT * FROM orders WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "totalAmount", column = "total_amount"),
        @Result(property = "status", column = "status"),
        @Result(property = "createTime", column = "create_time")
    })
    Order findById(Long id);
    
    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "productId", column = "product_id"),
        @Result(property = "productName", column = "product_name"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price")
    })
    List<OrderItem> findItemsByOrderId(Long orderId);
    
    @Update("UPDATE orders SET status = #{status} WHERE id = #{id}")
    void updateOrderStatus(@Param("id") Long id, @Param("status") Integer status);
    
    @Insert("INSERT INTO orders(user_id, total_amount, status) VALUES(#{userId}, #{totalAmount}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOrder(Order order);
    
    @Insert("INSERT INTO order_item(order_id, product_id, product_name, quantity, price) VALUES(#{orderId}, #{productId}, #{productName}, #{quantity}, #{price})")
    void insertOrderItem(OrderItem orderItem);
    
    @Delete("DELETE FROM order_item WHERE order_id = #{orderId}")
    void deleteOrderItemsByOrderId(Long orderId);
    
    @Delete("DELETE FROM orders WHERE id = #{id}")
    void deleteOrder(Long id);
}