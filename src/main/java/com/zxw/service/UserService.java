package com.zxw.service;

import com.zxw.mapper.UserMapper;
import com.zxw.pojo.User;
import com.zxw.util.MD5;
import com.zxw.vo.Page;
import com.zxw.vo.PageResult;
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
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User AccountCheck(String username, String phone) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
//        detachedCriteria.add(Restrictions.eq("username", username));
        detachedCriteria.add(Restrictions.eq("phone", phone));
        List<User> list = userMapper.findByCriteria(detachedCriteria);
        return list.size() == 0 || list == null ? null : list.get(0);
    }

    public void register(User user) {
        userMapper.save(user);
    }

    public User login(User user) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
        detachedCriteria.add(Restrictions.eq("phone", user.getPhone()));
        detachedCriteria.add(Restrictions.eq("password", MD5.md5(user.getPassword())));
        List<User> list = userMapper.findByCriteria(detachedCriteria);
        return list.size() == 0 || list == null ? null : list.get(0);
    }

    public void updateGoodsNum(int id, int i) {
        User user = userMapper.findById(id);
        user.setGoodsNum(i);
        userMapper.update(user);
    }

    public void updateUserInfo(User user) {
        userMapper.update(user);
    }

    public User queryUserInfo(int id) {
        return userMapper.findById(id);
    }

    public PageResult findAll(Page page) {
        List<User> list = userMapper.findAll(page.getPage(), page.getRows(), null, null, null);
        long count = userMapper.count();
        return new PageResult(count, list);
    }
}
