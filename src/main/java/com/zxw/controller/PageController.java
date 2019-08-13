package com.zxw.controller;

import com.zxw.pojo.Purse;
import com.zxw.pojo.User;
import com.zxw.service.PurseService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by zxw on 2019/8/6.
 */
@Controller
public class PageController {
    @Autowired
    private PurseService purseService;

    public String execute() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("cur_user");
        Purse purse = purseService.queryByUserId(user.getId());
        ServletActionContext.getRequest().setAttribute("myPurse", purse);
        ServletActionContext.getRequest().setAttribute("cur_user", user);
        return "success";
    }
}
