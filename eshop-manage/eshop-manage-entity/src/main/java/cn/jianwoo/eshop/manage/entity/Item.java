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
public class Item  implements Serializable {
    private Long id;

    private String title;
    private Long randomId;

    private String sellPoint;
    private Long price;
    private Double price1;
    private Integer num;
    private Integer selnum;
    private String barcode;
    private String image;
    private Long cid;
    private Integer status;
    private Integer recommended;
    private Timestamp created;
    private Timestamp updated;
    private String  itemDesc;
    private String  productNo;
    private  String place;
    private  Long monSales;

    private  ItemCat itemCat;

    private List<ItemImages> itemImages;
    private  List<ItemProv> itemProvs;
}
