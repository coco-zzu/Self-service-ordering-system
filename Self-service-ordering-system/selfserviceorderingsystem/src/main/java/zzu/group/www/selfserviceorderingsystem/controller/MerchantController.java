package zzu.group.www.selfserviceorderingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zzu.group.www.selfserviceorderingsystem.javabean.Order;
import zzu.group.www.selfserviceorderingsystem.javabean.OrderItem;
import zzu.group.www.selfserviceorderingsystem.javabean.Product;
import zzu.group.www.selfserviceorderingsystem.service.OrderService;
import zzu.group.www.selfserviceorderingsystem.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// MerchantController.java
@RestController
@RequestMapping("/api/merchant")
public class MerchantController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    // 获取所有商品
    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", products);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取商品列表失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // 根据ID获取单个商品
    @GetMapping("/products/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("data", product);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "商品不存在");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取商品信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // 添加商品
    @PostMapping("/products")
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody Product product) {
        try {
            productService.addProduct(product);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "商品添加成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "商品添加失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // 更新商品信息
    @PutMapping("/products/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            product.setId(id);
            productService.updateProduct(product);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "商品更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "商品更新失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // 上架/下架商品
    @PutMapping("/products/{id}/status")
    public ResponseEntity<Map<String, Object>> updateProductStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            productService.updateProductStatus(id, status);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "商品状态更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "商品状态更新失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // 删除商品
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "商品删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "商品删除失败: " + e.getMessage());
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

    // 获取订单详情
    @GetMapping("/orders/{id}")
    public ResponseEntity<Map<String, Object>> getOrderDetails(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            List<OrderItem> items = orderService.getOrderItems(id);

            Map<String, Object> result = new HashMap<>();
            result.put("order", order);
            result.put("items", items);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取订单详情失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // 更新订单状态
    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            orderService.updateOrderStatus(id, status);
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
}

