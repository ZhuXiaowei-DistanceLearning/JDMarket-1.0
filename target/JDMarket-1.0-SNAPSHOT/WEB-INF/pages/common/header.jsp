<%--
  Created by IntelliJ IDEA.
  User: zxw
  Date: 2019/8/6
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
<div ng-controller="headerController" class="header stark-components navbar-fixed ng-scope">
    <nav class="white nav1">
        <div class="nav-wrapper">
            <a href="<%=basePath%>goods_homeGoods" class="logo">
                <em class="em1">吉首</em>
                <em class="em2">二手工坊</em>
                <em class="em3">ldu.market</em>
            </a>
            <div class="nav-wrapper search-bar">
                <form ng-submit="search()" class="ng-pristine ng-invalid ng-invalid-required" action="<%=basePath%>goods_search">
                    <div class="input-field">
                        <input id="search" name="str" placeholder="搜点什么吧..." style="height: 40px;"
                               class="ng-pristine ng-untouched ng-empty ng-invalid ng-invalid-required"/>
                        <input type="submit" class="btn"value="搜索"></input>
                        <label for="search" class="active">
                            <i ng-click="search()" class="iconfont"></i>
                        </label>

                    </div>

                </form>
            </div>
            <ul class="right">
                <c:if test="${empty cur_user}">
                    <li class="publish-btn">
                        <button onclick="showLogin()" data-toggle="tooltip"
                                title="您需要先登录哦！" class="red lighten-1 waves-effect waves-light btn" 	>
                            我要发布</button>

                    </li>
                </c:if>
                <c:if test="${cur_user!=null}">
                    <li class="publish-btn">
                        <button data-position="bottom" class="red lighten-1 waves-effect waves-light btn">
                            <a href="<%=basePath%>page_goods_pubGoods">我要发布</a>
                        </button>
                    </li>
                    <li>
                        <a href="<%=basePath%>user_queryBySellProduct">我发布的商品</a>
                    </li>
                    <li>
                              <a>${cur_user.username}</a>
                    </li>
                    <!--  <li class="notification">
                    <i ng-click="showNotificationBox()" class="iconfont"></i>
                    <div ng-show="notification.tagIsShow" class="notification-amount red lighten-1 ng-binding ng-hide">0 </div>
                    </li> -->
                    <li class="changemore">
                        <a class="changeMoreVertShow()">
                            <i class="iconfont"></i>
                        </a>
                        <div class="more-vert">
                            <ul class="dropdown-content">
                                <li><a href="<%=basePath%>user/home">个人中心</a></li>
                                <li><a href="<%=basePath%>user/allFocus">我的关注</a></li>
                                <li><a onclick="ChangeName()">更改用户名</a></li>
                                <li><a href="<%=basePath%>user/logout">退出登录</a></li>
                            </ul>
                        </div>
                    </li>
                </c:if>
                <c:if test="${empty cur_user}">
                    <li>
                        <a onclick="showLogin()">登录</a>
                    </li>
                    <li>
                        <a onclick="showSignup()">注册</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>
</div>
</body>
