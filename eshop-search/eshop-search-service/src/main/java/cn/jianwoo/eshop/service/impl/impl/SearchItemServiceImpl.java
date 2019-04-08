package cn.jianwoo.eshop.service.impl.impl;

import cn.jianwoo.eshop.api.SearchItemService;
import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.response.SearchItem;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.mapper.ItemMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
//    private CloudSolrClient solrClient;
    private SolrClient solrClient;

    @Override
    public EShopResult importItems() {
        try {
            //查询商品列表
            List<Item> itemList = itemMapper.getItemList();
            //导入索引库
            for (Item searchItem : itemList) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档中添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSellPoint());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getItemCat().getName());
                //写入索引库
                solrClient.add(document);
            }
            //提交
            solrClient.commit();
            //返回成功
            return EShopResult.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return EShopResult.build(500, "商品导入失败");
        }
    }
}
