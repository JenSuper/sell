<html>
<head>
    <meta charset="UTF-8">
    <title>异常页面</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    错误页面!
                </h4> <strong>${masg}</strong>3秒后<a href="${url}" class="class="alert-link"">跳转</a>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    //3秒后跳转到指定页面
    setTimeout('location.href="${url}"',3000)
</script>
</html>