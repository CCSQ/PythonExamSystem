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
			<a class="navbar-brand" href="/PythonExamSystem/#/examManage">考试管理</a>
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
							<h4 class="title">考试信息</h4>
							<p class="category">已安排考试数据</p>
						</div>
						<div class="content table-responsive table-full-width">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>考试时间</th>
										<th>考试时长</th>
										<th>已考人数</th>
										<th>操 作</th>
									</tr>
								</thead>
								<tbody infinite-scroll='examReddit.nextPage()'
									infinite-scroll-disabled='examReddit.busy'
									infinite-scroll-distance='1'>
									<tr ng-repeat="item in examReddit.examData">
										<td><a href="javascript:void(0)"
											ng-click="showExamList(item,$index)">{{
												formatTime(item.beginTime)}} ~ {{formatTime(item.endTime) }}</a></td>
										<td>{{ item.timeLong}}</td>
										<td>{{ item.number}}</td>
										<td>
											<div class="btn-group">
												<button type="button" class="btn btn-default"
													ng-click="setExamType(item.id,$index,$event)">{{
													type[item.type] }}</button>
												<button type="button" class="btn btn-default"
													ng-click="changExam(item,$index,$event)">修改</button>
												<button type="button" class="btn btn-default"
													ng-click="deleteExam(item.id,$index,$event)">删除</button>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
							<div ng-show='examReddit.busy'>Loading data...</div>

							<div class="text-center">
								<button type="button" class="btn btn-primary"
									ng-click="addExam()">新 增 考 试</button>
							</div>

						</div>
					</div>
				</div>

			</div>
		</div>
	</div>



	<div class="modal fade" id="addExamModal" tabindex="-1" role="dialog"
		data-backdrop="false" aria-labelledby="addExamModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content bgModel ">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addExamModalLabel">考试信息</h4>
				</div>
				<div class="modal-body">
					<form class="bs-example bs-example-form" role="form">
						<div class="col-lg-12 col-md-12">
							<div class="card card-user">
								<div class="text-center">
									<div class="row">
										<div class="col-md-5 col-md-offset-1">
											<br>
											<input id="beginTime" type="text"
												class="form-control  border-input" ng-model="exam.beginTime">
											<h5>
												<small>开放时间(yyyy-m-d)</small>
											</h5>
										</div>
										<div class="col-md-5">
										<br>
											<input id="endTime" type="text"
												class="form-control border-input" ng-model="exam.endTime">
											<h5>
												<small>关闭时间(yyyy-m-d)</small>
											</h5>
										</div>
									</div>
								</div>
								<hr>

								<div class="text-center">
									<div class="row">
										<div class="col-md-10 col-md-offset-1">
											<input type="number" class="form-control border-input"
												ng-model="exam.timeLong">
											<h5>
												<small>考试时长</small>
											</h5>
										</div>
									</div>
								</div>
								<hr>


							</div>
						</div>


					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary"
						ng-click="addExamServer(exam)">保存</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="examStudentList" tabindex="-1"
		role="dialog" data-backdrop="false"
		aria-labelledby="examStudentListLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content bgModel ">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="examStudentListLabel">已考学生列表</h4>
				</div>
				<div class="modal-body">
					<table class="table table-hover table-striped">
						<thead>
							<tr>
								<th>ID</th>
								<th>姓名</th>
								<th>用时</th>
								<th>成绩</th>
								<th>考试时间</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="item in examList">
								<td>{{ item.studentId }}</a></td>
								<td>{{ item.studentName}}</td>
								<td>{{ item.spenTime}}</td>
								<td>{{ item.soce}}</td>
								<td>{{ formatTime(item.examTime)}}</td>
							</tr>
						</tbody>
					</table>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
				</div>
			</div>
		</div>
	</div>

</div>
