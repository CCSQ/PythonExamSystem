

var app=angular.module("app",['ngRoute','infinite-scroll','ngSanitize'])
/* 公共主页 */
app.controller("public_index", function($scope, $http, $rootScope, Reddit, dataServer) {
	$scope.reddit = new Reddit();
	// 获取轮播图
	$http.get("./public/index")
	.then(function success(response) {
		$scope.adsImg=[
			{index:0,url:"1.jpg",alt:'图片1', title:'标题1'},
			{index:1,url:"2.jpg",alt:'图片2', title:'标题2'},
			{index:2,url:"3.jpg",alt:'图片3', title:'标题3'},
			{index:3,url:"4.jpg",alt:'图片4', title:'标题4'},
			{index:4,url:"5.jpg",alt:'图片5', title:'标题5'},
			{index:5,url:"6.jpg",alt:'图片6', title:'标题6'},
		]
		$("#AdsCarousel").carousel('cycle');
	},function error(response) {
		console.log("请求失败")
	})
	
	// 装填题目信息
	$scope.answer = function(topId,index) {
		dataServer.setTopData($scope.reddit.publicTopic[index])
	}
	
	// 移除按钮事件
	$scope.remove = function(index) {
		$scope.reddit.publicTopic.splice(index,1)
		if ($scope.reddit.publicTopic.length < 4){
			$scope.reddit.nextPage()
		}
	}

	// 设置轮播style
	$scope.setStyle = function(type,index) {
		if (type == 1 && index == 0){
			return 'active'
		}
		if (type == 2 && index == 0) {
			return 'active item'
		}
		if (type == 2) {
			return 'item'
		}
		return ''
	}
	
	// 轮播前一页后一页
	$scope.prev = function() {
		$('#AdsCarousel').carousel('prev')
	}
	$scope.next = function() {
		$('#AdsCarousel').carousel('next')
	}

	

	// 导航栏切换表现
	// $rootScope.changeSelect = function () {
		// console.log($('.navbar-left'))
	// }
})

.run(['$rootScope', '$location', '$http', function ($rootScope, $location, $http) {
	// 退出
	$rootScope.loginout = function() {
		$http.get("./user/loginout")
		.then(function success(response){
			window.location = "/PythonExamSystem/index"
		}, function error() {
			console.log('请求失败')
		})
	}

	$rootScope.pwd = {
		oldPwd: "",
		newPwd: "",
		chickPwd: "",
	}
	

	$rootScope.changPWD = function () {
		$rootScope.pwd = {
			oldPwd: "",
			newPwd: "",
			chickPwd: "",
		}
		$("#chgPwdModal").modal("show")
	}

	$rootScope.submitPWD = function(pwd) {
		$("#oldP").popover('hide')
		if (pwd.newPwd === pwd.chickPwd) {
			$("#chickPw").popover('hide')
			// $("#submit").removeAttr("disabled")
		} else {
			$("#chickPw").popover('show')
			// $("#submit").attr("disabled","disabled")
			return
		}
		$http.post("./user/setPWD", pwd)
		.then(function success(response) {
			if (response.data.msg == 0) {
				$("#chgPwdModal").modal("hide")
				window.location = "/PythonExamSystem/index"
				return
			} else if (response.data.msg == 1) {
				$("#oldP").popover('show')
				return
			}
			demo.showNotification('top','right',"修改成功")
			$("#chgPwdModal").modal("hide")
		})
	}
}])

/* 答题相关 */
.controller("answer_public_top", function($scope, $rootScope, $http, $location, dataServer) {
	$scope.topic = dataServer.getTopData()
	$scope.show = false
	$scope.infor = ''

	var editor = ace.edit("editor")
	editor.setTheme("ace/theme/textmate")
	editor.getSession().setMode("ace/mode/python")
	editor.renderer.setScrollMargin(10, 10, 10, 10)
	editor.setOptions({
		autoScrollEditorIntoView: true,
		minLines: 35,
		maxLines: 35
	})



	
	// 没有取到数据，跳转到主页
	if ($scope.topic == null) {
		$location.path("/")
	} else {
		editor.setValue($scope.topic.codeTemp,-1)
	}

	$scope.resetCode = function () {
		editor.setValue($scope.topic.codeTemp,-1)
	}
	
	$scope.sublimeCode = function () {
		var code = editor.getValue()
		var temp = [$scope.topic.topId,code.split('\n').join("~")]
		$http.post("./topic/getCodeResult",temp)
		.then(function success(response) {
			console.log(response.data)
			var msg = response.data.code.split('/')
			console.log(response.data)
			$scope.infor = '<center><h4>得分：'+msg[0] + "/10	</h4></center><br>"
			
			msg[1] = msg[1].substring(0,msg[1].lastIndexOf('-'))
			$scope.infor += ('<center><h5>输入：'+msg[1]+"</h5></center><br>")
			$scope.infor += '	<center><h5>结果：'
			if (msg[2]) {
				if(msg[2] === "运行失败"){
					$scope.infor += "运行失败"
				}else {
					for(var i = 2; i < msg.length; i++) {
						if(i == msg.length -1){
							$scope.infor += (msg[i])
						} else {
							$scope.infor += (msg[i]+ " - ")
						}
						
					}
				}
				
				
			}
			$scope.infor += "</h5></center><br>"
			$scope.show = true
			console.log($scope.show,$scope.infor)
			demo.showNotification('top','right','提交成功，本次提交代码评分为：'+msg[0] + "/10")
		},function error(response) {
			console.log('提交失败')
		})
	}

})

