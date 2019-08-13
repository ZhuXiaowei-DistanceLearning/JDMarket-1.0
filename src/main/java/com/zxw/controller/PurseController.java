package com.zxw.controller;

import com.zxw.controller.base.BaseController;
import com.zxw.pojo.Purse;
import com.zxw.pojo.User;
import com.zxw.service.PurseService;
import com.zxw.vo.PageResult;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by zxw on 2019/8/5.
 */
@Controller
public class PurseController extends BaseController<Purse> {
    @Autowired
    private PurseService purseService;

    /**
     * 查看个人钱包
     */
    public String getMoney() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        Purse purse = purseService.queryByUserId(user.getId());
        ServletActionContext.getRequest().setAttribute("myPurse", purse);
        return "money";
    }

    /**
     * 充值
     */
    public String updatePurse() {
        User cur_user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        Purse purse = getModel();
        purse.setUserId(cur_user.getId());
        purse.setState(0);
        if (purse.getRecharge() != null) {
            purseService.updatePurse(purse);
        }
        if (purse.getWithdrawals() != null) {
            purseService.updatePurse(purse);
        }
        return "updatePurse";
    }

    public String purseList() {
        PageResult list = purseService.findAll(getiPage().getPage(), getiPage().getRows(), "", "", "");
        ServletActionContext.getRequest().getSession().setAttribute("purseGrid", list);
        return "purseList";
    }

}
