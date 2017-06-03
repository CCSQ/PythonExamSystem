<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style type="text/css">
#newEditor {
	-webkit-border-radius: 0;
	-moz-border-radius: 0;
	border-radius: 0;
	height: 300px;
}
</style>

<div>

	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar bar1"></span> <span class="icon-bar bar2"></span> <span
					class="icon-bar bar3"></span>
			</button>
			<a class="navbar-brand" href="/PythonExamSystem/#/topicManage">题库管理</a>
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
							<h4 class="title">python题库</h4>
							<!-- <p class="category">本班学生列表（{{studentReddit.studentData[0].clazz}}）</p> -->
						</div>
						<div class="content table-responsive table-full-width">
							<div class="text-center">
								<button type="button" class="btn btn-primary"
									ng-click="showMode()">新 增 题 目</button>
							</div>
							<table class="table table-striped">
								<thead>
									<tr>
										<th>题目</th>
										<th>类型</th>
										<th>作者</th>
										<th>创建时间</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody infinite-scroll='topicReddit.nextPage()'
									infinite-scroll-disabled='topicReddit.busy'
									infinite-scroll-distance='100'>
									<tr ng-repeat="item in topicReddit.topicData">
										<td><a href="javascript:void(0)"
											ng-click="changeTop(item,$index)">{{ item.title }}</a></td>
										<td>{{ topTypeArr[item.topType] }}</td>
										<td>{{ item.authorName}}</td>
										<td>{{ formatTime(item.creaTime)}}</td>
										<td ng-if="teacherInfor.userId != item.authorId">无权限</td>
										<td ng-el><button type="button" class="btn btn-primary"
												ng-click="deleteTop(item,$index)">删除</button></td>
									</tr>
								</tbody>
							</table>
							<div ng-show='topicReddit.busy'>Loading data...</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="addTopModal" tabindex="-1" role="dialog"
		data-backdrop="false" aria-labelledby="addTopModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header" style="padding-left: 15%;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addTopModalLabel">
						<div class="input-group" style="padding-right: 15%;">
							<span class="input-group-addon">题目</span> <input type="text"
								class="form-control border-input" ng-model="newTopic.title">
							<div class="input-group-btn" ng-click="setTopType()">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									{{ topTypeArr[newTopic.topType] }} <span class="ti-pencil-alt2"></span>
								</button>
							</div>
						</div>
					</h4>
				</div>
				<div class="modal-body">
					<form class="bs-example bs-example-form" role="form">
						<div class="row">
							<div class="col-lg-6 col-xs-6 col-sm-6 col-md-6">
								<textarea id="editorInfor">请在此处键入说明</textarea>
							</div>
							<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6'>
								<pre id="newEditor"># 代码模板</pre>
							</div>
						</div>

						<br>
						<div class="col-lg-12 col-md-12">
							<div class="card card-user">
								<div class="text-center">
									<div class="row">
										<div class="col-md-10 col-md-offset-1">
											<h5>
												多个输入请用英文逗号隔开<br /> <small></small>
											</h5>
										</div>
									</div>
								</div>
								<hr>

								<div class="text-center">
									<div class="row" ng-repeat="item in newTopic.input">
										<div class="col-md-5 col-md-offset-1">
											<input type="text" class="form-control border-input"
												ng-model="item.input">
											<h5>
												<small>输入</small>
											</h5>
										</div>
										<div class="col-md-5">
											<input type="text" class="form-control border-input"
												ng-model="item.output">
											<h5>
												<small>输出</small>
											</h5>
										</div>
									</div>
									<button type="button" class="btn btn-default"
						ng-click="addInput()">新增输入</button>
								</div>
								<hr>
								<div class="text-center">
									<div class="row" ng-repeat="item in newTopic.keyCode">
										<div class="col-md-5 col-md-offset-1">
											<input type="text" class="form-control border-input"
												ng-model="item.keyCode">
											<h5>
												<small>奖励代码块</small>
											</h5>
										</div>
										<div class="col-md-5">
											<input type="text" class="form-control border-input"
												ng-model="item.keyScore">
											<h5>
												<small>奖励分数</small>
											</h5>
										</div>
									</div>
									<button type="button" class="btn btn-default"
						ng-click="addkeyCode()">新增代码块</button>
								</div>
								<hr>

							</div>
						</div>


					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						ng-click="cancerTopic()">取消</button>
					<button type="button" class="btn btn-primary"
						ng-click="saveTopic(newTopic, $event)"
						ng-show="!newTopic.authorId || teacherInfor.userId == newTopic.authorId"
						data-container="body" data-placement="right" data-trigger="manual"
						data-content="保存成功">保存</button>
				</div>
			</div>
		</div>
	</div>

</div>

<script>
	
</script>
