package cn.jianwoo.eshop.common.utils;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Data
public class ItemCatLayuiTree implements Serializable {
    private  String name;
    private  Long id;
    private  boolean spread;
    private List<ItemCatLayuiTree> children;

}
