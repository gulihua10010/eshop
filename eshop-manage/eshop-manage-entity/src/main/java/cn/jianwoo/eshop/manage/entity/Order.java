package cn.jianwoo.eshop.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Order implements Serializable {
    private  Long id;
    private String orderId;

    private Double payment;
    private String token;

    private  Long uid;

    private Integer paymentType;

    private Double postFee;

    private Integer status;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Timestamp paymentTime;

    private Timestamp consignTime;

    private Timestamp endTime;

    private Timestamp closeTime;

    private String shoppingName;

    private String shoppingCode;


    private String buyerMessage;

    private String buyerNick;

    private Integer buyerRate;

    private  Long addressId;

    private List<OrderItem>  orderItems;

    private  User user;
}
