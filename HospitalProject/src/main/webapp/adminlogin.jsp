<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/25
  Time: 21:43
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
                    <form role="form" class="form-inline" action="/adminlogin?method=search" method="post">
                        <div class="form-group">
                            <label for = "searchnamevalue" class="panel-heading">医务工作人员姓名</label>
                            <input class="form-control" type="text" name="searchnamevalue" placeholder="请填写医生姓名" id="searchnamevalue"/>
                        </div>
                        <div class="form-group">
                            <label for="key">科室</label>
                            <select id="level1" class="form-control" onchange="populateLevel2()">
                                <option value=""></option>
                                <option value="内科">内科</option>
                                <option value="外科">外科</option>
                                <option value="其他">其他</option>
                            </select>

                            <!-- 第二级选择框 -->
                            <select id="level2" name="department" class="form-control">
                                <option value=""></option>
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
                        <div class="form-group " style="margin-left: 48px">
                            <button type="button" class="btn btn-default" data-id="${newDno}"data-toggle="modal" data-target="#addUserModal">
										<span style="margin-right: 5px" class="" aria-hidden="true">
											<i class="fa fa-user-plus">增加账号</i>
											</span>
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
                        <th>职工号/账号</th>
                        <th>密码</th>
                        <th>姓名</th>
                        <th>科室</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="doctor">
                        <tr>
                            <td>${doctor.getDno()}</td>
                            <td>${doctor.getPassword()}</td>
                            <td>${doctor.getName()}</td>
                            <td>${doctor.getDepartment()}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-success "
                                            data-id="${doctor.getDno()}"
                                            data-username="${doctor.getName()}"
                                            data-toggle="modal"
                                            data-target="#updateUserModal">
                                        <i class="fa fa-user-o">重置密码</i>
                                    </button>
                                    <button type="button" class="btn btn-danger "
                                            data-did="${doctor.getDno()}"
                                            data-department="${doctor.getName()}"
                                            data-toggle="modal"
                                            onclick="" data-target="#delUserModal">
                                        <i class="fa fa-user-times">删除</i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- add框示例（Modal） -->
                <form method="post" action="/adminlogin?method=add" class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="addUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel2">新建用户</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="building" class="col-sm-3 control-label">大类科室</label>
                                            <div class="col-sm-9">
                                                <select id="building" required  class="form-control" name="buildingId" onchange="populateLevel21()">
                                                    <option value=""></option>
                                                    <option value="内科">内科</option>
                                                    <option value="外科">外科</option>
                                                    <option value="其他">其他</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="dormitory" class="col-sm-3 control-label">具体科室</label>
                                            <div class="col-sm-9">
                                                <select id="dormitory" required class="form-control" name="dormitoryId">
                                                    <option value=""></option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="student" class="col-sm-3 control-label">医生</label>
                                            <div class="col-sm-9">
                                                <select id="student" required class="form-control" name="studentId">
                                                    <%--                                                    //<option value=""></option>--%>
                                                    <%--                                                    <c:forEach items="${studentList}" var="student">--%>
                                                    <%--                                                        <option value="${student.id}">${student.name}</option>--%>
                                                    <%--                                                    </c:forEach>--%>

                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="ppp" class="col-sm-3 control-label">设置密码</label>
                                            <div class="col-sm-9">
                                                <input type="text"  required class="form-control" id="ppp"
                                                       name="ppp">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-primary">确认</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <%--                打印--%>
                <form method="post" action="/adminlogin?method=update" class="form-horizontal"
                      style="margin-top: 0px" role="form" id="form_data" style="margin: 20px;" accept-charset="UTF-8">
                    <div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="myModalLabel1">用户信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-9">
                                                <h5 class="col-sm-18 control-label" id="deleteLabel">删除信息</h5>
                                                <input type="hidden" class="form-control" id="tab"
                                                       name="tab" placeholder="" value="dor_admin"> <input
                                                    type="hidden" class="form-control" id="id"
                                                    name="id" placeholder="">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-danger">确定</button>
                                    <span id="tip"> </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- 删除模态框示例（Modal） -->
                <form method="post" action="/adminlogin?method=delete"
                      class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data1" style="margin: 20px;">
                    <div class="modal fade" id="delUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="myModalLabel1">用户信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-9">
                                                <h5 class="col-sm-18 control-label" id="deleteLabel">删除信息</h5>
                                                <input type="hidden" class="form-control" id="tab"
                                                       name="tab" placeholder="" value="dor_admin"> <input
                                                    type="hidden" class="form-control" id="tno"
                                                    name="tno" placeholder="">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-danger">确定</button>
                                    <span id="tip"> </span>
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
        var department = button.data('username')
        var dname = button.data('dname')
        var status=button.data('status')
        var modal = $(this)
        modal.find('.modal-title').text('重置密码')
        modal.find('#deleteLabel').text('是否重置职工号为' + id + '的'+department+'的医生的账号密码？')
        modal.find('#id').val(id)

    })
    $('#addUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var modal = $(this)

        modal.find('.modal-title').text('新增医生信息')
        modal.find('#dno').val(id)
    })

    $('#delUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('did')
        var department = button.data('department')
        var dname = button.data('dname')
        var status=button.data('status')
        var modal = $(this)
        modal.find('.modal-title').text('删除')
        modal.find('#deleteLabel').text('是否删除账号为' + id + '的账号？')
        modal.find('#tno').val(id)
    })
</script>
<script>
    // 模拟数据，可以从后端获取
    var data = {
        "内科": ["消化内科", "心血管内科", "呼吸内科", "泌尿内科", "血液内科", "内分泌科", "神经内科"],
        "外科": ["胃肠外科", "肝胆外科","神经外科","骨科","皮肤科"],
        "其他": ["儿科", "妇科","产科","服务台"]
    };

    function populateDropdown(selectElement, options) {
        // 清空选择框
        selectElement.innerHTML = "<option value=''></option>";

        // 添加新的选项
        options.forEach(function (option) {
            var optionElement = document.createElement("option");
            optionElement.value = option;
            optionElement.text = option;
            selectElement.add(optionElement);
        });
    }

    function populateLevel2() {
        var level1Select = document.getElementById("level1");
        var level2Select = document.getElementById("level2");
        var selectedValue = level1Select.value;

        if (selectedValue in data) {
            populateDropdown(level2Select, data[selectedValue]);
        } else {
            // 如果未选择第一级，则清空第二级和第三级
            populateDropdown(level2Select, []);
            //populateDropdown(document.getElementById("level3"), []);
        }
    }
    function populateLevel21() {
        var level1Select = document.getElementById("building");
        var level2Select = document.getElementById("dormitory");
        var selectedValue = level1Select.value;

        if (selectedValue in data) {
            populateDropdown(level2Select, data[selectedValue]);
        } else {
            // 如果未选择第一级，则清空第二级和第三级
            populateDropdown(level2Select, []);
            //populateDropdown(document.getElementById("level3"), []);
        }
    }
    $(function(){
        $('#dormitory').change(function(){
            var id = $(this).val();
            $.ajax({
                url:"/adminlogin?method=findByDormitoryId&dormitoryId="+id,
                type:"post",
                dataType:"json",
                success:function (data) {
                    var str = '';
                    for(var i = 0;i<data.length;i++){
                        var student = $(data).get(i);
                        str += '<option value="'+student.dno+'">'+student.name+'</option>'
                    }
                    $('#student').html(str);
                }
            })
        })
    })
</script>
</body>
</html>
