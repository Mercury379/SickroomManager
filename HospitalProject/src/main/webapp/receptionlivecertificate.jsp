<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/23
  Time: 15:15
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
                    <form role="form" class="form-inline" action="/rlive?method=search" method="post">
                        <div class="form-group">
                            <label for="key">病房所属科室</label>
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
                        <div class="form-group">
                            <label for = "searchidvalue" class="panel-heading">住院号</label>
                            <input class="form-control" type="text" name="searchidvalue" placeholder="请填写病人住院号" id="searchidvalue"/>
                        </div>
                        <div class="form-group">
                            <label for = "searchdatevalue" class="panel-heading">查询日期</label>
                            <input class="form-control" type="date" name="searchdatevalue" placeholder="请选择日期" id="searchdatevalue"/>
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
                        <th>病人姓名</th>
                        <th>医生姓名</th>
                        <th>就诊科室</th>
                        <th>建议入住时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="live">
                        <tr>
                            <td>${live.getTime()}</td>
                            <td>${live.getLno()}</td>
                            <td>${live.getName()}</td>
                            <td>${live.getDname()}</td>
                            <td>${live.getDepartment()}</td>
                            <td>${live.getPreintime()}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-danger "
                                            data-telephone="${live.getLno()}"
                                            data-toggle="modal"
                                            onclick="" data-target="#addUserModal">
                                        <i class="fa fa-search">办理入院</i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- add框示例（Modal） -->
                <form method="post" action="/rlive?method=live" class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="addUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel2">办理入院</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="form-group">
                                                <label for="telephone" class="col-sm-3 control-label">住院号</label>
                                                <div class="col-sm-9">
                                                    <input type="text" required class="form-control" id="telephone"
                                                           name="telephone" value="" placeholder="">
                                                </div>
                                            </div>
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
                                            <label for="dormitory" class="col-sm-3 control-label">病房所属科室</label>
                                            <div class="col-sm-9">
                                                <select id="dormitory" required class="form-control" name="dormitoryId">
                                                    <option value=""></option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="student" class="col-sm-3 control-label">科室下病房号</label>
                                            <div class="col-sm-9">
                                                <select id="student" required class="form-control" name="studentId">
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="bno" class="col-sm-3 control-label">空闲病床号</label>
                                            <div class="col-sm-9">
                                                <select id="bno" required class="form-control" name="bno">
                                                </select>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-primary">确认入院</button>
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
    $('#addUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var telephone = button.data('telephone')
        var modal = $(this)

        modal.find('.modal-title').text('住院办理')
        modal.find('#telephone').val(telephone)
    })
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
                url:"/rlive?method=findByDormitoryId&dormitoryId="+id,
                type:"post",
                dataType:"json",
                success:function (data) {
                    var str = '';
                    for(var i = 0;i<data.length;i++){
                        var student = $(data).get(i);
                        str += '<option value="'+student.RNo+'">'+student.RNo+'</option>'
                    }
                    $('#student').html(str);
                }
            })
        })
    })
    $(function(){
        $('#student').change(function(){
            var id = $(this).val();
            $.ajax({
                url:"/rlive?method=findByDormitoryId1&studentId="+id,
                type:"post",
                dataType:"json",
                success:function (data) {
                    var str = '';
                    for(var i = 0;i<data.length;i++){
                        var student = $(data).get(i);
                        str += '<option value="'+student.BNo+'">'+student.BNo+'</option>'
                    }
                    $('#bno').html(str);
                }
            })
        })
    })
</script>
</body>
</html>
