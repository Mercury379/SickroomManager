<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/18
  Time: 21:28
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
    <title>医生服务页面</title>
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
                <a>欢迎您,${systemDoctor.getName()}医生</a>
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

            <a href="javascript:void(0)" class="list-group-item active" onclick="change('/treatinfo?method=list',0)">
						<span class="" aria-hidden="true">
							<i class="fa fa-user-circle-o fa-fw"></i>
						</span>治疗记录
            </a>
<%--            <a href="javascript:void(0)" class="list-group-item" onclick="change('/doctormanager.jsp?method=list',1)">--%>
<%--						<span class="" aria-hidden="true">--%>
<%--							<i class="fa fa-user-circle fa-fw"></i>--%>
<%--						</span>查看病人住院记录--%>
<%--            </a>--%>
            <a href="javascript:void(0)" class="list-group-item" onclick="change('/dblankbed?method=list',1)">
						<span class="" aria-hidden="true">
							<i class="fa fa-home fa-fw"></i>
						</span>查看空病床
            </a>
            <a href="javascript:void(0)" class="list-group-item" onclick="change('/dlivecertificate?method=list',2)">
						<span class="" aria-hidden="true">
							<i class="fa fa-bed fa-fw"></i>
						</span>办理入院证明
            </a>
            <a href="javascript:void(0)" class="list-group-item" onclick="change('/dlivecanout?method=list',3)">
						<span class="" aria-hidden="true">
							<i class="fa fa-address-card-o fa-fw"></i>
						</span>办理出院许可
            </a>
            <a href="javascript:void(0)" class="list-group-item" onclick="change('/dtreat?method=list',4)">
						<span class="" aria-hidden="true">
							<i class="fa fa-bookmark fa-fw"></i>
						</span>填写病例
            </a>

        </div>
        <!--右边内容-->
        <iframe style="width: 81%; height: 700px; border: 0px;" src="/treatinfo?method=list"></iframe>
    </div>
</div>
<%--<div class="footer">--%>
<%--    <p class="text-center">--%>
<%--        2022 © DORMS--%>
<%--    </p>--%>
<%--</div>--%>
</body>
</html>
