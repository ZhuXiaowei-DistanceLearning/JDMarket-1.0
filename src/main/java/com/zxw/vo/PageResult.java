package com.zxw.vo;

import java.util.List;

public class PageResult {
    private Long total;// 总条数
    private Integer totalPage;// 总页数
    private List items;// 当前页数据

    public PageResult() {
    }

    public PageResult(Long total, List items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}