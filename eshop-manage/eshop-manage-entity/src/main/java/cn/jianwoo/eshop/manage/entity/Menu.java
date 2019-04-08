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
public class Menu implements Serializable {
    private  Integer id;
    private  String name;
    private  Integer status;
    private  Integer order;
    private String url;
    private String target;
}
