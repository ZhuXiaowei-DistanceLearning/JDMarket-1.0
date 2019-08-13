package com.zxw.service;

import com.zxw.mapper.GoodsMapper;
import com.zxw.mapper.OrdersMapper;
import com.zxw.pojo.Goods;
import com.zxw.pojo.Orders;
import com.zxw.vo.PageResult;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxw on 2019/8/5.
 */
@Service
@Transactional
public class OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    public List<Orders> getOrdersByUserId(int id) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Orders.class);
        detachedCriteria.add(Restrictions.eq("userId", id));
        List<Orders> list = ordersMapper.findByCriteria(detachedCriteria);
        return list;
    }

    public List<Orders> getOrdersByUserIdAndGoods(int id) {
        List<Orders> ordersList = new ArrayList<>();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Goods.class);
        detachedCriteria.add(Restrictions.eq("userId", id));
        List<Goods> list = goodsMapper.findByCriteria(detachedCriteria);
        if (list != null && list.size() >= 1) {
            for (Goods goods : list) {
                DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(Orders.class);
                detachedCriteria2.add(Restrictions.eq("goodsId", goods.getId()));
                List<Orders> orders = ordersMapper.findByCriteria(detachedCriteria2);
                if (orders != null && orders.size() >= 1) {
                    ordersList.add(orders.get(0));
                }
            }
        }
        return ordersList;
    }

    public void addOrders(Orders orders) {
        ordersMapper.saveOrUpdate(orders);
    }

    public void updateDeliverInfo(int goodsId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Orders.class);
        detachedCriteria.add(Restrictions.eq("orderNum", Long.valueOf(goodsId)));
        Orders orders = ordersMapper.findByCriteria(detachedCriteria).get(0);
        Integer state = orders.getOrderState();
        // 1.未发货 2.发货 3.已完成
        if (state == 1) {
            orders.setOrderState(2);
            ordersMapper.update(orders);
        } else if (state == 2) {
            orders.setOrderState(3);
            ordersMapper.update(orders);
        }
    }

    public PageResult findAll(Integer page, Integer rows, String o, String o1, String o2) {
        List<Orders> all = ordersMapper.findAll(page, rows, o, o1, o2);
        long count = ordersMapper.count();
        return new PageResult(count, all);
    }
}
