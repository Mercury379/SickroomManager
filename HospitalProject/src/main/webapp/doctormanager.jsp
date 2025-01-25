<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/18
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <!-- 引入 Bootstrap -->
    <script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入 font-awesome -->
<%--    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="width: 1500px;">
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-10">
            <!-- 顶部搜索部分 -->
            <div class="panel panel-default">
                <div class="panel-heading">搜索</div>
                <div class="panel-body">
                    <form role="form" class="form-inline" action="/treatinfo?method=search" method="post">
                        <div class="form-group">
                            <label for="key">住院状态</label>
                            <select name="key" class="form-control" id="key">
                                <option value=""></option>
                                <option value="noneed">无需住院</option>
                                <option value="notin">还未住院</option>
                                <option value="living">正在住院</option>
                                <option value="notout">还未出院</option>
                                <option value="out">已经出院</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for = "patientidvalue" class="panel-heading">病案号</label>
                            <input class="form-control" type="text" name="patientidvalue" placeholder="请输入病案号" id="patientidvalue"/>
                        </div>
                        <div class="form-group">
                            <label for = "treatdatevalue" class="panel-heading">诊疗日期</label>
                            <input class="form-control" type="date" name="treatdatevalue" placeholder="请选择日期" id="treatdatevalue"/>
                        </div>
                        <div class="form-group " style="margin-left: 20px">
                            <button type="submit" class="btn btn-info ">
										<span style="margin-right: 5px"
                                              class="" aria-hidden="true">
                                            <i class="fa fa-search"></i>
										</span>开始搜索
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <!-- 列表展示-->
            <div class="table-responsive">
                <table class="table table-hover ">
                    <thead>
                    <tr>
                        <th>诊断时间</th>
                        <th>病案号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>年龄</th>
                        <th>地址</th>
                        <th>联系电话</th>
                        <th>住院状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="treatInfo">
                        <tr>
                            <td>${treatInfo.getTreattime()}</td>
                            <td>${treatInfo.getPno()}</td>
                            <td>${treatInfo.getName()}</td>
                            <td>${treatInfo.getSex()}</td>
                            <td>${treatInfo.getAge()}</td>
                            <td>${treatInfo.getAddress()}</td>
                            <td>${treatInfo.getPhonenumber()}</td>
                            <td>${treatInfo.getStatus()}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-danger "
                                            data-id="${treatInfo.getCase()}" data-toggle="modal"
                                            data-username="${treatInfo.getDiagnosis()}"
                                            data-password="${treatInfo.getTreat()}"
                                            data-toggle="modal"
                                            onclick="" data-target="#updateUserModal">
                                        <i class="fa fa-search">查看具体病例</i>
                                    </button>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-success "
                                            data-id="${treatInfo.LiveInfo().getLNo()}" data-toggle="modal"
                                            data-username="${treatInfo.LiveInfo().getRno()}"
                                            data-password="${treatInfo.LiveInfo().getBno()}"
                                            data-name="${treatInfo.LiveInfo().getInTime()}"
                                            data-telephone="${treatInfo.LiveInfo().getOutTime()}"
                                            data-toggle="modal"
                                            onclick="" data-target="#liveModal">
                                        <i class="fa fa-search">查看住院信息</i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <form method="post" action="/dormitoryAdmin?method=update" class="form-horizontal"
                      style="margin-top: 0px" role="form" id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">病人病例</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">病情</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="id"
                                                       name="id">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">诊断</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="username"
                                                       name="username" placeholder="请输入用户名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">治疗方案</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="password"
                                                       name="password" value="" placeholder="请输入密码">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <form method="post" action="/dormitoryAdmin?method=update" class="form-horizontal"
                      style="margin-top: 0px" role="form" id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="liveModal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">病人病例</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">住院号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="id"
                                                       name="id" placeholder="未有该信息">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">病房号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="username"
                                                       name="username" placeholder="未有该信息">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">病床号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="password"
                                                       name="password" value="" placeholder="未有该信息">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">入院时间</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="name"
                                                       name="name" value="" placeholder="未有该信息">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">出院时间</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="telephone"
                                                       name="telephone" value="" placeholder="未有该信息">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $('#updateUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var username = button.data('username')
        var password = button.data('password')
        var name = button.data('name')
        var gender = button.data('gender')
        var telephone = button.data('telephone')
        var modal = $(this)

        modal.find('.modal-title').text('病人病例')
        modal.find('#id').val(id)
        modal.find('#username').val(username)
        modal.find('#password').val(password)
        modal.find('#name').val(name)
        var list = modal.find('.gender')
        for(var i=0;i<list.length;i++){
            if(gender == $(list.get(i)).val()){
                $(list.get(i)).prop('checked',true)
            }
        }
        modal.find('#telephone').val(telephone)
    })
    $('#liveModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var username = button.data('username')
        var password = button.data('password')
        var name = button.data('name')
        var gender = button.data('gender')
        var telephone = button.data('telephone')
        var modal = $(this)

        modal.find('.modal-title').text('住院信息')
        modal.find('#id').val(id)
        modal.find('#username').val(username)
        modal.find('#password').val(password)
        modal.find('#name').val(name)
        var list = modal.find('.gender')
        for(var i=0;i<list.length;i++){
            if(gender == $(list.get(i)).val()){
                $(list.get(i)).prop('checked',true)
            }
        }
        modal.find('#telephone').val(telephone)
    })
</script>
</body>
</html>
