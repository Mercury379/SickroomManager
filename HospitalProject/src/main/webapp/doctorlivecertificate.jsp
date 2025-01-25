<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/21
  Time: 12:45
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
                    <form role="form" class="form-inline" action="/dlivecertificate?method=search" method="post">
                        <div class="form-group">
                            <label for = "searchnamevalue" class="panel-heading">病人姓名</label>
                            <input class="form-control" type="text" name="searchnamevalue" placeholder="请填写病人姓名" id="searchnamevalue"/>
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
                        <th>时间</th>
                        <th>病案号</th>
                        <th>病人姓名</th>
                        <th>病情</th>
                        <th>诊断</th>
                        <th>治疗方案</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="treatInfo">
                        <tr>
                            <td>${treatInfo.getTreattime()}</td>
                            <td>${treatInfo.getPno()}</td>
                            <td>${treatInfo.getName()}</td>
                            <td>${treatInfo.getCase()}</td>
                            <td>${treatInfo.getDiagnosis()}</td>
                            <td>${treatInfo.getTreat()}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-success "
                                            onclick=""
                                            data-id="${treatInfo.getNextLno()}"
                                            data-tno="${treatInfo.getTno()}"
                                            data-toggle="modal"
                                            data-target="#updateUserModal">
                                        <i class="fa fa-user-o">办理住院证明</i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <form method="post" action="/dlivecertificate?method=insert" class="form-horizontal"
                      style="margin-top: 0px" role="form" id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">住院证明</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="id" class="col-sm-3 control-label">住院号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="id"
                                                       name="id">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="tno" class="col-sm-3 control-label">挂号号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="tno"
                                                       name="tno" value="" placeholder="请输入密码">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="preintime" class="col-sm-3 control-label">建议住院时间</label>
                                            <div class="col-sm-9">
                                                <input type="date" required class="form-control" id="preintime"
                                                       name="preintime" placeholder="请输入建议住院时间">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                            <button type="submit" class="btn btn-primary">确定</button>
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
        var username = button.data('preintime')
        var tno = button.data('tno')
        var name = button.data('name')
        var gender = button.data('gender')
        var telephone = button.data('telephone')
        var modal = $(this)

        modal.find('.modal-title').text('办理住院证明')
        modal.find('#id').val(id)
        modal.find('#preintime').val(username)
        modal.find('#tno').val(tno)
        var list = modal.find('.gender')
        for(var i=0;i<list.length;i++){
            if(gender == $(list.get(i)).val()){
                $(list.get(i)).prop('checked',true)
            }
        }
        modal.find('#telephone').val(telephone)
    })

    $('#delUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var modal = $(this)
        modal.find('.modal-title').text('删除宿管信息')
        modal.find('#deleteLabel').text('是否删除ID为  ' + id + ' 的信息')
        modal.find('#id').val(id)
    })
</script>
</body>
</html>
