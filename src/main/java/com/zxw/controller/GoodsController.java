package com.zxw.controller;

import com.zxw.controller.base.BaseController;
import com.zxw.pojo.*;
import com.zxw.service.*;
import com.zxw.vo.PageResult;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zxw on 2019/8/5.
 */
@Controller
public class GoodsController extends BaseController<Goods> {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CatelogService catelogService;
    @Autowired
    private UserService userService;
    @Autowired
    private PurseService purseService;
    @Autowired
    private CommentsService commentsService;

    private File myfile;
    private String myfileFileName;
    private String myfileContentType;
    private String imgUrl;
    private String search;

    public String homeGoods() {
        // 商品种类数量
        int rows = 7;
        // 每个种类显示商品数量
        List<Goods> goodsList = null;
        List<GoodsExtend> goodsAndImage = new ArrayList<>();
        // 获取最新发布的商品列表
        goodsList = goodsService.queryByGoodsOrderByDate(1, rows, "startTime", "", "");
        for (int i = 0; i < goodsList.size(); i++) {
            GoodsExtend goodsExtend = new GoodsExtend();
            Goods goods = goodsList.get(i);
            List<Image> images = imageService.queryByImagesByGoodsPrimaryKey(goods.getId());
            goodsExtend.setGoods(goods);
            goodsExtend.setImages(images);
            goodsAndImage.add(goodsExtend);
        }
        ServletActionContext.getRequest().setAttribute("catelogGoods", goodsAndImage);
        // 获取其他发布列表
        for (int i = 1; i <= rows; i++) {
            goodsList = goodsService.queryByGoodsByCatelogOrderByDate(rows, i);
            goodsAndImage = new ArrayList<>();
            for (int j = 0; j < goodsList.size(); j++) {
                GoodsExtend goodsExtend = new GoodsExtend();
                Goods goods = goodsList.get(j);
                List<Image> images = imageService.queryByImagesByGoodsPrimaryKey(goods.getId());
                goodsExtend.setGoods(goods);
                goodsExtend.setImages(images);
                goodsAndImage.add(goodsExtend);
            }
            ServletActionContext.getRequest().setAttribute("catelogGoods" + i, goodsAndImage);
        }
        return "homeGoods";
    }

    /**
     * 发布商品信息
     */
    public String publishGoods() {
        User cur_user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        // 插入商品数据
        Goods goods = getModel();
        if (goods.getPolishTime() != null && goods.getPolishTime() != "") {
            goods.setUserId(cur_user.getId());
            goods.setStatus(1);
            goodsService.addGoods(goods, 10);
            int goodsId = goods.getId();
            // 插入图片数据
            Image image = new Image();
            image.setGoodsId(goodsId);
            image.setImgUrl(imgUrl);
            imageService.insert(image);
            return "publishGoods";
        } else {
            goods.setUserId(cur_user.getId());
            goods.setStatus(1);
            goodsService.addGoods(goods, 10);
            int goodsId = goods.getId();
            // 插入图片数据
            Image image = new Image();
            image.setGoodsId(goodsId);
            image.setImgUrl(imgUrl);
            imageService.insert(image);
            // 查询目录信息
            Integer goodsNum = cur_user.getGoodsNum();
            Integer catelogId = goods.getCatelogId();
            Catelog catelog = catelogService.queryByPrimaryKey(catelogId);
            // 更新目录表中的商品数量
            catelogService.updateCatelogNum(catelogId, catelog.getNumber() + 1);
            // 更新用户商品
            userService.updateGoodsNum(cur_user.getId(), goodsNum + 1);
            cur_user.setGoodsNum(goodsNum + 1);
            ServletActionContext.getRequest().setAttribute("cur_user", cur_user);
            return "publishGoods";
        }
    }

