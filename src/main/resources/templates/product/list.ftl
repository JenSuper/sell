<html>
<head>
    <meta charset="UTF-8">
    <title>卖家商品列表</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div id="wrapper" class="toggled">
<#-- 边栏 -->
<#include '../common/nav.ftl'>
<#-- 列表 -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                <#-- 表格 -->
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#-- 遍历所有数据 -->
                <#list pageContent.content as orderDTO>
                    <tr>
                        <td>productInfoPage.productId</td>
                        <td>productInfoPage.productName</td>
                        <td>productInfoPage.productIcon</td>
                        <td>productInfoPage.productPrice</td>
                        <td>productInfoPage.productStock</td>
                        <td>productInfoPage.productDescription</td>
                        <td>productInfoPage.categoryType</td>
                        <td>productInfoPage.createTime</td>
                        <td>productInfoPage.updateTime</td>
                        <td><a href="#">修改</a></td>
                        <#if pageContent.getProductStatusEnums().msg == '上架'>
                            <td><a href="/sell/seller/product/off_sale">下架</a></td>
                        <#else>
                            <td><a href="/sell/seller/product/on_sale">上架</a></td>
                        </#if>
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
                        <li><a href="/sell/seller/product/list?page=${currentPage-1}&size=${pageSize}">上一页</a></li>
                    </#if>
                    <#-- 所有页数 -->
                    <#list 1..pageContent.getTotalPages() as index>
                        <#if currentPage == index>
                            <li><a href="/sell/seller/product/list?page=${index}&size=${pageSize}"
                                   class="disabled">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/product/list?page=${index}&size=${pageSize}">${index}</a></li>
                        </#if>
                    </#list>
                    <#-- 如果当前页大于总页数，则不能点击 -->
                    <#if currentPage gte pageContent.getTotalPages()>
                        <li><a href="#" class="disabled">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/product/list?page=${currentPage+1}&size=${pageSize}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>