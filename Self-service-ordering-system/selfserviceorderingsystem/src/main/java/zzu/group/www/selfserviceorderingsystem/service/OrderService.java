package zzu.group.www.selfserviceorderingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzu.group.www.selfserviceorderingsystem.javabean.Order;
import zzu.group.www.selfserviceorderingsystem.javabean.OrderItem;
import zzu.group.www.selfserviceorderingsystem.mapper.OrderMapper;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    // 创建订单
    public Order createOrder(Order order) {
        try {
            // 插入订单主表
            orderMapper.insertOrder(order);

            // 插入订单明细
            for (OrderItem item : order.getItems()) {
                item.setOrderId(order.getId()); // 设置订单ID
                orderMapper.insertOrderItem(item);
            }

            return order;
        } catch (Exception e) {
            throw new RuntimeException("创建订单失败: " + e.getMessage(), e);
        }
    }

    // 获取所有订单
    public List<Order> getAllOrders() {
        try {
            // Use the correct method name from OrderMapper
            return orderMapper.findAll();
        } catch (Exception e) {
            throw new RuntimeException("获取订单列表失败: " + e.getMessage(), e);
        }
    }

    // 更新订单状态
    public void updateOrderStatus(Long orderId, Integer status) {
        try {
            orderMapper.updateOrderStatus(orderId, status);
        } catch (Exception e) {
            throw new RuntimeException("更新订单状态失败: " + e.getMessage(), e);
        }
    }

    // 删除订单
    public void deleteOrder(Long orderId) {
        try {
            // 先删除订单项
            orderMapper.deleteOrderItemsByOrderId(orderId);
            // 再删除订单
            orderMapper.deleteOrder(orderId);
        } catch (Exception e) {
            throw new RuntimeException("删除订单失败: " + e.getMessage(), e);
        }
    }
    
    // 根据ID获取订单
    public Order getOrderById(Long orderId) {
        try {
            // Use the correct method name from OrderMapper
            Order order = orderMapper.findById(orderId);
            if (order != null) {
                // Also get the order items
                List<OrderItem> items = getOrderItems(orderId);
                order.setItems(items);
            }
            return order;
        } catch (Exception e) {
            throw new RuntimeException("获取订单失败: " + e.getMessage(), e);
        }
    }
    
    // 根据订单ID获取订单项
    public List<OrderItem> getOrderItems(Long orderId) {
        try {
            return orderMapper.findItemsByOrderId(orderId);
        } catch (Exception e) {
            throw new RuntimeException("获取订单项失败: " + e.getMessage(), e);
        }
    }
    
    // Apply points discount to amount
    public BigDecimal applyPointsDiscount(BigDecimal amount, Integer points) {
        if (points == null || points <= 0) {
            return amount;
        }
        
        // Each point is worth 1 yuan
        BigDecimal discount = new BigDecimal(points);
        return amount.subtract(discount).max(BigDecimal.ZERO);
    }
}