/* 登陆相关 */
.controller("login_form",function($scope, $http, $location) {
	$scope.typeArr = {
			"student" : "学生",
			"teacher" : "教师",
			"admin" : "管理员",
	}
	$scope.user = {
			"type":"student",
			"id":"",
			"pwd":"",
	}
	
	$scope.index = 0;
	$scope.type = ["student","teacher","admin"]
	
	// 设置登陆身份
	$scope.setType = function() {
		console.log("doi")
		if (++$scope.index > 2) {
			$scope.index = 0
		}
		$scope.user.type = $scope.type[$scope.index]
		$scope.checkUser($scope.user.id,$scope.user.type)
	}
	// 取消
	$scope.clear = function() {
		$scope.user = {
				"type":"student",
				"id":"",
				"pwd":"",
		}
	}
	// 失去焦点和获取焦点事件
	$scope.checkUser = function(id,type) {
		$http.get("./user/userIsExist?id=" + id + "&type=" + type)
		.then(function success(response){
			if (response.data.isExist) {
				$('.popover-hide').popover('hide')
				$('#loginBtn').removeAttr("disabled")
			} else {
				$('.popover-hide').popover('show')
				$('#loginBtn').attr("disabled","disabled")
			}
		}, function error() {
			console.log('请求失败')
		})
	}
	$scope.focus = function () {
		$('.popover-hide').popover('hide')
	}

	$scope.focuspwd = function () {
		$('#pwd').popover('hide')
	}

	// 登陆
	$scope.submit = function(user) {
		$http.post("./user/login",user)
		.then(function success(response) {
			if (response.data.login) {
				$('#loginModal').modal('hide')
				window.location = "/PythonExamSystem/index"
			} else {
				$('#pwd').popover('show')
			}
		}, function error(response) {
			console.log('登陆失败')
		})
	}
})

/* 教师管理 */
.controller("teacher_manage",function($scope, $http, $location, TeacherReddit) {
	$scope.teacherReddit = new TeacherReddit()

	$scope.newUser = {
		"type":"teacher",
		"clazz":"",
		"name":"",
	}
	

	$scope.modify = function(id,index,event) {
		var inputTd = $(event.target).parent().parent().siblings("td")
		$(inputTd[1]).children().removeAttr('disabled')
		$(inputTd[2]).children().removeAttr('disabled')

		$(event.target).parent().parent().parent().addClass("warning")
		$scope.teacherReddit.teachetData[index].flag = true

	}

	$scope.save = function(id,index,event){
		var data = $.extend(true, {}, $scope.teacherReddit.teachetData[index])
		delete data.flag
		$http.post("./user/upDateUser",data)
		.then(function success(response) {
			if (!response.data.msg) {
				window.location = "/PythonExamSystem/index"
				return
			}
			var inputTd = $(event.target).parent().parent().siblings("td")
			$(inputTd[1]).children().attr('disabled','disabled')
			$(inputTd[2]).children().attr('disabled','disabled')
			$(event.target).parent().parent().parent().removeClass("warning")
			$scope.teacherReddit.teachetData[index].flag = false
			demo.showNotification('top','right',"保存教师信息成功！")
		},function error(response) {
			console.log("修改信息失败")
		})
		
	}

	$scope.resetPwd = function(id,index,event) {
		$http.get("./user/setUserPwd?id=" + id)
		.then(function success(response) {
			if (!response.data.msg) {
				window.location = "/PythonExamSystem/index"
				return
			}
			$(event.target).popover('show')
			setTimeout(function() {$(event.target).popover('hide')}, 800)
			demo.showNotification('top','right',"密码已重置！")
		},function error(response) {
			console.log("设置密码失败")
		})
		
	}

	$scope.delete = function(id,index,event) {
		$http.get("./user/deleteUser?id=" + id)
		.then(function success(response) {
			if (!response.data.msg) {
				window.location = "/PythonExamSystem/index"
				return
			}
			$scope.teacherReddit.teachetData.splice(index,1)
			demo.showNotification('top','right',"该教师信息已删除！")
		},function error(response) {
			console.log("删除失败")
		})
	}

	$scope.cancer = function (user) {
		$scope.newUser = {
			"type":"teacher",
			"clazz":"",
			"name":"",
		}
	}

	$scope.add = function (user) {
		$http.post("./user/addUser",user)
		.then(function success(response) {
			if (!response.data.msg) {
				window.location = "/PythonExamSystem/index"
				return
			}
			user["id"] = response.data.msg
			$("#addModal").modal('hide')
			$scope.teacherReddit.teachetData.push(user)
			demo.showNotification('top','right',"新教师信息已录入成功！")
		})
	}
})

