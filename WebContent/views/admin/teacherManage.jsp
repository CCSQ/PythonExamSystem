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
			<a class="navbar-brand" href="/PythonExamSystem/#/topicManage">教师管理</a>
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
							<h4 class="title">教师数据</h4>
							<!-- <p class="category">本班学生列表（{{studentReddit.studentData[0].clazz}}）</p> -->
						</div>
						<div class="content table-responsive table-full-width">
							<div class="text-center">
									<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addModal">新增教师</button>
							</div>
							<table class="table table-striped">
								<thead>
									<tr> <th>ID</th> <th>姓名</th> <th>所属班级</th> <th>操作</th> </tr>
								</thead>
								<tbody infinite-scroll='teacherReddit.nextPage()' infinite-scroll-disabled='teacherReddit.busy' infinite-scroll-distance='100'>
			<tr ng-repeat="item in teacherReddit.teachetData">
				<td>{{ item.id }}</td>
				<td><input type="text" class="form-control" ng-model="item.name" disabled></td>
				<td><input type="text" class="form-control" ng-model="item.clazz" disabled></td>
				<td>
					<div class="btn-group">
						<button type="button" class="btn btn-default" ng-click="modify(item.id,$index,$event)" ng-show='!item.flag'>修改</button>
						<button type="button" class="btn btn-default" ng-click="save(item.id,$index,$event)" ng-show='item.flag'>保存</button>
						<button type="button" class="btn btn-default" ng-click="resetPwd(item.id,$index,$event)"
						data-container="body" data-placement="top" data-trigger="manual" data-content="重置成功为4个0">重置密码</button>
						<button type="button" class="btn btn-default" ng-click="delete(item.id,$index,$event)">删除</button>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div ng-show='reddit.busy'>Loading data...</div>



						</div>
					</div>
				</div>

			</div>

		</div>


	</div>


		

	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" data-backdrop="false"  aria-labelledby="addModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content bgModel">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addModalLabel">新增教师信息</h4>
				</div>
				<div class="modal-body" >
					<form class="bs-example bs-example-form" role="form">
					
					<div class="col-lg-12 col-md-12">
                        <div class="card card-user">
                       
                             
                             <div class="text-center">
                                <div class="row">
                                    <div class="col-md-10 col-md-offset-1">
                                    <br>
                                    <input type="text" class="form-control border-input" placeholder="姓  名" ng-model="newUser.name">
                                        <h5><small>姓  名</small></h5>
                                    </div>
                                    <div class="col-md-10 col-md-offset-1">
                                    <input type="text" class="form-control border-input" placeholder="所属班级" ng-model="newUser.clazz">
                                        <h5><small>所属班级</small></h5>
                                    </div>
                                </div>
                            </div>
                            
                            <hr>
                            
                            </div></div>
                            
                           
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" ng-click="cancer(newUser)">取消</button>
					<button type="button" class="btn btn-primary" ng-click="add(newUser)">增加</button>
				</div>
			</div>
		</div>
	</div>

</div>
