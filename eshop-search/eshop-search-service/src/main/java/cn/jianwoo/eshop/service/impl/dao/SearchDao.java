package cn.jianwoo.eshop.service.impl.dao;

import cn.jianwoo.eshop.common.response.SearchItem;
import cn.jianwoo.eshop.common.response.SearchResult;
import cn.jianwoo.eshop.manage.entity.Item;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {

    @Autowired
    private SolrClient solrClient;

    public SearchResult search(SolrQuery query) throws Exception {
        //根据查询条件查询索引库
        QueryResponse queryResponse = solrClient.query(query);
        //取查询结果总记录数
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        long numFound = solrDocumentList.getNumFound();
        //创建一个返回结果对象
        SearchResult result = new SearchResult();
        result.setRecourdCount((int) numFound);
        //创建一个商品列表对象
        List<Item> itemList = new ArrayList<>();
        //取商品列表
        //取高亮后的结果
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (SolrDocument solrDocument : solrDocumentList) {
            //取商品信息
            Item searchItem = new Item();
            searchItem.setId((Long) solrDocument.get("id"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setPrice((Long) solrDocument.get("item_price"));
            searchItem.setSellPoint((String) solrDocument.get("item_sell_point"));
            //取高亮结果
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle = "";
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) solrDocument.get("item_title");
            }
            searchItem.setTitle(itemTitle);
            //添加到商品列表
            itemList.add(searchItem);
        }
        //把列表添加到返回结果对象中
        result.setItemList(itemList);
        return result;
    }

}
