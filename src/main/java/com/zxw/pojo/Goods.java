package com.zxw.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by zxw on 2019/8/5.
 */
@Entity
public class Goods {
    private int id;
    private Integer catelogId;
    private Integer userId;
    private String name;
    private Double price;
    private Double realPrice;
    private String startTime;
    private String polishTime;
    private String endTime;
    private String describle;
    private Integer status;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "catelog_id")
    public Integer getCatelogId() {
        return catelogId;
    }

    public void setCatelogId(Integer catelogId) {
        this.catelogId = catelogId;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "real_price")
    public Double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Double realPrice) {
        this.realPrice = realPrice;
    }

    @Basic
    @Column(name = "start_time")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "polish_time")
    public String getPolishTime() {
        return polishTime;
    }

    public void setPolishTime(String polishTime) {
        this.polishTime = polishTime;
    }

    @Basic
    @Column(name = "end_time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "describle")
    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return id == goods.id &&
                Objects.equals(catelogId, goods.catelogId) &&
                Objects.equals(userId, goods.userId) &&
                Objects.equals(name, goods.name) &&
                Objects.equals(price, goods.price) &&
                Objects.equals(realPrice, goods.realPrice) &&
                Objects.equals(startTime, goods.startTime) &&
                Objects.equals(polishTime, goods.polishTime) &&
                Objects.equals(endTime, goods.endTime) &&
                Objects.equals(describle, goods.describle) &&
                Objects.equals(status, goods.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, catelogId, userId, name, price, realPrice, startTime, polishTime, endTime, describle, status);
    }
}