/* 学生管理 */
.controller("student_manage",function($scope, $http, $location, StudentReddit) {
	$scope.studentReddit = new StudentReddit()
	$scope.newStudentUser = {
		"type":"student",
		"name":"",
	}

	$scope.resetStuPwd = function(id,index,event) {
		$http.get("./user/setStuPwd?id=" + id)
		.then(function success(response) {
			if (!response.data.msg) {
				window.location = "/PythonExamSystem/index"
				return
			}
			$(event.target).popover('show')
			setTimeout(function() {$(event.target).popover('hide')}, 800)
			demo.showNotification('top','right',"重置密码成功")
		},function error(response) {
			console.log("设置密码失败")
		})
	}

	$scope.deleteStu = function(id,index,event) {
		$http.get("./user/deleteStudent?id=" + id)
		.then(function success(response) {
			if (!response.data.msg) {
				window.location = "/PythonExamSystem/index"
				return
			}
			$scope.studentReddit.studentData.splice(index,1)
			demo.showNotification('top','right',"该学生信息已删除")
		},function error(response) {
			console.log("删除失败")
		})
	}

	$scope.cancerStudent = function(studentUser) {
		$scope.newStudentUser = {
			"type":"student",
			"name":"",
		}
	}

	$scope.addStudent = function(newStudentUser){
		if (newStudentUser.name === "") {
			demo.showNotification('top','right',"学生姓名不能为空！")
			return
		}
		$http.post("./user/addStudentUser",newStudentUser)
		.then(function success(response) {
			if (!response.data.msg) {
				window.location = "/PythonExamSystem/index"
				return
			}
			newStudentUser["id"] = response.data.msg
			demo.showNotification('top','right',"新增学生信息成功，新同学学号为：" + response.data.msg)
			$("#addStudentModal").modal('hide')
			$scope.studentReddit.studentData.push(newStudentUser)
			$scope.newStudentUser = {
				"type":"student",
				"name":"",
			}
		})
	}
})

/* 题库管理 */
.controller("topic_manage",function($scope, $http, $location, $route, TopicReddit) {
	$scope.topicReddit = new TopicReddit()
	tinymce.editors = []	// 去掉已经绑定的控件
	tinymce.init({
		mode : "exact",
		elements : "editorInfor",
		// selector:'textarea',
		language:'zh_CN',
		height:'300',
		// inline: true
	})

	$http.get("./user/getUserInfor")
	.then(function success(response) {
		if (response.data.msg == false) {
			window.location = "/PythonExamSystem/index"
			return
		}
		$scope.teacherInfor = response.data
	},function error(response) {
		console.log("请求用户信息失败")
	})


	$scope.topTypeArr = {
		"publicTop" : "公共题型",
		"privateTop" : "考试题型",
	}
	$scope.newTopic = {
		"id":0,
		"topType": "privateTop",
		"title": "",
		"infor": "",
		"codeTemp": "",
		"input": [{input:"",output:""}],
		"keyCode": [{keyCode: "", keyScore: 0}],
	}

	var newEditor = ace.edit("newEditor")
	newEditor.setTheme("ace/theme/textmate")
	newEditor.getSession().setMode("ace/mode/python")
	newEditor.renderer.setScrollMargin(10, 10, 10, 10)
	newEditor.setOptions({
		autoScrollEditorIntoView: true,
		maxLines: 31,
		minLines: 31,
	})

	$scope.showMode = function () {
		$scope.newTopic = {
			"id":0,
			"topType": "privateTop",
			"title": "",
			"infor": "",
			"codeTemp": "",
//			"input": [""],
//			"output": [""],
//			"keyCode":[""],
//			"keyScore":[0],
			"input": [{input:"",output:""}],
			"keyCode": [{keyCode: "", keyScore: 0}],
		}
		$('#addTopModal').modal('show')
	}

	$scope.changeTop = function (top, index) {
		$scope.showMode()
		$scope.newTopic = $.extend(true, {}, top)
		var tempinput = $scope.newTopic.input.split("-")
		var tempoutput = $scope.newTopic.output.split("-")
		$scope.newTopic.input = []
		for(var i=0; i<tempinput.length-1;i++){
			$scope.newTopic.input.push({input: tempinput[i], output: tempoutput[i]})
		}
		
		var tempkeyCode = $scope.newTopic.keyCode.split("-")
		var tempkeyScore = $scope.newTopic.keyScore.split("-")
		$scope.newTopic.keyCode = []
		for(var i=0; i<tempkeyCode.length-1;i++){
			$scope.newTopic.keyCode.push({keyCode: tempkeyCode[i], keyScore: tempkeyScore[i]})
		}
		
		tinymce.activeEditor.setContent(top.infor)

		newEditor.setValue(top.codeTemp,-1)
		
	}
	
	$scope.addInput = function() {
		$scope.newTopic.input.push({input:"",output:""})
	}
	
	$scope.addkeyCode = function() {
		$scope.newTopic.keyCode.push({keyCode: "", keyScore: 0})
	}

	$scope.cancerTopic = function() {
		$scope.newTopic = {
			"id":0,
			"topType": "privateTop",
			"title": "",
			"infor": "",
			"codeTemp": "",
			"input": [{input:"",output:""}],
			"keyCode": [{keyCode: "", keyScore: 0}],
		}

		newEditor.setValue("# 代码模板",-1)
		tinymce.activeEditor.setContent("请在此处键入说明")
	}

	var index = 0
	var type = ['privateTop','publicTop']
	$scope.setTopType = function () {
		if(++index > 1) {
			index = 0
		}
		$scope.newTopic.topType = type[index]
	}

	$scope.formatTime = function(time) {
		var date = new Date(time)
		return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()
	}
	
	$scope.deleteTop = function(item,index){
		console.log(item,index)
		$http.get("./topic/delTop?id="+item.id).then(function success(response) {
			if (!response.data.msg) {
				window.location = "/PythonExamSystem/index"
				return
			}
			$scope.topicReddit.topicData.splice(index,1)
			demo.showNotification('top','right',"该题目已删除")
		},function error(response) {
			console.log("删除失败")
			demo.showNotification('top','right',"删除题目失败，请重试")
		})
	}

	$scope.saveTopic = function (topic,event) {
		console.log(topic)
		$scope.newTopic.infor = tinymce.activeEditor.getContent()
		$scope.newTopic.codeTemp = newEditor.getValue()
		var temp = $scope.newTopic.input
		$scope.newTopic.input = ""
		$scope.newTopic.output = ""
		for(var i = 0; i < temp.length; i++) {
			$scope.newTopic.input += temp[i].input + "-"
			$scope.newTopic.output += temp[i].output + "-"
		}
		
		temp = $scope.newTopic.keyCode
		$scope.newTopic.keyCode = ""
		$scope.newTopic.keyScore = ""
		for(var i = 0; i < temp.length; i++) {
			$scope.newTopic.keyCode += temp[i].keyCode + "-"
			$scope.newTopic.keyScore += temp[i].keyScore + "-"
		}
				
		delete $scope.newTopic.authorName
		console.log(topic)
		demo.showNotification('top','right',"保存题目成功！")
		$http.post("./topic/saveTop",topic)
		.then(function success(response) {
			$(event.target).popover('show')
			setTimeout(function() {
				$(event.target).popover('hide')
				$('#addTopModal').modal('hide')
				$route.reload()
			}, 800)
		},function error(response) {
			console.log("保存题目失败")
			demo.showNotification('top','right',"保存题目失败，请检查数据是否正确！")
		})
	}
})

