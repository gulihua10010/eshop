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
public class WebConfig implements Serializable {
    private  Integer id;
    private  String key;
    private  String value;
    private  Integer status;

}
