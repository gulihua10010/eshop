package cn.jianwoo.eshop.manage.mapper;



import cn.jianwoo.eshop.manage.entity.Content;

import java.util.List;

public interface ContentMapper {
    List<Content> getContentListByCategoryId(Long categoryId);
    List<Content> getAllContentList();
    int insertContent(Content content);
    int updateContent(Content  content);

}
