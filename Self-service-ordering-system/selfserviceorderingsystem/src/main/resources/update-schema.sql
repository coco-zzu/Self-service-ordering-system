-- Add points column to customer table
-- Run this script manually against your database to add the points column

-- Check if points column exists, and add it if it doesn't
ALTER TABLE customer ADD COLUMN IF NOT EXISTS points INT DEFAULT 0;

-- For older MySQL versions that don't support IF NOT EXISTS, you can use this alternative approach:
-- ALTER TABLE customer ADD COLUMN points INT DEFAULT 0;
-- Note: The above command will fail if the column already exists, which is expected behavior
-- in environments where the column was created with the initial schema.