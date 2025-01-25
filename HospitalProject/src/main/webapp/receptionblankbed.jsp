<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/23
  Time: 14:57
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
                    <form role="form" class="form-inline" action="/rblankbed?method=search" method="post">
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
                            <label for = "rno" class="panel-heading">病房号</label>
                            <input class="form-control" type="text" name="rno" placeholder="输入病房号" id="rno"/>
                        </div>
                        <div class="form-group">
                            <label for = "searchdatevalue" class="panel-heading">查询该日期的空病房</label>
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
                        <th>病房号</th>
                        <th>病床号</th>
                        <th>收费标准(元/天)</th>
                        <th>所属科室</th>
                        <th>病床状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="blankBed">
                        <tr>
                            <td>${blankBed.getRNo()}</td>
                            <td>${blankBed.getBNo()}</td>
                            <td>${blankBed.getStandard()}</td>
                            <td>${blankBed.getDepartment()}</td>
                            <td>${blankBed.getStatus()}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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

        modal.find('.modal-title').text('修改宿管信息')
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

    $('#delUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var modal = $(this)
        modal.find('.modal-title').text('删除宿管信息')
        modal.find('#deleteLabel').text('是否删除ID为  ' + id + ' 的信息')
        modal.find('#id').val(id)
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
</script>
</body>
</html>
