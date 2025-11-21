package zzu.group.www.selfserviceorderingsystem.javabean;

import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer status; // 0: 下架, 1: 上架
}