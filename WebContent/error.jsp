<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String code = request.getParameter("code");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出错啦</title>
</head>
<body>
	<%
		out.println("程序发生错误，错误代码：" +code+ "，错误信息:访问" + request.getRequestURI() + "发生错误!");
	%>
</body>
</html>