/* 考试管理 */
.controller("exam_manage",function($scope, $http, $location, $route, ExamReddit) {
	$scope.examReddit = new ExamReddit()
	$scope.examList = []
	$scope.exam = {
		beginTime: "",
		endTime: "",
		timeLong: 0,
		type: 1,
	}
	$scope.type = {
		"0": "激活",
		"1": "暂时作废",
	}

	var nowTemp = new Date()
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0)
	

	$scope.formatTime = function(time) {
		var date = new Date(time)
		return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()
	}

	// 设置激活或者作废
	$scope.setExamType = function(id,index,event){
		var exam = $scope.examReddit.examData[index]
		var type = 0
		if (exam.type == 0) {
			exam.type = 1
			demo.showNotification('top','right',"本场考试已开放！")
		} else {
			exam.type = 0
			demo.showNotification('top','right',"本场考试已关闭！")
		}
		$http.post("./exam/setType",exam)
		.then(function success(response) {
			if (!response.data.msg) {
				if (exam.type == 0) {
					exam.type = 1
					
				} else {
					exam.type = 0
					
				}
			}
		},function error(response) {
			console.log('修改考试信息失败')
		})
	}

	// 删除本场考试
	$scope.deleteExam = function(id,index,event){
		$http.get("./exam/deleteExam?id=" + id)
		.then(function success(response) {
			if (!response.data.msg) {
				// window.location = "/PythonExamSystem/index"
				return
			}
			$scope.examReddit.examData.splice(index,1)
			demo.showNotification('top','right',"本场考试已删除！")
		})
	}

	$scope.addExam = function () {
//		$('#beginTime').datetimepicker({
//			format: 'yyyy-mm-dd ',
//			language: 'zh-CN',
//			minView: 'month',
//			autoclose: true,
//			// todayBtn:  true,
//			startDate: nowTemp,
//		}).on('changeDate', function(ev){
//			$scope.exam.beginTime = ev.date.valueOf()
//			$('#endTime').datetimepicker('setStartDate',ev.date)
//		})
//
//		$('#endTime').datetimepicker({
//			format: 'yyyy-mm-dd ',
//			language: 'zh-CN',
//			minView: 'month',
//			autoclose: true,
//			// todayBtn: true,
//			startDate: nowTemp,
//		}).on('changeDate', function(ev){
//			$scope.exam.endTime = ev.date.valueOf()
//			$('#beginTime').datetimepicker('setEndDate',ev.date)
//		})
		$scope.exam = {
			beginTime: "",
			endTime: "",
			timeLong: 0,
			type: 1,
		}
		$('#addExamModal').modal('show')
	}

	$scope.addExamServer = function(exam) {
		$http.post("./exam/addExam",exam)
		.then(function success(response) {
			if (!response.data.msg) {
				window.location = "/PythonExamSystem/index"
				return
			}
			$('#addExamModal').modal('hide')
			demo.showNotification('top','right',"保存考试信息成功！")
			$route.reload()
		},function error(response) {
			console.log('保存考试失败')
		})
	}

	// 学生考试结果
	$scope.showExamList = function (exam,index) {
		$http.get("./examList/getAllExamList?id=" + exam.id)
		.then(function success(response) {
			if (response.data.length == 1 && response.data[0].msg == false) {
				window.location = "/PythonExamSystem/index"
			}
			console.log(response.data)
			$scope.examList = response.data
			
			$('#examStudentList').modal('show')
		},function error(response) {
			console.log("请求失败")
		})
	}

	// 修改考试内容
	$scope.changExam = function(item,index,event) {
		$scope.exam = $.extend(true, {}, item)
		$scope.exam.beginTime = new Date(item.beginTime).getFullYear() + "-"+(new Date(item.beginTime).getMonth()+1) +"-"+new Date(item.beginTime).getDate()
		$scope.exam.endTime = new Date(item.endTime).getFullYear() + "-"+(new Date(item.endTime).getMonth()+1) +"-"+new Date(item.endTime).getDate()
//		$('#endTime').datetimepicker('setDate',new Date(item.endTime))
//		$('#beginTime').datetimepicker('setDate',new Date(item.beginTime))
		$('#addExamModal').modal('show')
	}
})

