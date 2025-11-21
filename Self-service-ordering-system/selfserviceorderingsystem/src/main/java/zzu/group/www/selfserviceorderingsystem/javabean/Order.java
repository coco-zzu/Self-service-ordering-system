package zzu.group.www.selfserviceorderingsystem.javabean;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private Integer status; // 0: 未支付, 1: 已支付, 2: 已完成
    private Date createTime;
    private List<OrderItem> items;
}