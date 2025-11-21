package zzu.group.www.selfserviceorderingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/database")
public class DatabaseController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/add-points-column")
    public Map<String, Object> addPointsColumn() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Try to add points column to customer table
            jdbcTemplate.execute("ALTER TABLE customer ADD COLUMN points INT DEFAULT 0");
            response.put("success", true);
            response.put("message", "Points column added successfully");
        } catch (DataAccessException e) {
            // Check if it's because the column already exists
            try {
                // Try to select from the points column to see if it exists
                jdbcTemplate.queryForObject("SELECT points FROM customer LIMIT 1", Integer.class);
                response.put("success", true);
                response.put("message", "Points column already exists");
            } catch (DataAccessException ex) {
                response.put("success", false);
                response.put("message", "Error adding points column: " + e.getMessage());
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Unexpected error: " + e.getMessage());
        }
        
        return response;
    }
    
    @GetMapping("/init-database")
    public Map<String, Object> initializeDatabase() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Check if points column exists
            try {
                jdbcTemplate.queryForObject("SELECT points FROM customer LIMIT 1", Integer.class);
                response.put("success", true);
                response.put("message", "Database is already initialized with points column");
            } catch (DataAccessException e) {
                // Points column doesn't exist, add it
                return addPointsColumn();
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error checking database: " + e.getMessage());
        }
        
        return response;
    }
    
    @GetMapping("/check-points-column")
    public Map<String, Object> checkPointsColumn() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Try to select from the points column to see if it exists
            jdbcTemplate.queryForObject("SELECT points FROM customer LIMIT 1", Integer.class);
            response.put("success", true);
            response.put("message", "Points column exists");
            response.put("exists", true);
        } catch (DataAccessException e) {
            response.put("success", true);
            response.put("message", "Points column does not exist");
            response.put("exists", false);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error checking points column: " + e.getMessage());
        }
        
        return response;
    }
}