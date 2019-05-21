<html>
<head>
    <meta charset="UTF-8">
    <title>商品</title>
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
                    <form role="form" method="post" action="/seller/product/index">
                        <div class="form-group">
                            <label>名称</label><input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label><input type="text" name="productPrice" class="form-control" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label><input type="text" name="productStock" class="form-control" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label><input type="text" name="productPrice" class="form-control" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label><img width="100px" height="100px" src="${(productInfo.productIcon)!''}" >
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                            <#if category.categoryType == productInfo.categoryType>selected</#if>
                                    >${category.categoryName}</option>
                                </#list>
                            </select>
                        </div>
                        <input type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>