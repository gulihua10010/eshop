package cn.jianwoo.eshop.common.response;

import cn.jianwoo.eshop.manage.entity.Item;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    private List<Item> itemList;
    private int totalPages;
    private int recourdCount;

    public List<Item> getItemList() {
        return itemList;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "itemList=" + itemList +
                ", totalPages=" + totalPages +
                ", recourdCount=" + recourdCount +
                '}';
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRecourdCount() {
        return recourdCount;
    }

    public void setRecourdCount(int recourdCount) {
        this.recourdCount = recourdCount;
    }
}
