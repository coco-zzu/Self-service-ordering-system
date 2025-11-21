-- Add points column to customer table
-- Run this script manually against your database to add the points column

ALTER TABLE customer ADD COLUMN points INT DEFAULT 0;