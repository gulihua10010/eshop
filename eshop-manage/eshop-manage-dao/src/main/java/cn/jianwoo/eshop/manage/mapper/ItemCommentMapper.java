package cn.jianwoo.eshop.manage.mapper;

import cn.jianwoo.eshop.manage.entity.ItemComment;
import cn.jianwoo.eshop.manage.entity.ItemImages;

import java.util.List;
import java.util.Map;

public interface ItemCommentMapper {

    int insert(ItemComment itemComment);
    int update(ItemComment itemComment);
    int delete(ItemComment itemComment);
    List<ItemComment> getItemCommentListByMap(Map<String, Object> params) ;
    ItemComment getItemCommentById(Long id) ;
    List<ItemComment> getItemCommentByIid(Long Iid) ;
}