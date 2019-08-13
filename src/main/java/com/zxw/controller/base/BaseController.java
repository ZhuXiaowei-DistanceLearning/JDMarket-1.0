package com.zxw.controller.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zxw.util.JsonUtils;
import com.zxw.vo.Page;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zxw on 2019/8/5.
 */
public class BaseController<T> extends ActionSupport implements ModelDriven<T> {
    protected T model;
    private Integer page;
    private Integer rows;
    private String sortBy;
    private String desc;
    private String search;
    private Page iPage;
    private DetachedCriteria detachedCriteria = null;

    public Page getiPage() {
        Page iPage = new Page();
        iPage.setDesc(desc);
        iPage.setPage(page);
        iPage.setRows(rows);
        iPage.setSearch(search);
        iPage.setSortBy(sortBy);
        return iPage;
    }

    @Override
    public T getModel() {
        return model;
    }

    public BaseController() {
        System.out.println(this.getClass());
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        Class<T> tClass = (Class<T>) actualTypeArguments[0];
        detachedCriteria = DetachedCriteria.forClass(tClass);
        try {
            model = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void writePageBean2Json(Object info) {
        try {
            ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
            ServletActionContext.getResponse().getWriter().print(JsonUtils.serialize(info));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeList2Json(List list) throws IOException {
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().print(JsonUtils.serialize(list));
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public DetachedCriteria getDetachedCriteria() {
        return detachedCriteria;
    }

    public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
        this.detachedCriteria = detachedCriteria;
    }
}
