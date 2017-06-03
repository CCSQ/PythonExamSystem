
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/default.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datepicker/css/bootstrap-datetimepicker.min.css">


<!-- Bootstrap core CSS     -->
<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
<!-- Animation library for notifications   -->
<link href="assets/css/animate.min.css" rel="stylesheet" />
<!--  Paper Dashboard core CSS    -->
<link href="assets/css/paper-dashboard.css" rel="stylesheet" />
<!--  CSS for Demo Purpose, don't include it in your project     -->
<link href="assets/css/demo.css" rel="stylesheet" />
<link href="assets/css/themify-icons.css" rel="stylesheet">
<style>
.sidebar[data-background-color="black"]:before {
	background-color: red!imortant;
}

.navbar-default {
	background: url(./image/ads/e.jpg);
	background-size: 100% 100%;
}

.col-lg-3 .card .content {
	background: url(./image/ads/c.jpg) center center;
}

.col-xs-4 h4 {
	color: #6e776e;
}

.card .footer hr {
	border-color: #A2E3F2 !important;
}

.card .footer div p {
	color: #756e6e;
}

.navbar .navbar-nav>li>a.btn {
	border-width: 1px !important;
}

.modal-title {
	font-weight: 500 !important;
}

.bgModel {
	background: url(./image/ads/f3.jpg) -483px -262px;
	opcity: 0.8;
}

.bgModel .modal-topic {
	background: #fff !important;
}

.navbar .navbar-nav>li>a.btn-primary, .btn-primary {
	border-color: #66615B !important;
	color: #66615B !important;
}

.input-group-btn .btn {
	width: 6em;
	border: none;
}

.modal-footer {
	border: none !important;
}

.sidebar .sidebar-wrapper {
	background: #56a4a4;
}

.sidebar .sidebar-wrapper a {
	color: black !important;
}

.sidebar .sidebar-wrapper .active a {
	color: black !important;
}

.btn {
	border-radius: 0px;
}

.modal-content {
	border-radius: 0px;
}

@media ( min-width : 768px) bootstrap.min.css:5 .modal-content {
	border-radius
	
	
	:
	
	 
	
	0
	px
	
	
	;
}
</style>

