<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/25
  Time: 21:48
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
                    <form role="form" class="form-inline" action="/rliverecord?method=search" method="post">
                        <div class="form-group">
                            <label for="key">住院状态</label>
                            <select name="key" class="form-control" id="key">
                                <option value=""></option>
                                <option value="还未入院">还未入院</option>
                                <option value="正在住院">正在住院</option>
                                <option value="可以出院">可以出院</option>
                                <option value="已经出院">已经出院</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for = "patientnamevalue" class="panel-heading">病人姓名</label>
                            <input class="form-control" type="text" name="patientnamevalue" placeholder="请输入病人姓名" id="patientnamevalue"/>
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
                        <th>病案号</th>
                        <th>病人姓名</th>
                        <th>入院时间</th>
                        <th>出院时间</th>
                        <th>病房号</th>
                        <th>病床号</th>
                        <th>住院状态</th>
                        <th>联系电话</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="treatInfo">
                        <tr>
                            <td>${treatInfo.getPno()}</td>
                            <td>${treatInfo.getName()}</td>
                            <td>${treatInfo.LiveInfo().getInTime()}</td>
                            <td>${treatInfo.LiveInfo().getOutTime()}</td>
                            <td>${treatInfo.LiveInfo().getRno()}</td>
                            <td>${treatInfo.LiveInfo().getBno()}</td>
                            <td>${treatInfo.getStatus()}</td>
                            <td>${treatInfo.getPhonenumber()}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <form method="post" action="/dtreat?method=update" class="form-horizontal"
                      style="margin-top: 0px" role="form" id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">用户电子病例</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="id" class="col-sm-3 control-label">挂号号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="id"
                                                       name="id">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="pcase" class="col-sm-3 control-label">病情</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="pcase"
                                                       name="pcase" placeholder="请输入病人的具体病情描述">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="diagosis" class="col-sm-3 control-label">诊断</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="diagosis"
                                                       name="diagosis" value="" placeholder="请输入对病人病情的诊断">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="treat" class="col-sm-3 control-label">治疗方案</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="treat"
                                                       name="treat" value="" placeholder="请给出建议病人采取的治疗方案">
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
        var username = button.data('username')
        var password = button.data('password')
        var name = button.data('name')
        var gender = button.data('gender')
        var telephone = button.data('telephone')
        var modal = $(this)

        modal.find('.modal-title').text('填写病人电子病例')
        modal.find('#id').val(id)
        modal.find('#pcase').val(username)
        modal.find('#diagosis').val(password)
        modal.find('#treat').val(name)
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
