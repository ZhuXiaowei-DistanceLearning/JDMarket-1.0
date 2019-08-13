package com.zxw.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by zxw on 2019/8/5.
 */
@Entity
public class Focus {
    private int id;
    private Integer goodsId;
    private Integer userId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "goods_id")
    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Focus focus = (Focus) o;
        return id == focus.id &&
                Objects.equals(goodsId, focus.goodsId) &&
                Objects.equals(userId, focus.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, goodsId, userId);
    }
}
