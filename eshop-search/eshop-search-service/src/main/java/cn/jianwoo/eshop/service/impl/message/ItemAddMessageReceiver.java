package cn.jianwoo.eshop.service.impl.message;

import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ItemAddMessageReceiver {

    @Autowired
    private ItemMapper searchItemMapper;
    @Autowired
    private SolrClient solrClient;

    @JmsListener(destination = "itemAddTopic", containerFactory = "jmsTopicListenerContainerFactory")
    public void itemAddReceiver(Long msg) {
        try {
            // 0、等待1s让e3-manager-service提交完事务，商品添加成功
            Thread.sleep(1000);
            // 1、根据商品id查询商品信息
            Item searchItem = searchItemMapper.getById(msg);
            // 2、创建一SolrInputDocument对象。
            SolrInputDocument document = new SolrInputDocument();
            // 3、使用SolrServer对象写入索引库。
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSellPoint());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            // 5、向索引库中添加文档。
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
