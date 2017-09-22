<html>
<head>
    <!-- 引入样式 -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">
    <!-- 引入组件库 -->
    <script
            src="https://code.jquery.com/jquery-2.2.4.min.js"
            integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
            crossorigin="anonymous"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</head>
<body>
<div  data-example-id="carousel-with-captions">
    <div id="carousel-example-captions" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <#list list as post>
                <li data-target="#carousel-example-captions" data-slide-to="${post_index}" <#if post_index==0 >class="active"</#if>></li>
            </#list>
        </ol>
        <div class="carousel-inner" role="listbox">
            <#list list as post>
                <div class="item  <#if post_index==0 >active</#if>">
                    <img  src="${post.url}" alt="${post.desc}">
                    <div class="carousel-caption">
                        <h3>${post.publishedAt}</h3>
                    </div>
                </div>
            </#list>
        </div>
        <a class="left carousel-control" href="#carousel-example-captions" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#carousel-example-captions" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
</div>
</body>
</html>