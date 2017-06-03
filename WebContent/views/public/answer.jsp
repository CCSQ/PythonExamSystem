<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 公共答题界面 -->
<style>
#editor {
	margin: 0;
	position: absolute;
	top: 0;
	/* bottom: 0; */
	left: 0;
	right: 0;
}

#editorDiv {
	margin: 0;
	/* position: absolute; */
	top: 0;
	bottom: 0;
	/* left: 0; */
	right: 0;
	height: 500px;
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
			<a class="navbar-brand">答题</a>
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

							<p ng-bind-html="topic.infor"></p>

						</div>
						<hr>
						<div class="text-center">
							<div class="row">
								<center>
									<h3>{{ topic.title }}</h3>
								</center>
							</div>
						</div>
					</div>
					<div class="card">
						<div class="header">
							<center>
								<h4 class="title">输出</h4>
							</center>
						</div>
						<hr>
						<div class="content">
							<ul class="list-unstyled team-members">
								<li>
									<div class="row">
										<p ng-bind-html="infor"></p>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>

				<div class="col-lg-8 col-md-7">
					<div class="card">
						<div class="header">
							<h4 class="title">代码</h4>
						</div>
						<div class="content">
							<form>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div id='editorDiv'>
												<pre id="editor" ng-model="topic.codeTemp"></pre>
											</div>
										</div>
									</div>
								</div>
								<br><br>
								<div class="text-center">
									<button type="button" class="btn btn-info btn-fill"
										ng-click="sublimeCode()">提交</button>
									<button type="button" class="btn btn-info"
										ng-click="resetCode()">重置</button>
								</div>
								<div class="clearfix"></div>
							</form>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>


	<!-- 
	<div class="row" ng-show="show">
		<div class="alert alert-success"></div>
	</div>
	<div class="row">
		<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6'>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">{{ topic.title }}</h3>
				</div>
				<div class="panel-body">
					{{ topic.infor }}
				</div>
			</div>
		</div>

		<div class='col-xs-6 col-sm-6 col-md-6 col-lg-6'>
			
			<div id='editorDiv'>
				<pre id="editor" ng-model="topic.codeTemp"></pre>
			</div>
		</div>
	</div>
	<div class="btn-group col-xs-5 col-sm-5 col-md-5 col-lg-5 pull-right">
		
	</div>
</div> -->


	<script type="text/javascript-lazy">
	
</script>