<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>吉首二手工坊</title>
    <link rel="icon" href="<%=basePath%>img/logo.jpg" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=basePath%>css/index.css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/materialize.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/index.bundle.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/materialize-icon.css"/>
    <link rel="stylesheet" href="<%=basePath%>css/detail.css"/>
    <script>
        function showLogin() {
            if ($("#signup-show").css("display") == 'block') {
                $("#signup-show").css("display", "none");
            }
            if ($("#login-show").css("display") == 'none') {
                $("#login-show").css("display", "block");
            } else {
                $("#login-show").css("display", "none");
            }
        }
        function showSignup() {
            if ($("#login-show").css("display") == 'block') {
                $("#login-show").css("display", "none");
            }
            if ($("#signup-show").css("display") == 'none') {
                $("#signup-show").css("display", "block");
            } else {
                $("#signup-show").css("display", "none");
            }
        }
        function ChangeName() {
            if ($("#changeName").css("display") == 'none') {
                $("#changeName").css("display", "block");
            } else {
                $("#changeName").css("display", "none");
            }
        }
    </script>

    <script type="text/javascript">

        function addFocus(id) {
            location.href = '<%=basePath%>focus_addCart?goodsId=' + id
            alert("已关注成功，查看关注列表~")

        }

        /* 前往支付 */
        function toPay(id) {
            window.location.href = '<%=basePath%>goods_buy?id=' + id
        }

    </script>


<body ng-view="ng-view">
<!--

    描述：顶部
-->
<%@include file="/WEB-INF/pages/common/header.jsp" %>
<%@include file="/WEB-INF/pages/common/user_login.jsp" %>
<!--更改用户名-->
<div ng-controller="changeNameController" class="ng-scope">
    <div id="changeName" class="change-name stark-components">
        <div class="publish-box z-depth-4">
            <div class="row">
                <div class="col s12 title">
                    <h1>修改用户名</h1>
                </div>
                <form action="../../user/changeName" method="post" role="form">
                    <div class="input-field col s12">
                        <input type="text" name="username" required="required"
                               class="validate ng-pristine ng-empty ng-invalid ng-invalid-required ng-valid-pattern ng-touched"/>
                        <label>修改用户名</label>
                    </div>
                    <div ng-show="checkTelIsShow" class="col s12">
                        <button class="waves-effect waves-light btn publish-btn red lighten-1">
                            <i class="iconfont left"></i>
                            <em>确认</em>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!--显示商品详情-->
<div ng-controller="detailBoxController" class="detail-box stark-components z-depth-1 row ng-scope">
    <div class="col s12 path">
        <a href="<%=basePath%>goods/catelog/${catelog.id}">${catelog.name}</a>
        <em>></em>
        <a>${goodsExtend.goods.name}</a>
    </div>
    <div class="col s6">
        <div class="slider" style="height: 440px;">
            <ul class="slides" style="height: 400px;">
                <img src="<%=basePath%>upload/${goodsExtend.images[0].imgUrl}"/>
            </ul>
            <ul class="indicators">
                <li class="indicator-item"></li>
                <li class="indicator-item"></li>
                <li class="indicator-item"></li>
                <li class="indicator-item"></li>
            </ul>
        </div>
    </div>
    <div class="col s6">
        <h1 class="item-name">${goodsExtend.goods.name}</h1>
        <h2 class="item-price">${goodsExtend.goods.price}</h2>
        <h2 class="publisher-info-title">原价：<span
                style="text-decoration:line-through;">${goodsExtend.goods.realPrice}</span></h2>
        <div class="item-public-info">
            <p class="bargain">可讲价</p>
            <p>
                <i class="iconfont"></i>
                <em class="item-location">吉首大学</em>
            </p>
        </div>
        <div class="publisher-info-title">
            <em>卖家信息</em>
            <hr>
        </div>
        <c:if test="${empty cur_user}">
            <div class="item-contact">
                <p class="not-login">
                    <a onclick="showLogin()">登录</a>
                    <em>或</em>
                    <a onclick="showSignup()">注册</a>
                    <em>后查看联系信息</em>
                </p>
            </div>
        </c:if>
        <c:if test="${!empty cur_user}">
            <div class="item-contact">
                <div>
                    <div class="base-blue z-depth-1 attr">
                        <i class="iconfont"></i>
                    </div>
                    <div class="value">${seller.username}</div>
                </div>
                <div>
                    <div class="base-blue z-depth-1 attr">
                        <i class="iconfont"></i>
                    </div>
                    <div class="value">${seller.phone}</div>
                </div>
                <div>
                    <div class="base-blue z-depth-1 attr">
                        <i class="iconfont"></i>
                    </div>
                    <c:if test="${seller.qq!=null}">
                        <div class="value">${seller.qq}</div>
                    </c:if>
                    <c:if test="${seller.qq==null}">
                        <div class="value">该同学没留下QQ</div>
                    </c:if>

                </div>
                <div>
                    <input type="button" value="加入关注" class="blue lighten-1 waves-effect waves-light btn" id="btn_cart"
                           onclick="addFocus(${goodsExtend.goods.id})"></input>
                    <c:if test="${cur_user.id==goodsExtend.goods.userId}">
                        <input type="button" value="在线支付" data-toggle="tooltip" title="不可以购买自己的东西哦~" disabled="disabled"
                               class="blue lighten-1 waves-effect waves-light btn" id="btn_buy"></input>
                    </c:if>
                    <c:if test="${cur_user.id!=goodsExtend.goods.userId}">
                        <input type="button" value="在线支付" class="blue lighten-1 waves-effect waves-light btn"
                               id="btn_buy" onclick="toPay(${goodsExtend.goods.id})"></input>
                    </c:if>
                </div>
            </div>
        </c:if>
        <h1 class="item-pub-time">发布于 ${goodsExtend.goods.startTime}</h1>
    </div>
