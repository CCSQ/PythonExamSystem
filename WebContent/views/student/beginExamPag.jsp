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
				<div class="col-lg-4 col-md-5">
					<div class="card card-user">
						<div class="content">

							<p ng-bind-html="examListItem.topInfor "></p>

						</div>
						<hr>
						<div class="text-center">
							<div class="row">
								<center>
									<h3> {{ examListItem.topTitle }} ( {{index + 1}} / 10 )</h3>
								</center>
							</div>
						</div>
					</div>
				</div>

				<div class="col-lg-8 col-md-7">
					<div class="card">
						<div class="header">
							<h4 class="title">代码</h4>
						</div>
						<hr>
						<div class="content">
							<form>

								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div id='editorDiv'>
												<pre id="examEditor"></pre>
											</div>
										</div>
									</div>
								</div>
								<div class="clearfix"></div>
							</form>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>





	<center>
		<div class="input-group col-lg-8 col-xs-8 col-sm-8 col-md-8">
			<span class="input-group-btn"><button id="pre" class="btn btn-default" type="button" ng-click="preItem()">上一题</button></span>
			<span class="input-group-btn"><button class="btn btn-default" type="button" ng-click="sublimeExam()">提 交 试 卷 {{ nowTime }} / {{ examtime }} 分钟</button></span>
			<span class="input-group-btn"><button id="next" class="btn btn-default" type="button" ng-click="nextItem()">下一题</button></span>
		</div>
	</center>

</div>