<title>Python在线考试系统</title>
</head>
<body ng-app='app'>
	<div class="wrapper">
		<div class="sidebar" data-background-color="black"
			data-active-color="danger">
			<div class="sidebar-wrapper">
				<div class="logo">
					<a href="#/" class="simple-text"> Python在线考试系统 </a>
				</div>

				<ul class="nav">
					<li class="active"><a href="#/">练习题库</a></li>
					<%
						String type = (String) session.getAttribute("UserType");
						if (null == type) {

						} else if (type.equals("admin")) {
					%>
					<li><a href="#/teacherManage">教师管理</a></li>
					<li><a href="#">主页管理</a></li>
					<%
						} else if (type.equals("teacher")) {
					%>
					<li><a href="#/studentManage">学生管理</a></li>
					<li><a href="#/topicManage">题库管理</a></li>
					<li><a href="#/examManage">考试管理</a></li>
					<li><a href="#">学生成绩排行</a></li>
					<%
						} else if (type.equals("student")) {
					%>
					<li><a href="#/exam">考试</a></li>
					<%
						}
					%>

					<%
						String name = (String) session.getAttribute("UserName");
						if (null == name) {
					%>
					<li class="active-pro"><a href="javascript:void(0)"
						data-toggle="modal" data-target="#loginModal"><span
							class="glyphicon glyphicon-log-in"></span> 登录</a></li>
					<%
						} else {
					%>

					<li><a href="javascript:void(0)" ng-click="changPWD()"><span
							class="glyphicon glyphicon-cog"></span> 修改密码</a></li>
					<li><a href="javascript:void(0)" ng-click="loginout()"><span
							class="glyphicon glyphicon-log-in"></span> 退出</a></li>
					<li class="active-pro"><a>欢迎登陆：<span> <%
 	out.print(name);
 %>
						</span></a></li>
					<%
						}
					%>
				</ul>
			</div>
		</div>

		<div class="main-panel">
			<div ng-view></div>

			<footer class="footer">
			<div class="container-fluid">
				<div class="copyright pull-right">
					&copy;
					<script>
						document.write(new Date().getFullYear())
					</script>
					, <a href="#/">PYTHON考试系统</a>
				</div>
			</div>
			</footer>
		</div>
	</div>

	<!-- 登陆模态框 -->
	<div ng-controller="login_form" class="modal fade" id="loginModal"
		tabindex="-1" role="dialog" aria-labelledby="loginModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div id="login" class="modal-content bgModel ">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="loginModalLabel">登陆</h4>
				</div>
				<div class="modal-body" style="padding-left: 15%;">
					<form class="bs-example bs-example-form" role="form">
						<div class="row">
							<div class="col-lg-10 col-xs-10 col-sm-10 col-md-10">
								<div class="input-group"'>

									<div class="input-group-btn" ng-click="setType()">
										<label>Username</label>
										<button type="button" class="btn btn-default dropdown-toggle"
											data-toggle="dropdown">
											{{ typeArr[user.type] }} <span class="ti-layers"></span>
										</button>
										<!-- <ul class="dropdown-menu" role="menu">
											<li><a ng-click="setType('student')">学生</a></li>
											<li><a ng-click="setType('teacher')">教师</a></li>
											<li><a ng-click="setType('admin')">管理员</a></li>
										</ul> -->

									</div>

									<div class="form-group">
										<input type="text"
											class="form-control popover-hide border-input"
											placeholder="账号" ng-model="user.id"
											ng-blur="checkUser(user.id,user.type)" ng-focus="focus()"
											data-container="body" data-placement="right"
											data-trigger="manual" data-content="账号不存在">

									</div>

								</div>
							</div>
						</div>

						<br>

						<div class="row">
							<div class="col-lg-10 col-xs-10 col-sm-10 col-md-10">
								<div class="input-group"'>

									<div class="input-group-btn">
										<label>pwd</label>
										<button type="button" class="btn btn-default ">密 码</button>
									</div>
									<div class="form-group">
										<input id="pwd" type="password"
											class="form-control border-input" placeholder="密码"
											ng-model="user.pwd" ng-focus="focuspwd()"
											data-container="body" data-placement="right"
											data-trigger="manual" data-content="密码不正确">
									</div>
								</div>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						ng-click="clear(user)">取消</button>
					<button id="loginBtn" type="button" class="btn btn-primary"
						ng-click="submit(user)" disabled="disabled">登陆</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改密码 -->
	<div class="modal fade" id="chgPwdModal" tabindex="-1" role="dialog"
		aria-labelledby="chgPwdModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content bgModel ">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="chgPwdModalLabel">修改密码</h4>
				</div>
				<div class="modal-body" style="padding-left: 15%;">
					<form class="bs-example bs-example-form" role="form">

						<div class="row">
							<div class="col-lg-10 col-xs-10 col-sm-10 col-md-10">
								<div class="input-group"'>

									<div class="input-group-btn">
										<label>pwd</label>
										<button type="button" class="btn btn-default ">旧 密 码
										</button>
									</div>
									<div class="form-group">
										<input id="oldP" type="password"
											class="form-control border-input" ng-model="pwd.oldPwd"
											data-container="body" data-placement="right"
											data-trigger="manual" data-content="旧密码不正确">
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-lg-10 col-xs-10 col-sm-10 col-md-10">
								<div class="input-group"'>

									<div class="input-group-btn">
										<label>pwd</label>
										<button type="button" class="btn btn-default ">新 密 码
										</button>
									</div>
									<div class="form-group">
										<input type="password" class="form-control border-input"
											ng-model="pwd.newPwd">
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-lg-10 col-xs-10 col-sm-10 col-md-10">
								<div class="input-group"'>

									<div class="input-group-btn">
										<label>pwd</label>
										<button type="button" class="btn btn-default ">再输一遍</button>
									</div>
									<div class="form-group">
										<input id="chickPw" type="password"
											class="form-control border-input" ng-model="pwd.chickPwd"
											data-container="body" data-placement="right"
											data-trigger="manual" data-content="密码不一致">

									</div>
								</div>
							</div>
						</div>
					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="submit" type="button" class="btn btn-primary"
						ng-click="submitPWD(pwd)">确定</button>
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angular.min.js"></script>
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angular-route.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angular-sanitize.min.js"></script>
<script src="assets/js/chartist.min.js"></script>

<script src="assets/js/paper-dashboard.js"></script>




<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ng-infinite-scroll.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script
	src="${pageContext.request.contextPath}/js/datepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/highlight.pack.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ace/ace.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ace/ext-static_highlight.js"></script>
<script>
	hljs.initHighlightingOnLoad();
</script>
<script
	src="${pageContext.request.contextPath}/js/tinymce/tinymce.min.js"></script>
<script src="assets/js/bootstrap-notify.js"></script>
<script src="assets/js/bootstrap-checkbox-radio.js"></script>
<script src="assets/js/demo.js"></script>

<script
	src="${pageContext.request.contextPath}/js/datepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/app.js"></script>

<script>
	$(function(document) {
		$("li").click(function() {
			$(this).addClass("active").siblings().removeClass("active")
		})
	})
</script>

</html>