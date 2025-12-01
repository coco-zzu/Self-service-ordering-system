-- 创建products表
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    status INT DEFAULT 1
);

-- 创建customer表
CREATE TABLE IF NOT EXISTS customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    points INT DEFAULT 0
);

-- 创建merchant表
CREATE TABLE IF NOT EXISTS merchant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

-- 创建orders表
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建order_item表
CREATE TABLE IF NOT EXISTS order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255) DEFAULT '',
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- 插入一些测试商品数据
INSERT INTO products (name, description, price, status) VALUES
('珍珠奶茶', '经典珍珠奶茶，香浓可口', 12.00, 1),
('红豆奶茶', '红豆奶茶，甜香软糯', 10.00, 1),
('绿茶', '清香绿茶，回味甘甜', 8.00, 1),
('咖啡', '浓郁咖啡，提神醒脑', 15.00, 1);

-- Try to add points column if it doesn't exist (for environments where this might be needed)
ALTER TABLE customer ADD COLUMN IF NOT EXISTS points INT DEFAULT 0;