package cn.jianwoo.eshop.adminweb;

import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.item.api.ItemService;
import cn.jianwoo.eshop.manage.entity.*;
import com.github.pagehelper.PageInfo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
//    @Autowired
//    AddressMapper addressMapper;
//    @Autowired
//    ContentMapper contentMapper;
//    @Autowired
//    ItemMapper itemMapper;
//    @Autowired
//    ItemCatMapper itemCatMapper;
//    @Autowired
//    OrderMapper orderMapper;
//    @Autowired
//    OrderItemMapper orderItemMapper;
//    @Autowired
//    UserMapper userMapper;

//
    @Autowired
    ItemService itemService;
//    @org.junit.Test
//    public void address(){
//        Address address=new Address(null,"dd",1,"dddd","dddd","dd","dd","dd",
//                "dd","dd",new Date(),new Date());
//        addressMapper.insert(address);
//Address address1=new Address();
//address1.setId(37L);
////address1.setReceiverName("ccc");
////addressMapper.update(address1);
////        List<Address> addresses=addressMapper.getAddresslist();
//        Address address2=addressMapper.getaddressbyid(37L);
//        System.out.println(address2);
//    }
//    @org.junit.Test
//    public void content(){
//        Content content=new Content(null,1L,"ddd","ddd","ddd","ddd","ddd","ddd",new Date(),new Date(),"ddd");
////        contentMapper.insertContent(content);
//Content content1=new Content();
//content1.setCategoryId(22L);
//content1.setId(1L);
////contentMapper.updateContent(content1);
//List<Content> contents=contentMapper.getContentListByCategoryId(22L);
//
//
//    }
//    @org.junit.Test
//    public  void  item(){
//
////        Item item=new Item(null,"dd","d",1L,1,"dd","dd",1L,1,new Date(),new Date(),"dd","d",1L,1L);
////        itemMapper.insert(item);
////        Item item1=new Item();
////        item1.setTitle("cc");
////        item1.setId(1L);
////        itemMapper.update(item1);
//        List<Item> items=itemMapper.getItemList();
//        Item item2=itemMapper.getById(1L);
////        System.out.println();
//
//    }
//    @org.junit.Test
//    public void itemcat(){
//        ItemCat itemCat=new ItemCat(null,1L,"dd",1,1,1,new Date(),new Date());
//itemCatMapper.insert(itemCat);
////        ItemCat itemCat1=new ItemCat();
////        itemCat1.setId(1L);
////        itemCat1.setParentId(22L);
////        itemCatMapper.update(itemCat1);
//    }
//    @org.junit.Test
//    public void order(){
//        Order o=new Order(null,"dd","dd",1,1,"dd",1,new Date(),new Date(),new Date(),new Date(),new Date(),new Date(),"ddd","ddd",1L,"d","d",1,1);
////        orderMapper.insert(o);
//        Order order=new Order();
////        order.setId(1L);
////        order.setOrderId("222");
////        orderMapper.update(order);
//        order=orderMapper.getOrderbyId(1L);
//        order=orderMapper.getOrderbyoId("222");
//    }
//
//    @org.junit.Test
//    public void orderiem(){
//        OrderItem orderItem=new OrderItem(null,"ddd","ddd",1,"ddd",1L,1L,"D");
////        orderItemMapper.insert(orderItem);
//        OrderItem orderItem1=new OrderItem();
////        orderItem1.setId(1L);
////        orderItem1.setItemId("ddd");
////        orderItemMapper.update(orderItem1);
//
//orderItem1=orderItemMapper.getOrderItemById(1L);
//    }
//    @org.junit.Test
//    public void user(){
//        User user=new User(null,"dd","dd","dd","dd",new Date(),new Date(),"d","d",null,null,null,1);
//        userMapper.insert(user);
//        User u=new User();
//        u.setId(1L);
//        u.setEmail("d");
//        userMapper.update(u);
//userMapper.getById(1L);
//        Map<String,Object> map=new HashMap<>();
//        map.put("username","ddd");
//        map.put("password","ddd");
//userMapper.getByNameOrPwd(map);
//
//    }
//
    @org.junit.Test
    public void ttt(){
//        System.out.println(itemService.getById(155267110788639L));
//        System.out.println(itemService.getItemListByPage(1,10,"锈钢加厚拉丝"));
//        LayuiResult pageInfo=itemService.getItemListByPage(1,10);
//        System.out.println(pageInfo);

        int i=1;
        i=i++;
        System.out.println(i);
    }
}
