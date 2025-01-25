<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/25
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- 引入 Bootstrap -->
    <script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入 font-awesome -->
    <%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <%--    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">--%>
    <%--    <link href="https://cdn.bootcss.com/font-awesome/3.3.7/css/font-awesome.min.css" rel="stylesheet">--%>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="application/javascript">
        function change(url,index){
            $(".list-group-item").removeClass("active");
            $(".list-group-item").eq(index).addClass("active");
            $("iframe").attr("src",url);
        }
        // function change(type,index){
        //     $(".list-group-item").removeClass("active");
        //     $(".list-group-item").eq(index).addClass("adt")
        // }
    </script>
    <title>超级管理员页面</title>
</head>
<body>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-left">
            <li>
                <%--                <a style="font-size: 26px;">医院住院管理系统-医生</a>--%>
                <img src="images/label5.png" height="90">
            </li>
        </ul>
        <span style="color: #CCCCCC;font-size: 26px;position: relative;top: 5px;"></span>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a>欢迎您,超级管理员</a>
            </li>
            <li>
                <a href="/logindoctor?method=logout">安全退出</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2">

            <a href="javascript:void(0)" class="list-group-item active" onclick="change('/admindoctor?method=list',0)">
						<span class="" aria-hidden="true">
							<i class="fa fa-male"></i>
						</span>修改医生信息
            </a>
            <a href="javascript:void(0)" class="list-group-item" onclick="change('/adminlogin?method=list',1)">
						<span class="" aria-hidden="true">
							<i class="fa fa-home fa-fw"></i>
						</span>修改登录信息
            </a>
        </div>
        <!--右边内容-->
        <iframe style="width: 81%; height: 700px; border: 0px;" src="/admindoctor?method=list"></iframe>
    </div>
</div>
<%--<div class="footer">--%>
<%--    <p class="text-center">--%>
<%--        2022 © DORMS--%>
<%--    </p>--%>
<%--</div>--%>
</body>
</html>
