<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	
    <link rel="stylesheet" href="./css/background.css" type="text/css" />
    
  <script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<title>注册页面</title>
	
</head>

<body>
<div class="container">
	<a  class="col-xs-2 col-xs-offset-5" style="margin-top:150px;" href="index.jsp"><h1>HiMusic</h1></a>
	<div class="col-xs-8 col-xs-offset-2 "  id="form">
		<form action="register.do" role="form" method="post">
            <div class="col-xs-12 row">
                <label for="username">用户名<span class="text-danger">  *</span></label>
        		<input type="text" class="form-control" name="username" placeholder="请输入用户名" required>
            </div>
            <div class="col-xs-12 row">
                <label for="password">密码<span class="text-danger">  *</span></label>
        		<input type="password" class="form-control" name="password" placeholder="请输入密码" required>
			</div>
			<div class="col-xs-12 row">
                <label for="OKpassword">确认密码<span class="text-danger">  *</span></label>
        		<input type="password" class="form-control" name="OKpassword" placeholder="请再次输入密码" required>
			</div>
			<div class="col-xs-12 row">
                <label for="email">邮箱<span class="text-danger">  *</span></label>
        		<input type="email" class="form-control" name="email" placeholder="请输入邮箱" required>
			</div>
			<div class="col-xs-12 row">
				<div class="col-xs-6">	
					<label for="age">年龄<span class="text-danger">  *</span></label>
					<select class="form-control" name="age" required>
						<option>1</option>
							<%
							for(int i=2;i<=100;i++){
								%>
								<option><%=i%></option>
								<%
							}
							%>
					</select>
				</div>
				<div class="col-xs-2">
					<label for="gender">性别<span class="text-danger">  *</span></label>
					<div class="radio">
						<label>
							<input type="radio" name="gender" id="auto1" value="boy" checked>男
						</label>
					
						<label>
							<input type="radio" name="gender" id="auto2" value="girl">女
						</label>
					</div>
				</div>
				<button type="submit" class="btn btn-lg btn-primary" style="margin-top:20px;" name="submit" >注册</button>
				<a class="btn btn-lg btn-default" style="margin-top:20px; margin-left:20px;" href="login.jsp" role="button">返回</a>
			</div>
			<% 
        		String registerMessage = (String)request.getAttribute("error");
        		if(registerMessage!=null){
        	%>
        		<script type="text/javascript">
                	alert("${error}");
            	</script>
            <% 
        		}
            %>
        </form>
    </div>

</div>
<div class="clearfix"></div>
</body>
</html>