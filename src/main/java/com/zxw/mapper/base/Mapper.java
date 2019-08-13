package com.zxw.mapper.base;

import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zxw on 2019/8/5.
 */
public interface Mapper<T> {
    public void save(T t);

    public void update(T t);

    public void delete(T t);

    public void saveOrUpdate(T t);

    public T findById(Serializable id);

    public List<T> findAll(Integer page, Integer rows, String sortBy, String desc, String search);

    public void executeUpdate(String queryName, Object... objects);

    public List<T> findByCriteria(DetachedCriteria detachedCriteria);

    public List<T> findByCriteria(DetachedCriteria detachedCriteria, Integer page, Integer rows);
}
