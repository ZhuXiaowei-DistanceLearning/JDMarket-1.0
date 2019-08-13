package com.zxw.controller;

import com.zxw.controller.base.BaseController;
import com.zxw.pojo.Admin;
import com.zxw.service.AdminService;
import com.zxw.service.UserService;
import com.zxw.vo.PageResult;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by zxw on 2019/8/5.
 */
@Controller
public class AdminController extends BaseController<Admin> {
    @Autowired
    private AdminService adaminService;
    @Autowired
    private UserService userService;

    public String changePassword() {
        adaminService.changePassword(getModel());
        return "success";
    }

    public String modify() {
        adaminService.modifyUser(getModel());
        return "success";
    }

    public String logout() {
        ServletActionContext.getRequest().getSession().invalidate();
        return "logout";
    }

    public String userList() {
        PageResult pageResult1 = userService.findAll(getiPage());
        ServletActionContext.getRequest().getSession().setAttribute("userGrid", pageResult1);
        ServletActionContext.getRequest().getSession().setAttribute("ipage", getiPage());
        return "userList";
    }

    public String login() {
        Admin admin = adaminService.login(getModel());
        ServletActionContext.getRequest().setAttribute("admin", admin);
        return "success";
    }
}
