package zzu.group.www.selfserviceorderingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zzu.group.www.selfserviceorderingsystem.service.NotificationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerNotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    // Get notifications for a user
    @GetMapping("/notifications")
    public ResponseEntity<Map<String, Object>> getNotifications(@RequestParam Long userId) {
        try {
            List<String> notifications = notificationService.getAndClearNotifications(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", notifications);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取通知失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}