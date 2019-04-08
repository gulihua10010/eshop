package cn.jianwoo.eshop.item.service;

import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.common.utils.HttpClientUtil;
import cn.jianwoo.eshop.common.utils.IDUtils;
import cn.jianwoo.eshop.item.api.ItemService;
import cn.jianwoo.eshop.manage.entity.ItemCat;
import cn.jianwoo.eshop.manage.entity.ItemImages;
import cn.jianwoo.eshop.manage.entity.ItemProv;
import cn.jianwoo.eshop.manage.mapper.ItemCatMapper;
import cn.jianwoo.eshop.manage.mapper.ItemImagesMapper;
import cn.jianwoo.eshop.manage.mapper.ItemMapper;
import cn.jianwoo.eshop.manage.mapper.ItemProvMapper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Item {
@Autowired
    ItemCatMapper itemCatMapper;
@Autowired
ItemImagesMapper  itemImagesMapper;
@Autowired
    ItemProvMapper itemProvMapper;
@Autowired
    ItemMapper itemMapper;
//@Autowired
//    ItemService itemService;
    @Test
    public void add(){

String path="D:\\IT_java\\IdeaProjects\\eshop\\eshop-item\\eshop-item-service\\src\\main\\resources\\item.json";
StringBuffer stringBuffer=new StringBuffer();
        File file=new File(path);
        if (!file.exists()){
            System.out.println("no");

        }
        JSONArray jsonArray=null;
        List<cn.jianwoo.eshop.manage.entity.Item> items=new ArrayList<>();
        try {
            String input =FileUtils.readFileToString(file,"UTF-8");
            JSONObject jsonObject = JSONObject.fromObject(input);
            if (jsonObject != null) {
                //取出按钮权限的数据 b\n
                jsonArray = jsonObject.getJSONArray("list");
                for(int i = 0; i < jsonArray.size(); i++){
                  String   name = jsonArray.getJSONObject(i).get("商品标题").toString();
                 String img     = jsonArray.getJSONObject(i).get("_id").toString();
                 String url     = jsonArray.getJSONObject(i).get("商品链接").toString();
                    cn.jianwoo.eshop.manage.entity.Item item=new cn.jianwoo.eshop.manage.entity.Item();
                      Random random=new Random();
                    item.setId(IDUtils.genItemId());
                    item.setTitle(name);
                    item.setStatus(1);
                    item.setCreated(new Timestamp(new Date().getTime()));
                    item.setUpdated(new Timestamp(new Date().getTime()));
                    if (name.contains("智能锁")||name.contains("溶胶棒") ){
                        item.setCid(121L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("马桶盖")||name.contains("水槽双槽")||name.contains("卧室")||name.contains("浴室")){
                        item.setCid(118L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    } else if (name.contains("电钻")){
                        item.setCid(120L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("厨")||name.contains("纸")){
                        item.setCid(48L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("摄像头")){
                        item.setCid(350L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("水果")){
                        item.setCid(61L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    } else if (name.contains("饮料")){
                        item.setCid(81L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    } else if (name.contains("洗浴")){
                        item.setCid(164L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("零食")){
                        item.setCid(190L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("粮油")){
                        item.setCid(220L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("滋补")){
                        item.setCid(140L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("电磁炉")||name.contains("锅")){
                        item.setCid(127L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("电热毯")){
                        item.setCid(114L);
                    }else if (name.contains("书籍")){
                        item.setCid(346L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("手机")||name.contains("主机")||name.contains("电脑")){
                        item.setCid(351L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("充值")){
                        item.setCid(100L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("蔬菜")){
                        item.setCid(62L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("家具")){
                        item.setCid(281L);
                    }else if (name.contains("奶粉")){
                        item.setCid(82L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("方便面")){
                        item.setCid(232L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("润唇膏")){
                        item.setCid(180L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("遥控器")){
                        item.setCid(129L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("电风扇")){
                        item.setCid(362L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("薯片")){
                        item.setCid(204L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("药")||name.contains("感冒")){
                        item.setCid(137L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }else if (name.contains("咖啡")){
                        item.setCid(79L);
                        item.setRandomId( Math.abs(random.nextLong())%8999999999L+1000000000L);

                    }  else{
                        item.setCid( Math.abs(random.nextLong())%314+47);
                        item.setRandomId(random.nextLong()%100000000);

                    }
                    String desc="<h3>"+name+"</h3><br/><img src=\""+img+"\"/>";
                   item.setImage(img);
                   item.setItemDesc(desc);
                   item.setNum(Math.abs(random.nextInt()));
                   item.setPlace("江苏省-南京市-江宁区");
                   item.setMonSales(Math.abs(random.nextLong())%100000);
//                    item.setNum(random.nextInt());
                    item.setPrice(Math.abs(random.nextLong())%1000000);
                    item.setSellPoint("卖点"+name);
                    item.setBarcode(url);
                    item.setProductNo(String.valueOf(Math.abs(random.nextLong())%8999999999L+1000000000L));
//                Long cid=itemCats.get(cccid).getId();

                    boolean f=false;
                    for (cn.jianwoo.eshop.manage.entity.Item item1:items){
                        if (item1.getBarcode().equals(item.getBarcode())){
//                            ItemImages itemImages=new ItemImages(null,item1.getId(),item.getImage());
//                            itemImagesMapper.insert(itemImages);
                            f=true;break;
                        }
                    }
                    if (!f){
                        items.add(item);
                        ItemImages itemImages1=new ItemImages(null,item.getId(),item.getImage());
                        ItemImages itemImages2=new ItemImages(null,item.getId(),item.getImage());
                        ItemImages itemImages3=new ItemImages(null,item.getId(),item.getImage());
                        ItemImages itemImages4=new ItemImages(null,item.getId(),item.getImage());
                        ItemImages itemImages5=new ItemImages(null,item.getId(),item.getImage());
                                                    itemImagesMapper.insert(itemImages1);
                                                    itemImagesMapper.insert(itemImages2);
                                                    itemImagesMapper.insert(itemImages3);
                                                    itemImagesMapper.insert(itemImages4);
                                                    itemImagesMapper.insert(itemImages5);

                        ItemProv itemProv=new ItemProv(null,item.getId(),"颜色","红色");
                        ItemProv itemProv1=new ItemProv(null,item.getId(),"尺寸","100*100英寸");
                        ItemProv itemProv2=new ItemProv(null,item.getId(),"重量","2kg");
                        ItemProv itemProv3=new ItemProv(null,item.getId(),"材料","塑料");
                        itemProvMapper.insert(itemProv);
                        itemProvMapper.insert(itemProv1);
                        itemProvMapper.insert(itemProv2);
                        itemProvMapper.insert(itemProv3);
                        System.out.println(item);
                        itemMapper.insert(item);
                    }

                    //新增到数据库
                    //查看已执行次数
                }

                System.out.println(items.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void ttt(){

//        LayuiResult pageInfo=itemService.getItemListByPage(0,10);
//        System.out.println(pageInfo);
    }
    @Test
    public void tttremovenourl(){
//   String res =     HttpClientUtil.doGet("https://img.alicdn.com/imgextra/i1/408131043/TB2MG0DaAWM.eBjSZFhXXbdWpXa_!!408131043.jpg",null);
//        System.out.println(res=="");

//        itm,
        int p=0;
      List<cn.jianwoo.eshop.manage.entity.Item> items=  itemMapper.getItemList();
      int o=0;
        for (cn.jianwoo.eshop.manage.entity.Item i:items            ) {
            String img=i.getImage();
            String res =     HttpClientUtil.doGet(img,null);
            if (res==""){
                itemMapper.delete(i);
                p++;
            }
            o++;
            System.out.println(o);
        }

        System.out.println(p);
//        LayuiResult pageInfo=itemService.getItemListByPage(0,10);
//        System.out.println(pageInfo);
    }
}
