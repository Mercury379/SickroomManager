<%--
  Created by IntelliJ IDEA.
  User: HeQuan
  Date: 2023/12/22
  Time: 16:25
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
                    <form role="form" class="form-inline" action="/patientpay?method=search" method="post">
                        <div class="form-group">
                            <label for="key">支付状态</label>
                            <select name="key" class="form-control" id="key">
                                <option value=""></option>
                                <option value="已支付">已支付</option>
                                <option value="未支付">未支付</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for = "roomvalue" class="panel-heading">病房号</label>
                            <input class="form-control" type="text" name="roomvalue" placeholder="请输入病房号" id="roomvalue"/>
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
                        <th>发票号</th>
                        <th>住院号</th>
                        <th>病房</th>
                        <th>病床</th>
                        <th>入院时间</th>
                        <th>出院时间</th>
                        <th>支付金额</th>
                        <th>支付状态</th>
                        <th>支付时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="payInfo">
                        <tr>
                            <td>${payInfo.getIno()}</td>
                            <td>${payInfo.getLno()}</td>
                            <td>${payInfo.getRno()}</td>
                            <td>${payInfo.getBno()}</td>
                            <td>${payInfo.getIntime()}</td>
                            <td>${payInfo.getOuttime()}</td>
                            <td>${payInfo.getMoney()}</td>
                            <td>${payInfo.getStatus()}</td>
                            <td>${payInfo.getPaytime()}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-danger "
                                            data-itime="${payInfo.getTime()}"
                                            data-ino="${payInfo.getIno()}"
                                            data-lno="${payInfo.getLno()}"
                                            data-rno="${payInfo.getRno()}"
                                            data-bno="${payInfo.getBno()}"
                                            data-roomstandard="${payInfo.getStandard()}"
                                            data-intime="${payInfo.getIntime()}"
                                            data-outime="${payInfo.getOuttime()}"
                                            data-days="${payInfo.getDays()}"
                                            data-pno="${payInfo.getPno()}"
                                            data-pname="${payInfo.getName()}"
                                            data-money="${payInfo.getMoney()}"
                                            data-status="${payInfo.getStatus()}"
                                            data-hospital="协济医院"
                                            data-toggle="modal"
                                            onclick="" data-target="#updateUserModal">
                                        <i class="fa fa-search">打印</i>
                                    </button>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-success "
                                            data-ino="${payInfo.getIno()}"
                                            data-money="${payInfo.getMoney()}"
                                            data-status="${payInfo.getStatus()}"
                                            data-toggle="modal"
                                            onclick="" data-target="#payModal">
                                        <i class="fa fa-ticket">支付</i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <form method="post" action="/patientpay?method=print" class="form-horizontal"
                      style="margin-top: 0px" role="form" id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">发票信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="itime" class="col-sm-3 control-label">发票产生时间</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="itime"
                                                       name="itime">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="ino" class="col-sm-3 control-label">发票号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="ino"
                                                       name="ino">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="lno" class="col-sm-3 control-label">住院号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="lno"
                                                       name="lno" placeholder="请输入用户名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="rno" class="col-sm-3 control-label">住院病房号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="rno"
                                                       name="rno" value="" placeholder="请输入密码">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="bno" class="col-sm-3 control-label">住院病床号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="bno"
                                                       name="bno" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="roomstandard" class="col-sm-3 control-label">收费标准(元/天)</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="roomstandard"
                                                       name="roomstandard" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="intime" class="col-sm-3 control-label">入院时间</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="intime"
                                                       name="intime" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>、

                                        <div class="form-group">
                                            <label for="outtime" class="col-sm-3 control-label">出院时间</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="outtime"
                                                       name="outtime" value="" placeholder="">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="days" class="col-sm-3 control-label">住院天数(天)</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="days"
                                                       name="days" value="" placeholder="">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="pno" class="col-sm-3 control-label">病案号</label>
                                                <div class="col-sm-9">
                                                    <input type="text" readonly class="form-control" id="pno"
                                                           name="pno" value="" placeholder="未支付">
                                                </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="name" class="col-sm-3 control-label">客户名称</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="name"
                                                       name="name" value="" placeholder="未支付">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="money" class="col-sm-3 control-label">收费金额(元)</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="money"
                                                       name="money" value="" placeholder="未支付">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="hospital" class="col-sm-3 control-label">开票单位</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="hospital"
                                                       name="hospital" value="" placeholder="未支付">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                            <button type="submit" class="btn btn-primary">确定打印该发票</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <form method="post" action="/patientpay?method=pay" class="form-horizontal"
                      style="margin-top: 0px" role="form" id="form_data1" style="margin: 20px;">
                    <div class="modal fade" id="payModal" tabindex="-1" role="dialog"
                         aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel1">发票信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="invoice" class="col-sm-3 control-label">发票号</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="invoice"
                                                       name="invoice">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="paymoney" class="col-sm-3 control-label">收费金额(元)</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="paymoney"
                                                       name="paymoney" value="" placeholder="未支付">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="paystatus" class="col-sm-3 control-label">支付状态</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly class="form-control" id="paystatus"
                                                       name="paystatus" value="" placeholder="未支付">
                                            </div>
                                        </div>
                                        <div class="form-group text-center">
                                            <img src="images/pay.png" width="300">
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
                <!-- 新的 Modal 用于显示已支付的提示信息 -->
                <div class="modal fade" id="paymentAlertModal" tabindex="-1" role="dialog" aria-labelledby="paymentAlertModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="paymentAlertModalLabel">支付状态提示</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                已支付，不能再重复进行支付！
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 新的 Modal 用于显示已支付的提示信息 -->
                <div class="modal fade" id="paymentAlertModal1" tabindex="-1" role="dialog" aria-labelledby="paymentAlertModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="paymentAlertModalLabel1">发票打印失败提示</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                未支付，不能打印该发票！
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
        var itime = button.data('itime')
        var ino = button.data('ino')
        var lno = button.data('lno')
        var rno = button.data('rno')
        var bno = button.data('bno')
        var standard = button.data('roomstandard')
        var intime = button.data('intime')
        var outtime = button.data('outime')
        var days = button.data('days')
        var pno = button.data('pno')
        var name = button.data('pname')
        var money = button.data('money')
        var hospital = button.data('hospital')
        var paystatus=button.data('status')
        var modal = $(this)

        modal.find('.modal-title').text('住院费用发票')
        modal.find('#itime').val(itime)
        modal.find('#ino').val(ino)
        modal.find('#lno').val(lno)
        modal.find('#rno').val(rno)
        modal.find('#bno').val(bno)
        modal.find('#roomstandard').val(standard)
        modal.find('#intime').val(intime)
        modal.find('#outtime').val(outtime)
        modal.find('#days').val(days)
        modal.find('#name').val(name)
        modal.find('#pno').val(pno)
        modal.find('#money').val(money)
        modal.find('#hospital').val(hospital)
        var list = modal.find('.gender')
        for(var i=0;i<list.length;i++){
            if(gender == $(list.get(i)).val()){
                $(list.get(i)).prop('checked',true)
            }
        }
        // 添加判断支付状态的条件
        if (paystatus === '未支付') {
            // 显示已支付的提示信息Modal
            $('#paymentAlertModal1').modal('show');

            // 阻止模态框显示
            event.preventDefault();

            // 关闭原始的删除Modal
            $('#updateUserModal').modal('hide');
        }
    })

    $('#payModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var ino = button.data('ino')
        var paymoney = button.data('money')
        var paystatus = button.data('status')
        var modal = $(this)
        modal.find('.modal-title').text('支付账单')
        modal.find('#invoice').val(ino)
        modal.find('#paymoney').val(paymoney)
        modal.find('#paystatus').val(paystatus)
        // 添加判断支付状态的条件
        if (paystatus === '已支付') {
            // 显示已支付的提示信息Modal
            $('#paymentAlertModal').modal('show');

            // 阻止模态框显示
            event.preventDefault();

            // 关闭原始的删除Modal
            $('#payModal').modal('hide');
        }
    })
</script>
</body>
</html>
