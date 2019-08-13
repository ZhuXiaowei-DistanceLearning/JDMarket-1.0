package com.zxw.controller;

import com.zxw.controller.base.BaseController;
import com.zxw.pojo.Goods;
import com.zxw.pojo.Orders;
import com.zxw.pojo.Purse;
import com.zxw.pojo.User;
import com.zxw.service.CatelogService;
import com.zxw.service.GoodsService;
import com.zxw.service.OrdersService;
import com.zxw.service.PurseService;
import com.zxw.vo.PageResult;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zxw on 2019/8/5.
 */
@Controller
public class OrdersController extends BaseController<Orders> {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private PurseService purseService;
    @Autowired
    private CatelogService catelogService;

    public String myOrders() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        List<Orders> ordersList1 = new ArrayList<>();
        List<Orders> ordersList2 = new ArrayList<>();
        // 查询已买到的宝贝
        ordersList1 = ordersService.getOrdersByUserId(user.getId());
        // 查询我卖的宝贝订单
        ordersList2 = ordersService.getOrdersByUserIdAndGoods(user.getId());
        Purse purse = purseService.queryByUserId(user.getId());
        ServletActionContext.getRequest().setAttribute("myPurse", purse);
        ServletActionContext.getRequest().setAttribute("orders", ordersList1);
        ServletActionContext.getRequest().setAttribute("ordersOfSell", ordersList2);
        return "myOrders";
    }

    public String addOrder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        Orders orders = getModel();
        orders.setUserId(user.getId());
        orders.setOrderState(1);
        orders.setOrderDate(sdf.format(new Date()));
        Goods goods = new Goods();
        // 更新商品状态，已被购买，下架
        Goods buyGoods = goodsService.buyGoods(orders.getGoodsId());
        // 更新商品目录下商品数量
        catelogService.updateCatelogByGoods(buyGoods.getCatelogId());
        // 创建订单信息
        ordersService.addOrders(orders);
        Double balance = orders.getOrderPrice();
        // 更新用户余额
        purseService.updatePurseOfDel(user.getId(), balance);
        return "addOrder";
    }

    public String deliver() {
        ordersService.updateDeliverInfo(getModel().getGoodsId());
        return "updateGoodsInfo";
    }

    public String ordersList() {
        PageResult list = ordersService.findAll(getiPage().getPage(), getiPage().getRows(), null, null, null);
        ServletActionContext.getRequest().getSession().setAttribute("ordersGrid", list);
        return "ordersList";
    }
}
