package com.zxw.service;

import com.zxw.mapper.FocusMapper;
import com.zxw.pojo.Focus;
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
public class FocusService {

    @Autowired
    private FocusMapper focusMapper;

    /**
     * 根据用户id获取我的关注
     */
    public List<Focus> queryFocusByUserId(Integer user_id) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Focus.class);
        detachedCriteria.add(Restrictions.eq("userId", user_id));
        List<Focus> list = focusMapper.findByCriteria(detachedCriteria);
        return list;
    }

	 /*
      * 根据用户id和关注id删除
	  */

    public void deleteFocusByUserIdAndGoodsId(Integer goods_id, Integer user_id) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Focus.class);
        detachedCriteria.add(Restrictions.eq("userId", user_id));
        detachedCriteria.add(Restrictions.eq("goodsId", goods_id));
        List<Focus> list = focusMapper.findByCriteria(detachedCriteria);
        focusMapper.delete(list.get(0));
    }

    /*
      * 添加我的关注
      */
    public void addFocusByUserIdAndId(Integer goods_id, Integer user_id) {
        Focus f = new Focus();
        f.setGoodsId(goods_id);
        f.setUserId(user_id);
        focusMapper.save(f);
    }
}
