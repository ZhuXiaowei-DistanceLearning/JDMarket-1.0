package com.zxw.service;

import com.zxw.mapper.CommentsMapper;
import com.zxw.pojo.Comments;
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
public class CommentsService {
    @Autowired
    private CommentsMapper commentsMapper;

    public List<Comments> findCommentById(int userId, int goodsId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Comments.class);
        detachedCriteria.add(Restrictions.eq("userId", userId));
        detachedCriteria.add(Restrictions.eq("goodsId", goodsId));
        List<Comments> list = commentsMapper.findByCriteria(detachedCriteria);
        return list;
    }
}
