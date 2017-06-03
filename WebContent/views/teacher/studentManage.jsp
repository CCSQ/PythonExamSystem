<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>


	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar bar1"></span> <span class="icon-bar bar2"></span> <span
					class="icon-bar bar3"></span>
			</button>
			<a class="navbar-brand" href="/PythonExamSystem/#/studentManage">学生管理</a>
		</div>

	</div>
	</nav>
	<br>

	<div class="content">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-12">
					<div class="card">
						<div class="header">
							<h4 class="title">学生信息</h4>
							<p class="category">本班学生列表（{{studentReddit.studentData[0].clazz}}）</p>
						</div>
						<div class="content table-responsive table-full-width">
							
								<table class="table table-striped" ALIGN="CENTER">
									<thead>
										<tr>
											<th>ID</th>
											<th>姓名</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody infinite-scroll='studentReddit.nextPage()'
										infinite-scroll-disabled='studentReddit.busy'
										infinite-scroll-distance='100'>
										<tr ng-repeat="item in studentReddit.studentData">
											<td>{{ item.id }}</td>
											<td>{{ item.name}}</td>
											<td>
												<div class="btn-group">
													<button type="button" class="btn btn-default"
														ng-click="resetStuPwd(item.id,$index,$event)"
														data-container="body" data-placement="top"
														data-trigger="manual" data-content="重置成功为4个0">重置密码</button>
													<button type="button" class="btn btn-default"
														ng-click="deleteStu(item.id,$index,$event)">删除</button>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							
							<div ng-show='studentReddit.busy'>Loading data...</div>

							<div class="text-center">
								<button type="button" class="btn btn-primary"
									data-toggle="modal" data-target="#addStudentModal">新 增
									学 生</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="addStudentModal" tabindex="-1"
		role="dialog" data-backdrop="false"
		aria-labelledby="aaddStudentModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content bgModel ">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="aaddStudentModalLabel">新增学生信息</h4>
				</div>
				<div class="modal-body" style="padding-left: 15%;">
					<form class="bs-example bs-example-form" role="form">


						<div class="row">
							<div class="col-lg-10 col-xs-10 col-sm-10 col-md-10">
								<div class="input-group"'>
									<div class="input-group-btn">
										<label>pwd</label>
										<button type="button" class="btn btn-default ">姓 名</button>
									</div>
									<div class="form-group">
										<input type="text" class="form-control border-input"
											placeholder="姓  名" ng-model="newStudentUser.name">
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						ng-click="cancerStudent(newStudentUser)">取消</button>
					<button type="button" class="btn btn-primary"
						ng-click="addStudent(newStudentUser)">增加</button>
				</div>
			</div>
		</div>
	</div>
</div>
