<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
<div id="user_nav">
    <div class="user_info">
        <div class="head_img">
            <img src="<%=basePath%>img/photo.jpg">
        </div>
        <div class="big_headimg">
            <img src="">
        </div>
        <span class="name">${cur_user.username}</span>
        <hr>
        <a class="btn" style="width: 98%;background-color: rgb(79, 190, 246);color:rgba(255, 255, 255, 1);"
           href="<%=basePath%>purse_getMoney">我的钱包：￥${myPurse.balance}</a>
        <input type="hidden" value="${myPurse.recharge}" id="recharge"/>
        <input type="hidden" value="${myPurse.withdrawals}" id="withdrawals"/>
        <span class="btn" data-toggle="modal" data-target="#myModal"
              style="width: 98%; background-color: rgb(79, 190, 246); color: rgba(255, 255, 255, 1); margin-top: 0.5cm;">我的信用积分：${cur_user.power}</span>
    </div>
    <div class="home_nav">
        <ul>
            <a href="<%=basePath%>orders_myOrders">
                <li class="notice">
                    <div></div>
                    <span>订单中心</span>
                    <strong></strong>
                </li>
            </a>
            <a href="<%=basePath%>user_cart">
                <li class="fri">
                    <div></div>
                    <span>关注列表</span>
                    <strong></strong>
                </li>
            </a>
            <a href="<%=basePath%>page_goods_pubGoods">
                <li class="store">
                    <div></div>
                    <span>发布物品</span>
                    <strong></strong>
                </li>
            </a>
            <a href="<%=basePath%>user_queryBySellProduct">
                <li class="second">
                    <div></div>
                    <span>我的闲置</span>
                    <strong></strong>
                </li>
            </a>
            <a href="<%=basePath%>user_basic">
                <li class="set">
                    <div></div>
                    <span>个人设置</span>
                    <strong></strong>
                </li>
            </a>
        </ul>
    </div>
</div>
</body>
