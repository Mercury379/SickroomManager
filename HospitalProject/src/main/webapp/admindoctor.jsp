<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/25
  Time: 17:23
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
                    <form role="form" class="form-inline" action="/admindoctor?method=search" method="post">
                        <div class="form-group">
                            <label for = "searchnamevalue" class="panel-heading">医务工作人员姓名</label>
                            <input class="form-control" type="text" name="searchnamevalue" placeholder="请填写医生姓名" id="searchnamevalue"/>
                        </div>
                        <div class="form-group">
                            <label for="key">挂号科室</label>
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
                        <th>职工号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>年龄</th>
                        <th>职称</th>
                        <th>电话号码</th>
                        <th>科室</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="doctor">
                        <tr>
                            <td>${doctor.getDno()}</td>
                            <td>${doctor.getName()}</td>
                            <td>${doctor.getSex()}</td>
                            <td>${doctor.getAge()}</td>
                            <td>${doctor.getTitle()}</td>
                            <td>${doctor.getPhonenum()}</td>
                            <td>${doctor.getDepartment()}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-success "
                                            data-id="${doctor.getDno()}"
                                            data-username="${doctor.getName()}"
                                            data-password="${doctor.getSex()}"
                                            data-name="${doctor.getAge()}"
                                            data-gender="${doctor.getTitle()}"
                                            data-telephone="${doctor.getPhonenum()}"
                                            data-status="${doctor.getDepartment()}"
                                            data-toggle="modal"
                                            data-target="#updateUserModal">
                                        <i class="fa fa-user-o">修改信息</i>
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
                <form method="post" action="/admindoctor?method=add" class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="addUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel2">挂号</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="dno" class="col-sm-3 control-label">职工号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="dno"
                                                       name="dno">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="dname" class="col-sm-3 control-label">姓名</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="dname"
                                                       name="dname" placeholder="请输入医生姓名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">性别</label>
                                            <div class="col-sm-9">
                                                <input type="radio" checked value="男" class="gender"
                                                       name="dsex" > 男
                                                &nbsp;&nbsp;&nbsp;<input type="radio" value="女" class="gender"
                                                                         name="dsex" > 女
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="age" class="col-sm-3 control-label">年龄</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="age"
                                                       name="age" value="" placeholder="请输入年龄">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="title" class="col-sm-3 control-label">职称</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="title"
                                                       name="title" value="" placeholder="请输入职称">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="dtelephone" class="col-sm-3 control-label">联系电话</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="dtelephone"
                                                       name="dtelephone" value="" placeholder="请输入联系电话">
                                            </div>
                                        </div>
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
                                            <label for="dormitory" class="col-sm-3 control-label">具体挂号科室</label>
                                            <div class="col-sm-9">
                                                <select id="dormitory" required class="form-control" name="dormitoryId">
                                                    <option value=""></option>
                                                </select>
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
                <form method="post" action="/admindoctor?method=update" class="form-horizontal"
                      style="margin-top: 0px" role="form" id="form_data" style="margin: 20px;" accept-charset="UTF-8">
                    <div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">修改医生信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="id" class="col-sm-3 control-label">职工号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="id"
                                                       name="id">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="username" class="col-sm-3 control-label">姓名</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="username"
                                                       name="username" placeholder="请输入用户名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">性别</label>
                                            <div class="col-sm-9">
                                                <input type="radio" checked value="男" class="gender"
                                                       name="password" > 男
                                                &nbsp;&nbsp;&nbsp;<input type="radio" value="女" class="gender"
                                                                         name="password" > 女
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="name" class="col-sm-3 control-label">年龄</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="name"
                                                       name="name" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="gender" class="col-sm-3 control-label">职称</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="gender"
                                                       name="gender" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="telephone" class="col-sm-3 control-label">联系电话</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="telephone"
                                                       name="telephone" value="" placeholder="">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="status" class="col-sm-3 control-label">科室</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="status"
                                                       name="status" value="" placeholder="">
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
                <!-- 删除模态框示例（Modal） -->
                <form method="post" action="/admindoctor?method=delete"
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
        var username = button.data('username')
        var password = button.data('password')

        var name = button.data('name')
        var gender = button.data('gender')
        var telephone = button.data('telephone')
        var status=button.data('status')
        var modal = $(this)

        modal.find('.modal-title').text('修改医生信息')
        modal.find('#id').val(id)
        modal.find('#username').val(username)
        var list = modal.find('.password')
        for(var i=0;i<list.length;i++){
            if(gender == $(list.get(i)).val()){
                $(list.get(i)).prop('checked',true)
            }
        }
        modal.find('#name').val(name)
        modal.find('#gender').val(gender)
        modal.find('#status').val(status)
        modal.find('#telephone').val(telephone)

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
        modal.find('#deleteLabel').text('是否职工号为' + id + '的'+department+'的医生？')
        modal.find('#tno').val(id)
    })
</script>
<script>
    // 模拟数据，可以从后端获取
    var data = {
        "内科": ["消化内科", "心血管内科", "呼吸内科", "泌尿内科", "血液内科", "内分泌科", "神经内科"],
        "外科": ["胃肠外科", "肝胆外科","神经外科","骨科","皮肤科"],
        "其他": ["儿科", "妇科","产科"]
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
</script>
</body>
</html>