/* 学生考试 */
.controller("exam_student_manage",function($scope, $http, $location, $route, dataServer) {
	var nowTemp = new Date().valueOf()

	$scope.examData = []
	$http.get("./exam/getStudentExam")
	.then(function success(response) {
		if (typeof(response.data) != 'object'){
			console.log('获取考试列表失败')
			return
		}
		if (response.data.length == 1 && response.data[0].msg == false) {
			window.location = "/PythonExamSystem/index"
			return
		}
		$scope.examData = response.data
	},function error(response) {
		console.log('获取考试列表失败')
	})

	$scope.formatTime = function(time) {
		var date = new Date(time)
		return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()
	}

	$scope.getType = function (examItem) {
		// 活动时间后
		if (nowTemp > examItem.endTime) {
			$('#' + examItem.id).removeAttr("disableds")
		} else {
			if (!examItem.soce && examItem.soce != 0) {
				$('#' + examItem.id).attr("disabled","disabled")
			} else {
				$('#' + examItem.id).removeAttr("disableds")
			}
		}
		
		if (nowTemp < examItem.beginTime){
			return "未开始"
		} else if (typeof(examItem.soce) == "number") {
			return examItem.soce + "分"
		} else if (nowTemp > examItem.endTime) {
			return "已结束"
		} else if (!examItem.soce) {
			return "未考"
		}
	}

	$scope.getCanExam = function(item) {
		if (nowTemp > item.endTime) {
			return false
		}
		if (nowTemp < item.beginTime) {
			return false
		}
		if (!item.soce && item.soce != 0) {
			return true
		}
		if (item.soce != -1) {
			return false
		}
		return true
	}


	$scope.showExam = function(item,index,event) {
		if (typeof(item.soce) != "number") {
			demo.showNotification('top','right','未参加本场考试，无法查看试卷')
			return 
		}
		var id = item.id
		$http.get("./papers/getPapersByExamId?examId=" + id)
		.then(function success(response) {
			if (typeof(response.data) != 'object'){
				return
			}
			if (response.data.length == 1 && response.data[0].msg == false) {
				window.location = "/PythonExamSystem/index"
				return
			}
			$scope.oneExamData = response.data
			$scope.oneExamInfor = []
			$scope.oneExamInfor.examTime = $scope.examData[index].examTime
			$scope.oneExamInfor.spenTime = $scope.examData[index].spenTime
			$scope.oneExamInfor.soce = $scope.examData[index].soce
			$scope.oneExamDataItem = $scope.oneExamData[0]
			$scope.oneExamInfor.index = 0
			$scope.oneExamDataItem.input = $scope.oneExamDataItem.input.substring(0,$scope.oneExamDataItem.input.lastIndexOf('-'))
			if($scope.oneExamDataItem.output.split("/")[1] === "运行失败"){
				$scope.oneExamDataItem.output = "运行失败"
			}
			$("#pre").attr("disabled","disabled")
			$("#next").removeAttr("disabled")
			$("#examModal").modal("show")
			console.log($scope.oneExamDataItem)
			// $(".code").setValue($scope.oneExamDataItem.code,-1)
			if ($scope.oneExamDataItem.input) {
			$scope.inputList = $scope.oneExamDataItem.input.split("/")
			} else {
				$scope.inputList = []
			}
			if ($scope.oneExamDataItem.output) {
				$scope.outputList = $scope.oneExamDataItem.output.split("/")
			} else {
				$scope.outputList = ['','','']
			}
			
			$scope.inputString = $scope.inputList.join(" | ")
			$scope.outputString = $scope.outputList.join(" | ")
			// 设置语法高亮
			// var highlight = ace.require("ace/ext/static_highlight")
			// // var dom = ace.require("ace/lib/dom")
			
			// var dom = $('#code')[0]
			// highlight(dom, {
			// 	mode: dom.getAttribute("ace-mode"),
			// 	theme: dom.getAttribute("ace-theme"),
			// 	startLineNumber: 1,
			// 	showGutter: dom.getAttribute("ace-gutter"),
			// 	trim: true,
			// }, function (highlighted) {
			// 	console.log(highlighted)
			// })

		
		},function error(response) {
			console.log("请求失败")
		})
	}

	$scope.preItem = function () {
		$("#next").removeAttr("disabled")
		$scope.oneExamDataItem = $scope.oneExamData[$scope.oneExamInfor.index - 1]
		$scope.oneExamInfor.index = $scope.oneExamInfor.index - 1
		$scope.oneExamDataItem.input = $scope.oneExamDataItem.input.substring(0,$scope.oneExamDataItem.input.lastIndexOf('-'))
		if($scope.oneExamDataItem.output.split("/")[1] === "运行失败"){
			$scope.oneExamDataItem.output = "运行失败"
		}
		if ($scope.oneExamDataItem.input) {
			$scope.inputList = $scope.oneExamDataItem.input.split("/")
		} else {
			$scope.inputList = []
		}
		if ($scope.oneExamDataItem.output) {
			$scope.outputList = $scope.oneExamDataItem.output.split("/")
		} else {
			$scope.outputList = ['','','']
		}
		
		$scope.inputString = $scope.inputList.join(" | ")
		$scope.outputString = $scope.outputList.join(" | ")
		if ($scope.oneExamInfor.index <= 0) {
			$("#pre").attr("disabled","disabled")
		}
	}
	$scope.nextItem = function () {
		$("#pre").removeAttr("disabled")
		$scope.oneExamDataItem = $scope.oneExamData[$scope.oneExamInfor.index + 1]
		$scope.oneExamInfor.index = $scope.oneExamInfor.index + 1
		$scope.oneExamDataItem.input = $scope.oneExamDataItem.input.substring(0,$scope.oneExamDataItem.input.lastIndexOf('-'))
		if($scope.oneExamDataItem.output.split("/")[1] === "运行失败"){
			$scope.oneExamDataItem.output = "运行失败"
		}
		if ($scope.oneExamDataItem.input) {
			$scope.inputList = $scope.oneExamDataItem.input.split("/")
		} else {
			$scope.inputList = []
		}
		if ($scope.oneExamDataItem.output) {
			$scope.outputList = $scope.oneExamDataItem.output.split("/")
		} else {
			$scope.outputList = ['','','']
		}
		
		$scope.inputString = $scope.inputList.join(" | ")
		$scope.outputString = $scope.outputList.join(" | ")
		
		if ($scope.oneExamInfor.index >= ($scope.oneExamData.length - 1)) {
			$("#next").attr("disabled","disabled")
		}
	}

	$scope.beginExam = function(item,$index,$event) {
		dataServer.setExamId(item.id)
		dataServer.setExamTime(item.timeLong)
		$location.path("/beginExamPag")
	}
	
})

