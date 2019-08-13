package com.zxw.service;

import com.zxw.mapper.PurseMapper;
import com.zxw.pojo.Purse;
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
public class PurseService {

    @Autowired
    private PurseMapper purseMapper;

    public Purse queryByUserId(int id) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Purse.class);
        detachedCriteria.add(Restrictions.eq("userId", id));
        List<Purse> list = purseMapper.findByCriteria(detachedCriteria);
        return list == null ? null : list.get(0);
    }

    public void updatePurse(Purse purse) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Purse.class);
        detachedCriteria.add(Restrictions.eq("userId", purse.getUserId()));
        List<Purse> list = purseMapper.findByCriteria(detachedCriteria);
        if (list.size() > 0) {
            Purse purse1 = list.get(0);
            purse1.setBalance(purse1.getBalance() + purse.getRecharge());
            purseMapper.update(purse1);

        }
    }

    public void updatePurseOfDel(int id, Double balance) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Purse.class);
        detachedCriteria.add(Restrictions.eq("userId", id));
        Purse purse = purseMapper.findByCriteria(detachedCriteria).get(0);
        purse.setBalance(purse.getBalance() - balance);
        purseMapper.update(purse);
    }

    public void initPurse(int id) {
        Purse p = new Purse();
        p.setBalance(0.0);
        p.setUserId(id);
        purseMapper.save(p);
    }

    public PageResult findAll(Integer page, Integer rows, String s, String s1, String s2) {
        List<Purse> list = purseMapper.findAll(page, rows, s, s1, s2);
        long count = purseMapper.count();
        return new PageResult(count, list);
    }
}
