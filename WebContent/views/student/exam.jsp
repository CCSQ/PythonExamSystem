<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
	    <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar bar1"></span>
                        <span class="icon-bar bar2"></span>
                        <span class="icon-bar bar3"></span>
                    </button>
                    <a class="navbar-brand">考试</a>
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
							<p class="category">已安排考试列表</p>
						</div>
						<div class="content table-responsive table-full-width">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>考试时间</th>
										<th>考试时长</th>
										<th>状态</th>
										<th>操 作</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="item in examData">
										<td>{{ formatTime(item.beginTime)}} ~
											{{formatTime(item.endTime) }}</td>
										<td>{{ item.timeLong}}</td>
										<td>{{ getType(item)}}</td>
										<td>
											<div class="btn-group">
												<button id="{{ item.id }}" type="button"
													class="btn btn-default"
													ng-click="showExam(item,$index,$event)">查看试卷</button>
												<button type="button" class="btn btn-default"
													ng-click="beginExam(item,$index,$event)"
													ng-show="{{ getCanExam(item) }}">开始考试</button>
												<!-- <button type="button" class="btn btn-default" ng-click="deleteExam(item.id,$index,$event)">删除</button> -->
											</div>
										</td>
									</tr>
								</tbody>
							</table>

						</div>
					</div>
				</div>

			</div>
		</div>
	</div>



	<div class="modal fade" id="examModal" tabindex="-1" role="dialog" data-backdrop="false" aria-labelledby="examModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="examModalLabel">试卷</h4>
				</div>
				<div class="modal-body">
					<form class="bs-example bs-example-form" role="form">
					
					
					
					<div class="col-lg-12 col-md-12">
                        <div class="card card-user">
                            <div class="text-center">
                                <div class="row">
                                    <div class="col-md-10 col-md-offset-1">
                                        <h5>{{ formatTime(oneExamInfor.examTime) }}<br /><small>考试时间</small></h5>
                                    </div>
                                </div>
                            </div>
                             <hr>
                             
                             <div class="text-center">
                                <div class="row">
                                    <div class="col-md-5 col-md-offset-1">
                                        <h5>{{ oneExamInfor.spenTime }}分钟<br /><small>花费时长</small></h5>
                                    </div>
                                    <div class="col-md-5">
                                        <h5>{{ oneExamInfor.soce }}<br /><small>成绩</small></h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
<div class="col-lg-4 col-md-5">
                     <div class="card card-user">
                            <div class="content">
                           
                            <p ng-bind-html="oneExamDataItem.topInfor"></p>
                                
                            </div>
                            <hr>
                            <div class="text-center">
                                <div class="row">
                                     <center>
                             <h3 >{{oneExamDataItem.topTitle}}</h3>
                            </center>
                                </div>
                            </div>
                        </div>
</div>

<div class="col-lg-8 col-md-7">
                     <div class="card card-user">
                            <div class="content">
                           
                            <pre id="code" ace-mode="ace/mode/python" ace-theme="ace/theme/chrome" ace-gutter="true">{{oneExamDataItem.code}}</pre>
                                
                            </div>
                            <hr>
                            <div class="text-center">
                                <div class="row">
                                     <center>
                             <h3 >代码</h3>
                            </center>
                                </div>
                            </div>
                        </div>
</div>

</div>

<div class="col-lg-12 col-md-12">
                        <div class="card card-user">
                            
                             <div class="text-center">
                                <div class="row">
                                    <div class="col-md-5 col-md-offset-1">
                                        <h5>{{inputString}}<br /><small>输入</small></h5>
                                    </div>
                                    <div class="col-md-5">
                                        <h5>{{outputString}}<br /><small>结果</small></h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

							<center>
								<div class="input-group col-lg-8 col-xs-8 col-sm-8 col-md-8">
									<span class="input-group-btn">
										<button id="pre" class="btn btn-default" type="button" ng-click="preItem()">上一题</button>
									</span>
									
									<div class="text-center">
                                <div class="row">
                                    <div class="col-md-10 col-md-offset-1">
                                        <h5>{{ oneExamDataItem.scoe }}<br /><small>本题得分</small></h5>
                                    </div>
                                </div>
                            </div>
                            
									
									<span class="input-group-btn">
										<button id="next" class="btn btn-default" type="button" ng-click="nextItem()">下一题</button>
									</span>
								</div>
							</center>
						</div>


					</form>
					
				</div>
				<!-- <div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
				</div> -->
			</div>
		</div>
	</div>

</div>