    /**
     * 图片上传
     *
     * @return
     */
    public String uploadFile() {
        // 得到upload文件上传路径
        String realPath = ServletActionContext.getRequest().getRealPath("/upload");
        File file = new File(realPath);
        // 如果该目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            FileUtils.copyFile(myfile, new File(file, myfileFileName));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", "成功啦");
            map.put("imgUrl", myfileFileName);
            writePageBean2Json(map);
        } catch (IOException e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", "图片不合法");
            writePageBean2Json(map);
        }

        return NONE;
    }

    /**
     * 商品编辑
     *
     * @return
     */
    public String editGoods() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        Goods goods = goodsService.queryGoodsByPrimaryKey(getModel().getId());
        List<Image> list = imageService.queryByImagesByGoodsPrimaryKey(getModel().getId());
        GoodsExtend goodsExtend = new GoodsExtend();
        goodsExtend.setGoods(goods);
        goodsExtend.setImages(list);
        Purse purse = purseService.queryByUserId(user.getId());
        ServletActionContext.getRequest().setAttribute("myPurse", purse);
        ServletActionContext.getRequest().setAttribute("goodsExtend", goodsExtend);
        return "editGoods";
    }

    /**
     * 下架商品
     * 0:下架
     * 1:上架
     * 擦亮
     *
     * @return
     */
    public String myGoodsInfo() {
        goodsService.updateGoodsInfo(getModel().getId(), getModel().getStatus());
        return "goodsDown";
    }

    public String updateGoodsTime() {
        goodsService.updateGoodsTime(getModel().getId());
        return "goodsDown";
    }

    /**
     * 查看商品详情
     *
     * @return
     */
    public String queryGoodsById() {
        Goods goods = goodsService.queryGoodsById(getModel().getId());
        // 查找评论数
        // TODO 此处留有评论数问题，主要在于表之间的连接查询需要处理
        List<Comments> commentsList = commentsService.findCommentById(goods.getUserId(), goods.getId());
        List<Image> imageList = imageService.queryByImagesByGoodsPrimaryKey(goods.getId());
        Catelog catelog = catelogService.queryByPrimaryKey(goods.getCatelogId());
        User seller = userService.queryUserInfo(goods.getUserId());
        GoodsExtend goodsExtend = new GoodsExtend();
        goodsExtend.setGoods(goods);
        goodsExtend.setComments(commentsList);
        goodsExtend.setImages(imageList);
        ServletActionContext.getRequest().setAttribute("goodsExtend", goodsExtend);
        ServletActionContext.getRequest().setAttribute("seller", seller);
        ServletActionContext.getRequest().setAttribute("search", search);
        ServletActionContext.getRequest().setAttribute("catelog", catelog);

        return "goodInfo";
    }

    /**
     * 支付
     *
     * @return
     */
    public String buy() {
        int goodsId = getModel().getId();
        Goods goods = goodsService.queryGoodsByPrimaryKey(goodsId);
        GoodsExtend goodsExtend = new GoodsExtend();
        List<Image> imageList = imageService.queryByImagesByGoodsPrimaryKey(goods.getId());
        goodsExtend.setImages(imageList);
        goodsExtend.setGoods(goods);
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        Purse purse = purseService.queryByUserId(user.getId());
        ServletActionContext.getRequest().setAttribute("myPurse", purse);
        ServletActionContext.getRequest().setAttribute("goodsExtend", goodsExtend);
        return "buy";
    }

    /**
     * GoodsList
     *
     * @return
     */
    public String goodsList() {
        PageResult list = goodsService.findAll(getiPage().getPage(), getiPage().getRows(), null, null, null);
        ServletActionContext.getRequest().getSession().setAttribute("goodsGrid", list);
        return "goodsList";
    }


    public File getMyfile() {
        return myfile;
    }

    public void setMyfile(File myfile) {
        this.myfile = myfile;
    }

    public String getMyfileFileName() {
        return myfileFileName;
    }

    public void setMyfileFileName(String myfileFileName) {
        this.myfileFileName = myfileFileName;
    }

    public String getMyfileContentType() {
        return myfileContentType;
    }

    public void setMyfileContentType(String myfileContentType) {
        this.myfileContentType = myfileContentType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
