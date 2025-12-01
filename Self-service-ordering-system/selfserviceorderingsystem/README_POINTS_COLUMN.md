# Fixing "Failed to obtain JDBC Connection" Error When Adding Points Column

## Problem Description

When trying to add the points column to the customer table, you may encounter the error "Failed to obtain JDBC Connection". This typically happens because:

1. The database connection settings in [application.properties](file:///C:/Users/asus/IdeaProjects/Self-service-ordering-system/Self-service-ordering-system/selfserviceorderingsystem/src/main/resources/application.properties) are incorrect
2. The MySQL server is not running
3. The points column is missing from the database but expected by the application code
4. MySQL version compatibility issues with certain SQL syntax
5. Firewall or network connectivity issues

## Solution Steps

### 1. Verify Database Connection Settings

First, check your database connection settings in [application.properties](file:///C:/Users/asus/IdeaProjects/Self-service-ordering-system/Self-service-ordering-system/selfserviceorderingsystem/src/main/resources/application.properties):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/selfserviceorderingsystem?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Make sure:
- The MySQL server is running
- The database `selfserviceorderingsystem` exists
- The username and password are correct
- The port (3306 by default) is correct

### 2. Test Database Connectivity

Before attempting to add the column, test your database connectivity:

#### Using Command Line:
```bash
mysql -u root -p -e "SELECT 1" selfserviceorderingsystem
```

If this fails, the issue is with your database connection, not specifically with adding the points column.

### 3. Add the Points Column

We've provided multiple ways to add the points column:

#### Option A: Using the Updated Schema (Recommended)

The [schema.sql](file:///C:/Users/asus/IdeaProjects/Self-service-ordering-system/Self-service-ordering-system/selfserviceorderingsystem/src/main/resources/schema.sql) file now includes a safe way to add the points column:

```sql
ALTER TABLE customer ADD COLUMN IF NOT EXISTS points INT DEFAULT 0;
```

This command will only add the column if it doesn't already exist.

#### Option B: Manual Execution of Update Script

You can manually execute the [update-schema.sql](file:///C:/Users/asus/IdeaProjects/Self-service-ordering-system/Self-service-ordering-system/selfserviceorderingsystem/src/main/resources/update-schema.sql) file:

```sql
-- For newer MySQL versions
ALTER TABLE customer ADD COLUMN IF NOT EXISTS points INT DEFAULT 0;

-- For older MySQL versions (will fail if column exists, which is expected)
ALTER TABLE customer ADD COLUMN points INT DEFAULT 0;
```

#### Option C: Automatic Initialization

The application now includes a [DatabaseInitializer](file:///C:/Users/asus/IdeaProjects/Self-service-ordering-system/Self-service-ordering-system/selfserviceorderingsystem/src/main/java/zzu/group/www/selfserviceorderingsystem/util/DatabaseInitializer.java) class that automatically tries to add the points column when the application starts.

### 4. Execute the SQL Commands

Connect to your MySQL database and execute one of the following commands:

#### Using MySQL Command Line:
```bash
mysql -u root -p selfserviceorderingsystem < src/main/resources/update-schema.sql
```

#### Using MySQL Workbench or phpMyAdmin:
1. Open your database client
2. Connect to the `selfserviceorderingsystem` database
3. Execute the ALTER TABLE command:
   ```sql
   ALTER TABLE customer ADD COLUMN IF NOT EXISTS points INT DEFAULT 0;
   ```

### 5. Restart Your Application

After adding the column, restart your Spring Boot application to ensure the changes take effect.

## Compatibility Notes

- The `IF NOT EXISTS` clause is supported in MySQL 5.7.5 and later
- For older MySQL versions, the command will fail if the column already exists, which is expected behavior
- The application code already handles cases where the column might not exist through try-catch blocks

## Troubleshooting

If you continue to experience the "Failed to obtain JDBC Connection" error:

1. **Verify MySQL Server Status:** Ensure MySQL is running
   ```bash
   # On Windows
   net start mysql
   
   # On Linux/Mac
   sudo systemctl status mysql
   ```

2. **Test Connection with MySQL Client:**
   ```bash
   mysql -h localhost -P 3306 -u root -p
   ```

3. **Check Firewall Settings:** Ensure port 3306 is open

4. **Verify Database Exists:**
   ```sql
   SHOW DATABASES;
   USE selfserviceorderingsystem;
   ```

5. **Check Connection Pool Settings:** The updated [application.properties](file:///C:/Users/asus/IdeaProjects/Self-service-ordering-system/Self-service-ordering-system/selfserviceorderingsystem/src/main/resources/application.properties) includes enhanced connection pool settings.

## Verification

To verify that the points column has been added successfully:

1. Connect to your MySQL database
2. Run the following query:
   ```sql
   DESCRIBE customer;
   ```
3. You should see a `points` column in the output

If everything is set up correctly, the "Failed to obtain JDBC Connection" error should be resolved.