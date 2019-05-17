<html>
<head>
    <meta charset="UTF-8">
    <title>订单详情页面</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <#-- 订单金额 -->
        <div class="col-md-2 column">
            <table class="table">
                <thead>
                <tr>
                    <th>订单id</th>
                    <th>订单金额</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDTO.orderId}</td>
                    <td>${orderDTO.orderAmount}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <#-- 订单详情列表 -->
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                <tr>
                    <th>商品id</th>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>总额</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.orderDetailList as orderDetail>
                <tr>
                    <td>${orderDetail.productId}</td>
                    <td>${orderDetail.productName}</td>
                    <td>${orderDetail.productPrice}</td>
                    <td>${orderDetail.productQuantity}</td>
                    <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <#-- 订单操作：完结订单和取消订单 -->
        <#if orderDTO.getOrderStatusMsg() == '新订单'>
            <div class="col-md-12 column">
                <a href="/sell/seller/order/finsh?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
            </div>
        </#if>
    </div>
</div>
</body>
</html>