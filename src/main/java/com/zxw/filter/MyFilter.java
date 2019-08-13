package com.zxw.filter;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Created by zxw on 2019/8/5.
 */
public class MyFilter extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        return invocation.invoke();
    }
}
