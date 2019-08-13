package com.zxw.service;

import com.zxw.mapper.ImageMapper;
import com.zxw.pojo.Image;
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
public class ImageService {

    @Autowired
    private ImageMapper imageMapper;

    public List<Image> queryByImagesByGoodsPrimaryKey(int id) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Image.class);
        detachedCriteria.add(Restrictions.eq("goodsId", id));
        List<Image> byCriteria = imageMapper.findByCriteria(detachedCriteria);
        return byCriteria;
    }

    public void insert(Image image) {
        imageMapper.save(image);
    }
}
