package com.zxw.service;

import com.zxw.mapper.AdminMapper;
import com.zxw.pojo.Admin;
import com.zxw.util.MD5;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by zxw on 2019/8/5.
 */
@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public void changePassword(Admin model) {
        Admin admin = adminMapper.findById(model.getId());
        admin.setPassword(MD5.md5(model.getPassword()));
        adminMapper.update(admin);
    }

    public void modifyUser(Admin model) {
        adminMapper.update(model);
    }

    public Admin login(Admin admin) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
        detachedCriteria.add(Restrictions.eq("phone", admin.getPhone()));
        detachedCriteria.add(Restrictions.eq("password", admin.getPassword()));
        List<Admin> list = adminMapper.findByCriteria(detachedCriteria);
        return list.size() == 0 || list == null ? null : list.get(0);
    }
}
