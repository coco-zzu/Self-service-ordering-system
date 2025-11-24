package zzu.group.www.selfserviceorderingsystem.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.ArrayList;

@Service
public class NotificationService {
    
    // Store notifications for each user
    private final ConcurrentHashMap<Long, CopyOnWriteArrayList<String>> userNotifications = new ConcurrentHashMap<>();
    
    // Add a notification for a user
    public void addNotification(Long userId, String message) {
        userNotifications.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(message);
    }
    
    // Get and clear notifications for a user
    public List<String> getAndClearNotifications(Long userId) {
        CopyOnWriteArrayList<String> notifications = userNotifications.get(userId);
        if (notifications != null) {
            List<String> result = new ArrayList<>(notifications);
            notifications.clear();
            return result;
        }
        return new ArrayList<>();
    }
    
    // Check if user has notifications
    public boolean hasNotifications(Long userId) {
        CopyOnWriteArrayList<String> notifications = userNotifications.get(userId);
        return notifications != null && !notifications.isEmpty();
    }
}