/* 考试页面 */
.controller("begin_exam_psge",function($scope, $http, $location, $route, dataServer) {
	$scope.examtime = dataServer.getExamTime()
	// $scope.nowTime = dataServer.getExamTime()
	$scope.nowTime = 40
	var examEditor = ace.edit("examEditor")
	examEditor.setTheme("ace/theme/textmate")
	examEditor.getSession().setMode("ace/mode/python")
	examEditor.renderer.setScrollMargin(10, 10, 10, 10)
	examEditor.setOptions({
		autoScrollEditorIntoView: true,
		maxLines: 35,
		minLines: 35,
	})
	$http.get("./papers/initExam?examId=" + dataServer.getExamId())
	.then(function success(response) {
		if (typeof(response.data) != 'object'){
			console.log('获取考试列表失败')
			return
		}
		if (response.data.length == 1 && response.data[0].msg == false) {
			window.location = "/PythonExamSystem/index"
			return
		}

		$scope.examList = response.data
		$scope.examListItem = $scope.examList[0]
		$scope.index = 0
		examEditor.setValue($scope.examListItem.code,-1)
		$("#pre").attr("disabled","disabled")
		$("#next").removeAttr("disabled")
	},function error(response) {
		console.log('获取失败')
	})

	$scope.preItem = function () {
		$("#next").removeAttr("disabled")
		$scope.examListItem.code = examEditor.getValue()
		$scope.index = $scope.index - 1
		$scope.examListItem = $scope.examList[$scope.index]
		examEditor.setValue($scope.examListItem.code,-1)
		if ($scope.index == 0) {
			$("#pre").attr("disabled","disabled")
		}
	}

	$scope.nextItem = function () {
		$("#pre").removeAttr("disabled")
		$scope.examListItem.code = examEditor.getValue()
		$scope.index = $scope.index + 1
		$scope.examListItem = $scope.examList[$scope.index]
		examEditor.setValue($scope.examListItem.code,-1)
		if ($scope.index >= $scope.examList.length - 1) {
			$("#next").attr("disabled","disabled")
		}
	}

	setTimeout("digitalTime()",60000)
	// 倒计时
	digitalTime = function () {
		$scope.nowTime  = $scope.nowTime - 1
		
		if ($scope.nowTime <= 0) {
			if ($scope.nowTime <= -1) return
			$scope.$digest()
			$scope.examListItem.code = examEditor.getValue()
			for (var i = 0; i < $scope.examList.length; i++) {
			$scope.examList[i].code = $scope.examList[i].code.split('\n').join("~")
		}
			$http.post("./papers/submitExam", $scope.examList)
			.then(function success(response) {
				demo.showNotification('top','right',"你已完成本场考试，得分："+response.data.msg+"分")
				$location.path("/exam")
			},function error(response) {
				console.log('提交失败')
			})
		} else {
			setTimeout("digitalTime()",60000)
		}
	}

	$scope.sublimeExam = function() {
		$scope.nowTime = 0
		$scope.examListItem.code = examEditor.getValue()
		
		for (var i = 0; i < $scope.examList.length; i++) {
			$scope.examList[i].code = $scope.examList[i].code.split('\n').join("~")
		}
		console.log($scope.examList)
		$http.post("./papers/submitExam", $scope.examList)
		.then(function success(response) {
			demo.showNotification('top','right',"你已完成本场考试，得分："+response.data.msg+"分")
			$location.path("/exam")
		},function error(response) {
			console.log('提交失败')
		})
	}
})

