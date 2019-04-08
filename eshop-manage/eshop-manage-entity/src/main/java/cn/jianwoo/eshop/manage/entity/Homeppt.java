package cn.jianwoo.eshop.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Homeppt  implements Serializable {
    private  Integer id;
    private  String name;
    private  String pic;
    private  Integer status;
    private String url;

}
