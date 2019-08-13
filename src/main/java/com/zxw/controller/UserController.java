package com.zxw.controller;

import com.zxw.controller.base.BaseController;
import com.zxw.pojo.*;
import com.zxw.service.*;
import com.zxw.util.MD5;
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
public class UserController extends BaseController<User> {
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PurseService purseService;
    @Autowired
    private FocusService focusService;

    public String accountCheck() {
        boolean flag;
        String username = getModel().getUsername();
        String phone = getModel().getPhone();
        User user = userService.AccountCheck(username, phone);
        if (user == null) {
            flag = true;
            writePageBean2Json(flag);
            return NONE;
        } else {
            flag = false;
            writePageBean2Json(flag);
            return NONE;
        }
    }

    public String register() {
        User user = getModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        user.setCreateAt(sdf.format(new Date()));
        user.setPower(100);
        user.setGoodsNum(0);
        user.setStatus((byte) 1);
        user.setPassword(MD5.md5(user.getPassword()));
        userService.register(user);
        purseService.initPurse(user.getId());
        return "success";
    }

    public String login() {
        User user = getModel();
        User u = userService.login(user);
        if (u.getStatus() == 1) {
            ServletActionContext.getRequest().getSession().setAttribute("cur_user", u);
        }
        return u == null ? NONE : "login";
    }

    /**
     * 查找所有发布的商品
     */
    public String queryBySellProduct() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        // 查找该用户发布的所有商品
        List<Goods> goodsList = goodsService.queryGoodsByUserId(user.getId());
        List<GoodsExtend> list = new ArrayList<>();
        // 查找该用户的商品的图片
        for (Goods goods : goodsList) {
            GoodsExtend goodsExtend = new GoodsExtend();
            List<Image> imageList = imageService.queryByImagesByGoodsPrimaryKey(goods.getId());
            goodsExtend.setImages(imageList);
            goodsExtend.setGoods(goods);
            list.add(goodsExtend);
        }
        // 获取用户购买的商品
        Purse purse = purseService.queryByUserId(user.getId());
        ServletActionContext.getRequest().setAttribute("goodsAndImage", list);
        ServletActionContext.getRequest().setAttribute("myPurse", purse);
        return "sellProductByUser";
    }

    /**
     * 购物车
     *
     * @return
     */
    public String cart() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        // 获取所有关注
        List<Focus> focusList = focusService.queryFocusByUserId(user.getId());
        // 存放商品和图片的信息
        List<GoodsExtend> goodsExtendList = new ArrayList<>();
        for (Focus focus : focusList) {
            GoodsExtend goodsExtend = new GoodsExtend();
            Goods goods = goodsService.queryGoodsByPrimaryKey(focus.getGoodsId());
            List<Image> imageList = imageService.queryByImagesByGoodsPrimaryKey(focus.getGoodsId());
            goodsExtend.setGoods(goods);
            goodsExtend.setImages(imageList);
            goodsExtendList.add(goodsExtend);
        }
        Purse purse = purseService.queryByUserId(user.getId());
        ServletActionContext.getRequest().setAttribute("goodsAndImage", goodsExtendList);
        ServletActionContext.getRequest().setAttribute("myPurse", purse);
        return "focus";
    }

    /**
     * 查询个人信息
     */
    public String basic() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        Purse purse = purseService.queryByUserId(user.getId());
        ServletActionContext.getRequest().setAttribute("myPurse", purse);
        ServletActionContext.getRequest().setAttribute("user", user);
        return "basic";
    }

    /**
     * 修改个人信息
     */
    public String updateUserInfo() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        user.setQq(getModel().getQq());
        user.setUsername(getModel().getUsername());
        userService.updateUserInfo(user);
        ServletActionContext.getRequest().setAttribute("cur_user", user);
        return "updateUserInfo";
    }


}
