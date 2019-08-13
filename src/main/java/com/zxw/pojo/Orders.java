package com.zxw.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by zxw on 2019/8/5.
 */
@Entity
public class Orders {
    private int id;
    private int userId;
    private int goodsId;
    private Long orderNum;
    private Double orderPrice;
    private Integer orderState;
    private String orderInformation;
    private String orderDate;

    private Goods goods;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//cascade级联状态，fetch是懒加载还是立即
   /* @JoinTable(
            name = "order_good",//中间表名称，如果没有这个表，程序启动后，自动在数据库创建
            //ForeignKey指定外键，none是没有；ConstraintMode.NO_CONSTRAINT是不创建
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT),
            //joinColumns 是主控方给当前类指定关联，name是当前类student的属性关联的中间表属性sid，
            // referencedColumnName 是当前类的属性student实体类的id
            joinColumns = {@JoinColumn(name = "oid", referencedColumnName = "id", nullable = false)},
            //inverseJoinColumns也就是被控方的属性，
            // referencedColumnName后面写Teacher的属性id，JoinColumn写中间表的属性名称
            inverseJoinColumns = {@JoinColumn(name = "fid", referencedColumnName = "id", nullable = false
            )})*/
    @JoinColumn(name = "goods_id", insertable = false, updatable = false)
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "goods_id")
    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    @Basic
    @Column(name = "order_num")
    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    @Basic
    @Column(name = "order_price")
    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Basic
    @Column(name = "order_state")
    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    @Basic
    @Column(name = "order_information")
    public String getOrderInformation() {
        return orderInformation;
    }

    public void setOrderInformation(String orderInformation) {
        this.orderInformation = orderInformation;
    }

    @Basic
    @Column(name = "order_date")
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
