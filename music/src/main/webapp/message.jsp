<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*,entity.User"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	
	<link href="css/message.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/header.js"></script>
    <link rel="stylesheet" href="./css/background.css" type="text/css" />
    
  	<script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
  	<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<title>留言栏</title>
</head>
<body>
	<%@include file="header.jsp"%>

	<div class="container">
		<form action="message.do" method="post" >
			<div>
				<textarea name="comment" id="message_ly" class="form-control" rows="8" style="background-color:rgba(255,255,255,0.8);" required></textarea>
				<br /> 昵称： <br />
				<%if(user!=null){ %>
				<input type="text" name="username" value="<%=user.getUsername() %>" />
				<%}else{ %>
				<input type="text" name="username" value="游客" />
				<%} %>
				<input type="submit" name="button" id="message_ly_sub" value="留言" />
			</div>
		</form>

		<div>
			<h1 style="color:white;">最近留言</h1>
		</div>
		<div>
			<%
			List<String> list = (List<String>)session.getAttribute("messages");
			if(list !=null){
				for(int i=list.size()-1;i>=0;i--){
					String str = list.get(i);
					%>
			<p style="color:white;"><%=str%></p>
			<% 					
				}
			}
			%>
		</div>
	</div>
</body>
</html>