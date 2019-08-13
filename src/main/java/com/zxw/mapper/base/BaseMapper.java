package com.zxw.mapper.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Created by zxw on 2019/8/5.
 */
public class BaseMapper<T> extends HibernateDaoSupport implements Mapper<T> {
    private Class<T> tClass;

    @Resource
    public void MySessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public BaseMapper() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        tClass = (Class<T>) actualTypeArguments[0];
    }

    @Override
    public void save(T t) {
        this.getHibernateTemplate().setCheckWriteOperations(false);
        this.getHibernateTemplate().save(t);
    }

    @Override
    public void update(T t) {
        this.getHibernateTemplate().update(t);
    }

    @Override
    public void delete(T t) {
        this.getHibernateTemplate().delete(t);
    }

    @Override
    public void saveOrUpdate(T t) {
        this.getHibernateTemplate().saveOrUpdate(t);
    }

    @Override
    public T findById(Serializable id) {
        return this.getHibernateTemplate().get(tClass, id);
    }

    @Override
    public List<T> findAll(Integer page, Integer rows, String sortBy, String desc, String search) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(tClass);
        if (sortBy != "" && sortBy != null) {
            if (desc.equals("DESC")) {
                detachedCriteria.addOrder(Order.asc(sortBy));
            } else {
                detachedCriteria.addOrder(Order.desc(sortBy));
            }
        }
        if (search != "" && search != null) {
            detachedCriteria.add(Restrictions.like("name", search));
        }
        // 页数调整
        int offset = (page - 1) * rows;
        if (offset == 0) {
            offset = rows;
        }
        List<?> list = this.getHibernateTemplate().findByCriteria(detachedCriteria, page - 1, offset);
        return (List<T>) list;
    }

   /* public void pageQuery(PageBean pageBean) {
        int Currentpage = pageBean.getCurrentPage();
        int pageSize = pageBean.getPageSize();
        DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
        //设置Projections改变Criteria的查询方式,改变Hiberante框架发出sql的形式
        detachedCriteria.setProjection(Projections.rowCount());
        //查询出所有的记录数
        List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
        Long aLong = list.get(0);
        pageBean.setTotal(aLong.intValue());
        //修改Criteria
        detachedCriteria.setProjection(null);
        //重置表和类的映射关系
        detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
        int firstResult = (Currentpage - 1) * pageSize;
        int maxResult = pageSize;
        //调用分页查询的方法，需要从第一条数据开始查，每页需要查询的数量
        List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResult);
        pageBean.setRows(rows);
    }*/

    @Override
    public void executeUpdate(String queryName, Object... objects) {
        Session session = this.getSessionFactory().openSession();
        Query query = session.getNamedQuery(queryName);
        int i = 0;
        for (Object args : objects) {
            query.setParameter(i++, args);
        }
        query.executeUpdate();
    }

    @Override
    public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
        return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    public List<T> findByCriteria(DetachedCriteria detachedCriteria, Integer page, Integer rows) {
        return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria, page, rows);
    }

    public long count() {
        Session session = this.getSessionFactory().openSession();
        Optional optional = session.createSQLQuery("select count(*) from " + tClass.getSimpleName()).uniqueResultOptional();
        return new Long(optional.get().toString());
    }
}
