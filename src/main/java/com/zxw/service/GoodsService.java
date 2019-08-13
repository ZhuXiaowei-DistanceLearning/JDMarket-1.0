package com.zxw.service;

import com.zxw.mapper.GoodsMapper;
import com.zxw.pojo.Goods;
import com.zxw.vo.PageResult;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zxw on 2019/8/5.
 */
@Service
@Transactional
public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    public List<Goods> queryByGoodsOrderByDate(Integer page, Integer rows, String sortBy, String desc, String search) {
        List<Goods> list = goodsMapper.findAll(page, rows, sortBy, desc, search);
        list.stream().filter(e -> e.getStatus() == 1);
        return list;
    }

    public PageResult findAll(Integer page, Integer rows, String sortBy, String desc, String search) {
        List<Goods> list = goodsMapper.findAll(page, rows, sortBy, desc, search);
        long count = goodsMapper.count();
        return new PageResult(count, list);
    }

    public List<Goods> queryByGoodsByCatelogOrderByDate(int rows, int catelogId) {
        DetachedCriteria forClass = DetachedCriteria.forClass(Goods.class);
        forClass.add(Restrictions.eq("catelogId", catelogId));
        forClass.add(Restrictions.eq("status", 1));
        forClass.addOrder(Order.desc("polishTime"));
        List<Goods> list = goodsMapper.findByCriteria(forClass, 0, rows);
        return list;
    }

    public List<Goods> queryGoodsByUserId(int id) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Goods.class);
        detachedCriteria.add(Restrictions.eq("userId", id));
        List<Goods> list = goodsMapper.findByCriteria(detachedCriteria);
        return list;
    }

    public Goods queryGoodsByPrimaryKey(Integer getGoodsId) {
        return goodsMapper.findById(getGoodsId);
    }

    public void addGoods(Goods goods, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        goods.setStartTime(sdf.format(new Date()));
        goodsMapper.saveOrUpdate(goods);
    }

    public void updateGoodsInfo(int id, int status) {
        // 0 下架,1 上架
        Goods goods = goodsMapper.findById(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (status == 1) {
            // 商品下架
            goods.setEndTime(sdf.format(new Date()));
            goods.setStatus(0);
        } else if (status == 0) {
            // 商品上架
            goods.setPolishTime(sdf.format(new Date()));
            goods.setStatus(1);
        }
        goodsMapper.update(goods);
    }

    public Goods buyGoods(int goods) {
        Goods id = goodsMapper.findById(goods);
        id.setStatus(0);
        goodsMapper.update(id);
        return id;
    }

    public void updateGoodsTime(int id) {
        Goods goods = goodsMapper.findById(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        goods.setPolishTime(sdf.format(new Date()));
        goodsMapper.update(goods);
    }

    public Goods queryGoodsById(int id) {
        return goodsMapper.findById(id);
    }
}
