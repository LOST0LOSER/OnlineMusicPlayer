<%@page import="entity.User"%>
<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<nav class="navbar navbar-inverse" role="navigation">
<div class="container-fluid">
	<div class="navbar-header">
		<a class="navbar-brand" href="index.jsp">HiMusic</a>
	</div>
	<div>
		<ul class="nav navbar-nav navbar-left">
			<li><a href="findMusic.do">歌单</a></li>
			<li><a href="message.jsp">留言栏</a></li>
    	</ul>
    	<ul class="nav navbar-nav navbar-right">
    		<li>
			<a id="time"></a>
			</li>
			<%
				User user = (User)session.getAttribute("user");
				if(user==null){
					%>
					<li><a class="glyphicon glyphicon-user" href="login.jsp"> 登陆</a></li>
					<li><a class="glyphicon glyphicon-hand-up" href="register.jsp"> 注册</a></li>
					<%
				}else{
					%>
					<li><a class="glyphicon glyphicon-heart" href="myFavorites.do">收藏夹</a></li>
					<li><a class="glyphicon glyphicon-user" href="cancel.do" onclick="return confirm('是否确定注销登陆?');" 
					style="text-decoration:underline; color:grey;"><%=user.getUsername()%></a></li>
					<%
				}
	
			%>
		</ul>
	</div>
</div>
</nav>
<div class="col-xs-offset-5 col-xs-2">
		<h1 style="font-size: 45px;font-weight:bold;">HiMusic</h1>
</div>
<div class="col-xs-offset-2 col-xs-8" style="margin-bottom:30px;">
	<form action="findMusic.do" class="form">
		<div class="col-xs-11">
    		<input type="text" class="form-control" placeholder="请输入您要搜索的歌曲" name="str">
    	</div>
    	<div class="col-xs-1">
            <button type="submit" class="btn btn-md glyphicon glyphicon-search" 
            	style="color:black;"	name="submit" >搜索</button>
            
		</div>
    </form>
</div>