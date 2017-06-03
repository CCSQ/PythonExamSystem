<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
	<!-- 广告轮播图 -->
	<!-- <div id="AdsCarousel" class="carousel slide">
		<ol class="carousel-indicators">
			<li ng-repeat="item in adsImg" data-target="#AdsCarousel" data-slide-to="{{ item.index }}" ng-class="setStyle(1,item.index)"></li>
		</ol>
		
		<center>
			<div class="carousel-inner">
				<div ng-repeat="adItem in adsImg" ng-class="setStyle(2,adItem.index)">
					<img ng-src="${pageContext.request.contextPath}/image/ads/{{ adItem.url }}" alt="{{ adItem.alt }}">
					<div class="carousel-caption">{{ adItem.title }}</div>
				</div>
			</div>
		</center>
		
		<a class="carousel-control left" href="javascript:void(0)" ng-click="prev()">&lsaquo;</a>
		<a class="carousel-control right" href="javascript:void(0)" ng-click="next()" >&rsaquo;</a>
	</div> -->


	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar bar1"></span> <span class="icon-bar bar2"></span> <span
					class="icon-bar bar3"></span>
			</button>
			<a class="navbar-brand" href="/PythonExamSystem/#/">练习题库</a>
		</div>

	</div>
	</nav>

	<br>
	<div class="content">
		<div class="container-fluid">
			<div infinite-scroll='reddit.nextPage()'
				infinite-scroll-disabled='reddit.busy'
				infinite-scroll-distance='100'>


				<div class="row">


					<div class="col-lg-3 col-sm-6"
						ng-repeat="item in reddit.publicTopic">
						<div class="card">
							<div class="content">
								<div class="row">
									<div class="col-xs-4">
										<div class="text-center">
											<h4>{{ item.title }}</h4>

										</div>
									</div>
									<div class="col-xs-8">
										<div class="numbers">

											<a href="#/answerPublicTop" class="btn btn-primary"
												role="button" ng-click="answer(item.topId,$index)"> 作答 </a>
											<a href="javascript:void(0)" class="btn btn-default"
												role="button" ng-click="remove($index)"> X </a>
										</div>
									</div>
								</div>
								<div class="footer">
									<hr />
									<div class="stats">
										<p ng-bind-html="item.infor"></p>

									</div>
								</div>
							</div>
						</div>
					</div>



				</div>
				<div ng-show='reddit.busy'>Loading data...</div>
			</div>


		</div>
	</div>
	<!-- 公共练习题 -->

	<!-- <div>
		<hr>
		<div infinite-scroll='reddit.nextPage()' infinite-scroll-disabled='reddit.busy' infinite-scroll-distance='1'>
			<div class="row">
				<div class="col-sm-4 col-md-3" ng-repeat="item in reddit.publicTopic">
					<div class="thumbnail">
						<div class="caption">
							<h3>{{ item.title }}</h3>
							<p>{{ item.infor }}</p>
							<p>
								<a href="#/answerPublicTop" class="btn btn-primary" role="button" ng-click="answer(item.topId,$index)"> 作答 </a>
								<a href="javascript:void(0)" class="btn btn-default" role="button" ng-click="remove($index)"> 做过了 </a>
							</p>
						</div>
					</div>
				</div>
			</div>
			<div ng-show='reddit.busy'>Loading data...</div>
		</div>
	</div> -->
</div>