/* 路由设置 */
.config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/', {
				templateUrl : './views/public/index.jsp',
				controller:'public_index'
			}).when('/answerPublicTop', {
				templateUrl : './views/public/answer.jsp',
				controller:'answer_public_top'
			}).when('/teacherManage', {
				templateUrl : './views/admin/teacherManage.jsp',
				controller:'teacher_manage'
			}).when('/studentManage', {
				templateUrl : './views/teacher/studentManage.jsp',
				controller:'student_manage'
			}).when('/topicManage', {
				templateUrl : './views/teacher/topicManage.jsp',
				controller:'topic_manage'
			}).when('/examManage', {
				templateUrl : './views/teacher/examManage.jsp',
				controller:'exam_manage'
			}).when('/exam', {
				templateUrl : './views/student/exam.jsp',
				controller:'exam_student_manage'
			}).when('/beginExamPag', {
				templateUrl : './views/student/beginExamPag.jsp',
				controller:'begin_exam_psge'
			}).otherwise({
				redirectTo : '/'
			})
		}]
)

// 加载不同路由的script代码
.directive('script', function() {
	return {
		restrict: 'E',
		scope: false,
		link: function(scope, elem, attr) {
			if (attr.type === 'text/javascript-lazy') {
				var code = elem.text()
				var f = new Function(code)
				f()
			}
		}
	}
})

// 共享数据
.service('dataServer', function() {
	this.topData = null
	this.examId = 0
	this.examTime = 0
	this.getTopData = function() {
		return this.topData
	}
	this.setTopData = function( top ) {
		this.topData = top
	}

	this.getExamId = function() {
		return this.examId
	}
	this.setExamId = function( examId ) {
		this.examId = examId
	}

	this.getExamTime = function () {
		return this.examTime
	}
	this.setExamTime = function (examTime) {
		this.examTime = examTime
	}
})

// 题目翻页相关
.factory('Reddit', function($http) {
	var Reddit = function() {
		this.pagNum = 12
		this.publicTopic = []
		this.busy = false
		this.page = 0
		this.isOtherData = true
	}
	Reddit.prototype.nextPage = function() {
		if (this.busy) return
		if (!this.isOtherData) return
		this.busy = true
		var that = this
		$http.get("./public/getTopicByType?type=publicTop&begin=" + this.page * this.pagNum + "&length=" + this.pagNum)
		.then(function success(response) {
			for (var i = 0; i < response.data.length; i++) {
				that.publicTopic.push(response.data[i])
			}
			that.page += 1
			that.busy = false
			if (response.data.length == 0){
				that.isOtherData = false
			}
		},function error(response) {
			console.log("请求失败")
		})
	}
	return Reddit
})

