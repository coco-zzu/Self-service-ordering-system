package zzu.group.www.selfserviceorderingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zzu.group.www.selfserviceorderingsystem.javabean.Order;
import zzu.group.www.selfserviceorderingsystem.javabean.OrderItem;
import zzu.group.www.selfserviceorderingsystem.javabean.User;
import zzu.group.www.selfserviceorderingsystem.service.OrderService;
import zzu.group.www.selfserviceorderingsystem.service.UserService;
import zzu.group.www.selfserviceorderingsystem.mapper.UserMapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;

    // 创建订单
    @PostMapping("/orders")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Order order) {
        try {
            Order createdOrder = orderService.createOrder(order);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", createdOrder);
            response.put("message", "订单创建成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "订单创建失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // 获取所有订单
    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取订单列表失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // 更新订单状态
    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(
            @PathVariable Long id, 
            @RequestBody Map<String, Integer> statusUpdate) {
        try {
            Integer status = statusUpdate.get("status");
            orderService.updateOrderStatus(id, status);
            
            // 如果订单状态变为已支付（status=1），则增加用户积分
            if (status == 1) {
                Order order = orderService.getOrderById(id);
                if (order != null) {
                    User user = userService.getUserByIdWithPoints(order.getUserId());
                    if (user != null) {
                        // 每满10元增加1积分
                        int pointsToAdd = order.getTotalAmount().intValue() / 10;
                        user.setPoints(user.getPoints() + pointsToAdd);
                        userService.updateUserPoints(user);
                    }
                }
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "订单状态更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "订单状态更新失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    // 删除订单
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "订单删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "删除订单失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    // 获取用户信息（包括积分）
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestParam String username, @RequestParam String password) {
        try {
            // Use the new method to get user with points
            User user = userService.getUserWithPoints(username, password);
            Map<String, Object> response = new HashMap<>();
            if (user != null) {
                response.put("success", true);
                response.put("data", user);
            } else {
                response.put("success", false);
                response.put("message", "用户未找到");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取用户信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    // 使用积分抵扣
    @PostMapping("/orders/calculate-with-points")
    public ResponseEntity<Map<String, Object>> calculateWithPoints(@RequestBody Map<String, Object> requestData) {
        try {
            Double amount = ((Number) requestData.get("amount")).doubleValue();
            Integer pointsToUse = (Integer) requestData.get("points");
            
            BigDecimal originalAmount = new BigDecimal(amount.toString());
            BigDecimal discountedAmount = orderService.applyPointsDiscount(originalAmount, pointsToUse);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", Map.of(
                "originalAmount", originalAmount,
                "discountedAmount", discountedAmount,
                "pointsUsed", pointsToUse,
                "discountAmount", new BigDecimal(pointsToUse)
            ));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "计算积分抵扣失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    // 扣除用户积分
    @PostMapping("/user/deduct-points")
    public ResponseEntity<Map<String, Object>> deductUserPoints(@RequestBody Map<String, Object> requestData) {
        try {
            Long userId = Long.valueOf(requestData.get("userId").toString());
            Integer pointsToDeduct = (Integer) requestData.get("points");
            
            boolean success = userService.deductUserPoints(userId, pointsToDeduct);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "积分扣除成功");
            } else {
                response.put("success", false);
                response.put("message", "积分扣除失败，可能积分不足");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "积分扣除失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}