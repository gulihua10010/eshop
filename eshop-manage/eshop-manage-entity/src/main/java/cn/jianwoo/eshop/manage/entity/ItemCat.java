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
public class ItemCat  implements Serializable {
    private Long id;

    private Long parentId;

    private String name;
    private String pic;
    private Integer status;

    private Integer sortOrder;

//    private Integer isParent;

    private Timestamp created;
    private List<ItemCat> itemCats;
    private  List<Item> items;

//    private Timestamp updated;
}