</div>
<div class="detail-box stark-components z-depth-1 row">
    <h1 class="title">商品描述</h1>
    <hr class="hr1"/>
    <hr class="hr2"/>
    <p class="section">描述：${goodsExtend.goods.describle}</p>
    <p class="section"></p>
    <p class="section">
        联系我的时候，请说明是在“吉首二手工坊”上看见的哦~
    </p>
</div>
<div class="row detail-area">
    <div class="clo s12">
        <div ng-controller="commentController" class="comment stark-components z-depth-1 ng-scope">
            <h1 class="title">评论</h1>
            <hr class="hr1"/>
            <hr class="hr2"/>
            <c:forEach var="item" items="${CommentExtend.comments}">
                <div class="comment-collection">
                    <div class="comment-item ng-scope">
                        <div class="comment-main-content">
                            <em class="name ng-binding">${item.user.username}:</em>
                            <em class="ng-hide">回复</em>
                            <em class="name ng-binding ng-hide">@:</em>
                            <em class="ng-binding">${item.content}</em>
                        </div>
                        <div class="comment-function">
                            <em class="time ng-biinding">${item.createAt}</em>

                            <!--  <a class="reply-or-delete">删除</a>
                             <a class="reply-or-delete">回复</a> -->
                        </div>
                    </div>
                </div>
            </c:forEach>
            <form id="addCommentForm" class="form-horizontal">
                <div class="comment-add row">
                    <div class="input-field col s12">
                        <i class="iconfont prefix"></i>
                        <input id="goodsId" name="goods.id" value="${goodsExtend.goods.id}" type="hidden"/>
                        <input id="commentbox" type="text" name="content"
                               class="validate ng-pristine ng-untouched ng-valid ng-empty"/>
                        <label for="commentbox">这里写下评论</label>
                        <%-- <c:if test="${!empty cur_user}">
                         <button type="button" class="waves-effect wave-light btn comment-submit" onclick="addComments()">确认</button>
                         </c:if>--%>
                        <%--   <c:if test="${!empty cur_user} && ${cur_user.id!=goodsExtend.comments.userId}">
                         <button type="submit" class="waves-effect wave-light btn comment-submit">确认</button>
                         </c:if>
                          <c:if test="${!empty cur_user} && ${cur_user.id==goodsExtend.comments.userId}">
                         <button data-toggle="tooltip"  title="您已经评论过了哦！" disabled="disabled"  class="waves-effect wave-light btn comment-submit">确认</button>
                         </c:if> --%>
                        <%--   <c:if test="${empty cur_user}">
                          <button href="javacript:void(0);" data-toggle="tooltip"  title="您需要先登录哦！" disabled="disabled" class="waves-effect wave-light btn comment-submit">确认</button>
                          </c:if>--%>
                    </div>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    /* 评论 */
    function addComments() {
        $.ajax({
            url: '<%=basePath%>goods/addComments',
            type: 'POST',
            data: $('#addCommentForm').serialize(),// 序列化表单值
            dataType: 'json',
            /* 	success:function(json){
             alert(1)
             alert(json.msg)
             },
             error:function(){
             alert('请求超时或系统出错!');
             } */
        });
        alert("您已评论成功~")
        window.location.reload();
    }
</script>
</html>