<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/22
  Time: 20:49
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
                    <form role="form" class="form-inline" action="/plivecertificate?method=search" method="post">
                        <div class="form-group">
                            <label for="key">使用状态</label>
                            <select name="key" class="form-control" id="key">
                                <option value=""></option>
                                <option value="已使用">已使用</option>
                                <option value="未使用">未使用</option>
                            </select>
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
                        <th>住院证明产生时间</th>
                        <th>住院号</th>
                        <th>医生姓名</th>
                        <th>就诊科室</th>
                        <th>建议入住时间</th>
                        <th>住院证明状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="liveCert">
                        <tr>
                            <td>${liveCert.getTime()}</td>
                            <td>${liveCert.getLno()}</td>
                            <td>${liveCert.getDname()}</td>
                            <td>${liveCert.getDepartment()}</td>
                            <td>${liveCert.getPreintime()}</td>
                            <td>${liveCert.getStatus()}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-danger "
                                            data-id="${liveCert.getTime()}"
                                            data-username="${liveCert.getLno()}"
                                            data-password="${liveCert.getName()}"
                                            data-pno="${liveCert.getPno()}"
                                            data-name="${liveCert.getDname()}"
                                            data-gender="${liveCert.getDepartment()}"
                                            data-telephone="${liveCert.getPreintime()}"
                                            data-status="${liveCert.getStatus()}"
                                            data-toggle="modal"
                                            onclick="" data-target="#updateUserModal">
                                        <i class="fa fa-search">打印住院证明</i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <form method="post" action="/plivecertificate?method=print" class="form-horizontal"
                      style="margin-top: 0px" role="form" id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">用户信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="id" class="col-sm-3 control-label">住院证明时间</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="id"
                                                       name="id">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="username" class="col-sm-3 control-label">住院号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="username"
                                                       name="username" placeholder="请输入用户名">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="pno" class="col-sm-3 control-label">病案号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="pno"
                                                       name="pno" value="" placeholder="请输入密码">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="password" class="col-sm-3 control-label">病人姓名</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="password"
                                                       name="password" value="" placeholder="请输入密码">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="name" class="col-sm-3 control-label">负责医生姓名</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="name"
                                                       name="name" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="gender" class="col-sm-3 control-label">所属科室</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="gender"
                                                       name="gender" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="telephone" class="col-sm-3 control-label">建议入院时间</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="telephone"
                                                       name="telephone" value="" placeholder="">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                            <button type="submit" class="btn btn-primary">确定打印该住院证明</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- 新的 Modal 用于显示已支付的提示信息 -->
                <div class="modal fade" id="paymentAlertModal" tabindex="-1" role="dialog" aria-labelledby="paymentAlertModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="paymentAlertModalLabel">住院证明打印失败提示</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                该住院证明已使用，不能再进行打印！
                            </div>
                        </div>
                    </div>
                </div>
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
        var pno = button.data('pno')
        var name = button.data('name')
        var gender = button.data('gender')
        var telephone = button.data('telephone')
        var status=button.data('status')
        var modal = $(this)

        modal.find('.modal-title').text('打印住院证明')
        modal.find('#id').val(id)
        modal.find('#username').val(username)
        modal.find('#password').val(password)
        modal.find('#name').val(name)
        modal.find('#pno').val(pno)
        modal.find('#gender').val(gender)
        modal.find('#telephone').val(telephone)
        if (status === '已使用') {
            // 显示已支付的提示信息Modal
            $('#paymentAlertModal').modal('show');

            // 阻止模态框显示
            event.preventDefault();

            // 关闭原始的删除Modal
            $('#updateUserModal').modal('hide');
        }
    })

</script>
</body>
</html>
