<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/25
  Time: 20:18
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
                            <label for="key">就诊状态</label>
                            <select name="key" class="form-control" id="key">
                                <option value=""></option>
                                <option value="已就诊">已就诊</option>
                                <option value="未就诊">未就诊</option>
                            </select>
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
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addUserModal">
										<span style="margin-right: 5px" class="" aria-hidden="true">
											<i class="fa fa-user-plus">挂号</i>
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
                        <th>挂号时间</th>
                        <th>挂号号</th>
                        <th>挂号科室</th>
                        <th>挂号医生</th>
                        <th>挂号费用</th>
                        <th>就诊状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="treatInfo">
                        <tr>
                            <td>${treatInfo.getTreattime()}</td>
                            <td>${treatInfo.getTno()}</td>
                            <td>${treatInfo.getDepartment()}</td>
                            <td>${treatInfo.getDname()}</td>
                            <td>${treatInfo.getRegistermoney()}</td>
                            <td>${treatInfo.getStatus()}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default "
                                            data-id="${treatInfo.getTreattime()}"
                                            data-username="${treatInfo.getTno()}"
                                            data-password="${treatInfo.getPno()}"
                                            data-name="${treatInfo.getName()}"
                                            data-gender="${treatInfo.getDepartment()}"
                                            data-telephone="${treatInfo.getDname()}"
                                            data-status="${treatInfo.getStatus()}"
                                            data-toggle="modal"
                                            data-target="#updateUserModal">
                                        <i class="fa fa-user-o">打印挂号单</i>
                                    </button>
                                    <button type="button" class="btn btn-danger "
                                            data-did="${treatInfo.getTno()}"
                                            data-department="${treatInfo.getDepartment()}"
                                            data-dname="${treatInfo.getDname()}"
                                            data-status="${treatInfo.getStatus()}"
                                            data-toggle="modal"
                                            onclick="" data-target="#delUserModal">
                                        <i class="fa fa-user-times">撤销挂号</i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- add框示例（Modal） -->
                <form method="post" action="/pregister?method=register" class="form-horizontal" style="margin-top: 0px" role="form"
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

                                        <div class="form-group">
                                            <label for="student" class="col-sm-3 control-label">挂号医生</label>
                                            <div class="col-sm-9">
                                                <select id="student" required class="form-control" name="studentId">
                                                    <%--                                                    //<option value=""></option>--%>
                                                    <%--                                                    <c:forEach items="${studentList}" var="student">--%>
                                                    <%--                                                        <option value="${student.id}">${student.name}</option>--%>
                                                    <%--                                                    </c:forEach>--%>

                                                </select>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-primary">确认挂号</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <%--                打印--%>
                <form method="post" action="/pregister?method=print" class="form-horizontal"
                      style="margin-top: 0px" role="form" id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">挂号信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="id" class="col-sm-3 control-label">挂号时间</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="id"
                                                       name="id">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="username" class="col-sm-3 control-label">挂号号</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="username"
                                                       name="username" placeholder="请输入用户名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="password" class="col-sm-3 control-label">病案号</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="password"
                                                       name="password" value="" placeholder="请输入密码">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="name" class="col-sm-3 control-label">挂号姓名</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="name"
                                                       name="name" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="gender" class="col-sm-3 control-label">挂号科室</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="gender"
                                                       name="gender" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="telephone" class="col-sm-3 control-label">就诊医生</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="telephone"
                                                       name="telephone" value="" placeholder="">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                            <button type="submit" class="btn btn-primary">确定打印该挂号单</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- 删除模态框示例（Modal） -->
                <form method="post" action="/pregister?method=delete"
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
                <!-- 新的 Modal 用于显示已支付的提示信息 -->
                <div class="modal fade" id="paymentAlertModal" tabindex="-1" role="dialog" aria-labelledby="paymentAlertModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="paymentAlertModalLabel">挂号单打印失败提示</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                该挂号信息已就诊，不能再进行打印！
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 新的 Modal 用于显示已支付的提示信息 -->
                <div class="modal fade" id="paymentAlertModal1" tabindex="-1" role="dialog" aria-labelledby="paymentAlertModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="paymentAlertModalLabel1">撤销挂号失败提示</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                该挂号信息已就诊，不能再撤销！
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
        var name = button.data('name')
        var gender = button.data('gender')
        var telephone = button.data('telephone')
        var status=button.data('status')
        var modal = $(this)

        modal.find('.modal-title').text('打印挂号信息')
        modal.find('#id').val(id)
        modal.find('#username').val(username)
        modal.find('#password').val(password)
        modal.find('#name').val(name)
        modal.find('#gender').val(gender)
        modal.find('#telephone').val(telephone)
        if (status === '已就诊') {
            // 显示已支付的提示信息Modal
            $('#paymentAlertModal').modal('show');

            // 阻止模态框显示
            event.preventDefault();

            // 关闭原始的删除Modal
            $('#updateUserModal').modal('hide');
        }
    })

    $('#delUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('did')
        var department = button.data('department')
        var dname = button.data('dname')
        var status=button.data('status')
        var modal = $(this)
        modal.find('.modal-title').text('撤销该挂号信息')
        modal.find('#deleteLabel').text('是否撤销编号为' + id + '的'+department+'的'+dname+'的挂号？')
        modal.find('#tno').val(id)
        if (status === '已就诊') {
            // 显示已支付的提示信息Modal
            $('#paymentAlertModal1').modal('show');

            // 阻止模态框显示
            event.preventDefault();

            // 关闭原始的删除Modal
            $('#delUserModal').modal('hide');
        }
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
    $(function(){
        $('#dormitory').change(function(){
            var id = $(this).val();
            $.ajax({
                url:"/pregister?method=findByDormitoryId&dormitoryId="+id,
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

