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
	<title>登陆页面</title>
	
</head>

<body>
<div class="container">
	<a  class="col-xs-2 col-xs-offset-5" style="margin-top:150px;" href="index.jsp"><h1>HiMusic</h1></a>
	<div class="col-xs-8 col-xs-offset-2 " id="form">
		<form action="login.do" role="form" method="post">
            <div class="col-xs-12 row">
                <label for="username">用户名<span class="text-danger">  *</span></label>
        		<input type="text" class="form-control" name="username" placeholder="请输入用户名" required>
            </div>
            <div class="col-xs-12 row">
                <label for="password">密码<span class="text-danger">  *</span></label>
        		<input type="password" class="form-control" name="password" placeholder="请输入密码" required>
            </div>
            <div class="row">
	            <div class="col-xs-8">
	                <label for="validatecode">验证码<span class="text-danger">  *</span></label>
	                <input type="text" class="col-xs-6 form-control" name="validatecode" placeholder="请输入验证码" required>
	        	</div>
	        	<div class="col-xs-4" style="margin-top:20px;">
	        		<img src="code" onclick="this.src='code?'+Math.random();" class="c_code" title="点击更换">
        			<button type="submit" class="btn btn-lg btn-primary" style="margin-left:20px;" name="submit" >登陆</button>
        		</div>
        	</div>
        	<% 
        		String loginMessage = (String)request.getAttribute("login");
        		if(loginMessage!=null){
        	%>
        		<script type="text/javascript">
                	alert("${login }");
            	</script>
            <% 
        		}
            %>
            <a href="register.jsp" style="color:white;"><p>如果您还没有账号请点击这里</p></a>
        </form>
    </div>

</div>
<div class="clearfix"></div>
</body>
</html>