// 教师翻页相关
// 翻页相关
.factory('TeacherReddit', function($http) {
	var TeacherReddit = function() {
		this.pagNum = 24
		this.teachetData = []
		this.busy = false
		this.page = 0
		this.isOtherData = true
		this.falseNum = 0
	}
	TeacherReddit.prototype.nextPage = function() {
		if (this.busy) return
		if (!this.isOtherData) return
		this.busy = true
		var that = this
		$http.get("./user/getAllTeacherData?begin=" + this.page * this.pagNum + "&length=" + this.pagNum)
		.then(function success(response) {
			if (typeof(response.data) != 'object'){
				that.falseNum += 1
				if (that.falseNum >= 10) that.isOtherData = false
				return
			}
			that.falseNum = 0
			if (response.data.length == 1 && response.data[0].msg == false) {
				window.location = "/PythonExamSystem/index"
				that.isOtherData = false
				return
			}
			for (var i = 0; i < response.data.length; i++) {
				response.data[i].flag = false
				that.teachetData.push(response.data[i])
			}
			that.isOtherData = false
			that.page += 1
			that.busy = false
			if (response.data.length == 0){
				that.isOtherData = false
			}
		},function error(response) {
			console.log("请求失败")
			that.falseNum += 1
			if (that.falseNum >= 10) that.isOtherData = false
		})
	}
	return TeacherReddit
})

// 学生翻页相关
.factory('StudentReddit', function($http) {
	var StudentReddit = function() {
		this.pagNum = 24
		this.studentData = []
		this.busy = false
		this.page = 0
		this.isOtherData = true
		this.falseNum = 0
	}
	StudentReddit.prototype.nextPage = function() {
		if (this.busy) return
		if (!this.isOtherData) return
		this.busy = true
		var that = this
		$http.get("./user/getAllStudentData?begin=" + this.page * this.pagNum + "&length=" + this.pagNum)
		.then(function success(response) {
			if (typeof(response.data) != 'object'){
				that.falseNum += 1
				if (that.falseNum >= 10) that.isOtherData = false
				return
			}
			that.falseNum = 0
			if (response.data.length == 1 && response.data[0].msg == false) {
				window.location = "/PythonExamSystem/index"
				that.isOtherData = false
				return
			}
			for (var i = 0; i < response.data.length; i++) {
				response.data[i].flag = false
				that.studentData.push(response.data[i])
			}
			that.isOtherData = false
			that.page += 1
			that.busy = false
			if (response.data.length == 0){
				that.isOtherData = false
			}
		},function error(response) {
			console.log("请求失败")
			that.falseNum += 1
			if (that.falseNum >= 10) that.isOtherData = false
		})
	}
	return StudentReddit
})

// 题目翻页相关
.factory('TopicReddit', function($http) {
	var TopicReddit = function() {
		this.pagNum = 24
		this.topicData = []
		this.busy = false
		this.page = 0
		this.isOtherData = true
	}
	TopicReddit.prototype.nextPage = function() {
		if (this.busy) return
		if (!this.isOtherData) return
		this.busy = true
		var that = this
		$http.get("./topic/getAllTopic?begin=" + this.page * this.pagNum + "&length=" + this.pagNum)
		.then(function success(response) {
			console.log(response)
			if (typeof(response.data) != 'object'){
				that.falseNum += 1
				if (that.falseNum >= 10) that.isOtherData = false
				return
			}
			that.falseNum = 0
			if (response.data.length == 1 && response.data[0].msg == false) {
				window.location = "/PythonExamSystem/index"
				that.isOtherData = false
				return
			}
			for (var i = 0; i < response.data.length; i++) {
				that.topicData.push(response.data[i])
			}
			that.page += 1
			that.busy = false
			if (response.data.length == 0){
				that.isOtherData = false
			}
		},function error(response) {
			console.log("请求失败")
		})
	}
	return TopicReddit
})

// 考试翻页相关
.factory('ExamReddit', function($http) {
	var ExamReddit = function() {
		this.pagNum = 24
		this.examData = []
		this.busy = false
		this.page = 0
		this.isOtherData = true
	}
	ExamReddit.prototype.nextPage = function() {
		if (this.busy) return
		if (!this.isOtherData) return
		this.busy = true
		var that = this
		$http.get("./exam/getAllExam?begin=" + this.page * this.pagNum + "&length=" + this.pagNum)
		.then(function success(response) {
			if (typeof(response.data) != 'object'){
				that.falseNum += 1
				if (that.falseNum >= 10) that.isOtherData = false
				return
			}
			that.falseNum = 0
			if (response.data.length == 1 && response.data[0].msg == false) {
				window.location = "/PythonExamSystem/index"
				that.isOtherData = false
				return
			}
			for (var i = 0; i < response.data.length; i++) {
				that.examData.push(response.data[i])
			}
			that.page += 1
			that.busy = false
			if (response.data.length == 0){
				that.isOtherData = false
			}
		},function error(response) {
			console.log("请求失败")
		})
	}
	return ExamReddit
})


// jquery 部分
$(function () {
	$(".navbar-left li").click(function () {
		$(this).addClass("active").siblings().removeClass("active")
	})
})