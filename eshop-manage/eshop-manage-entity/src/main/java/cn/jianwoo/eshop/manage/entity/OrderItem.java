package cn.jianwoo.eshop.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class OrderItem implements Serializable {
    private Long id;

    private Long itemId;

    private String orderId;

    private Integer num;

    private String title;

    private Long price;

    private Long totalFee;

    private String picPath;
}
