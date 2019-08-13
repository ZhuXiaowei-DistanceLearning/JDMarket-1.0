package com.zxw.service;

import com.zxw.mapper.CatelogMapper;
import com.zxw.pojo.Catelog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by zxw on 2019/8/5.
 */
@Service
@Transactional
public class CatelogService {

    @Autowired
    private CatelogMapper catelogMapper;

    public Catelog queryByPrimaryKey(Integer catelogId) {
        return catelogMapper.findById(catelogId);
    }

    public void updateCatelogNum(Integer catelogId, int i) {
        Catelog id = catelogMapper.findById(catelogId);
        id.setNumber(i);
        catelogMapper.update(id);
    }

    public void updateCatelogByGoods(Integer catelogId) {
        Catelog byId = catelogMapper.findById(catelogId);
        byId.setNumber(byId.getNumber() - 1);
        catelogMapper.update(byId);
    }
}
