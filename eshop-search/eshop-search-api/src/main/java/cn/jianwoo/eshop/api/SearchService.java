package cn.jianwoo.eshop.api;


import cn.jianwoo.eshop.common.response.SearchResult;
import cn.jianwoo.eshop.manage.entity.Item;

import java.util.List;

public interface SearchService {
    SearchResult search(String keyWord, int page, int rows) throws Exception;
    List<Item> search1(String keyWord, int page, int rows);
}
