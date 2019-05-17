<html>
<head>
    <meta charset="UTF-8">
    <title>卖家订单列表</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    <script></script>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
        <#-- 表格 -->
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>订单id</th>
                    <th>买家名称</th>
                    <th>买家电话</th>
                    <th>买家地址</th>
                    <th>订单金额</th>
                    <th>订单状态</th>
                    <th>支付状态</th>
                    <th>创建时间</th>
                    <th>修改时间</th>
                    <th colspan="2">操作</th>
                </tr>
                </thead>
                <tbody>
                <#-- 遍历所有数据 -->
                <#list pageContent.content as orderDTO>
                    <tr>
                        <td>orderDto.orderId</td>
                        <td>orderDto.buyerName</td>
                        <td>orderDto.buyerPhone</td>
                        <td>orderDto.buyerAddress</td>
                        <td>orderDto.orderAmount</td>
                        <td>orderDto.getOrderStatusMsg()</td>
                        <td>orderDto.getPayStatusMsg()</td>
                        <td>orderDto.createTime</td>
                        <td>orderDto.updateTime</td>
                        <td>orderDto.updateTime</td>
                        <td><a href="#">详情</a></td>
                        <td><a href="#">取消</a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#-- 分页 -->
            <ul class="pagination">
            <#-- 如果当前页小于等于1，则不能点击 -->
                    <#if currentPage lte 1>
                        <li><a href="#" class="disabled">上一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${pageSize}">上一页</a></li>
                    </#if>
            <#-- 所有页数 -->
                    <#list 1..pageContent.getTotalPages() as index>
                        <#if currentPage == index>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${pageSize}"
                                   class="disabled">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${pageSize}">${index}</a></li>
                        </#if>
                    </#list>
            <#-- 如果当前页大于总页数，则不能点击 -->
                    <#if currentPage gte pageContent.getTotalPages()>
                        <li><a href="#" class="disabled">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${pageSize}">下一页</a></li>
                    </#if>
            </ul>
        </div>
    </div>
</div>
</body>
</html>