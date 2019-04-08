package cn.jianwoo.eshop.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemComment  implements Serializable {
    private Long id;
    private  Long itemId;
    private  Long pid;
    private  Integer star;
    private  String content;
    private Timestamp created;
    private Timestamp updated;

}
