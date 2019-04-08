package cn.jianwoo.eshop.service.impl.impl;

import cn.jianwoo.eshop.api.SearchService;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.common.response.SearchResult;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.mapper.ItemMapper;
import cn.jianwoo.eshop.service.impl.dao.SearchDao;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
@Autowired
    ItemMapper itemMapper;
    @Autowired
    private SearchDao searchDao;

    @Value("${DEFAULT_FIELD}")
    private String DEFAULT_FIELD;

    @Override
    public SearchResult search(String keyWord, int page, int rows) throws Exception {
        //创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(keyWord);
        //设置分页条件
        query.setStart((page - 1) * rows);
        //设置rows
        query.setRows(rows);
        //设置默认搜索域
        query.set("df", DEFAULT_FIELD);
        //设置高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //执行查询
        SearchResult searchResult = searchDao.search(query);
        //计算总页数
        int recourdCount = searchResult.getRecourdCount();
        int pages = recourdCount / rows;
        if (recourdCount % rows > 0) {
            pages++;
        }
        //设置到返回结果
        searchResult.setTotalPages(pages);
        return searchResult;
    }

    @Override
    public List<Item> search1(String kw, int page, int rows)  {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        List<Item> list=null;
        list= itemMapper.search(kw);
        PageInfo<Item> pageInfo = new PageInfo<>(list);
      return    list;

    }

}
