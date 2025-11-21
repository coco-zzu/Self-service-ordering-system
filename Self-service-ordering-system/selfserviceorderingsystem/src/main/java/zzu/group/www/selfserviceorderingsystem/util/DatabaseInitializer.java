package zzu.group.www.selfserviceorderingsystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Component
public class DatabaseInitializer {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        try {
            // Try to add points column if it doesn't exist
            addPointsColumnIfNotExists();
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
        }
    }
    
    private void addPointsColumnIfNotExists() {
        try {
            // Check if points column exists by trying to select from it
            jdbcTemplate.queryForObject("SELECT points FROM customer LIMIT 1", Integer.class);
            System.out.println("Points column already exists in customer table");
        } catch (Exception e) {
            // Column doesn't exist, try to add it
            try {
                jdbcTemplate.execute("ALTER TABLE customer ADD COLUMN points INT DEFAULT 0");
                System.out.println("Successfully added points column to customer table");
            } catch (Exception ex) {
                System.err.println("Failed to add points column: " + ex.getMessage());
            }
        }
    }
}