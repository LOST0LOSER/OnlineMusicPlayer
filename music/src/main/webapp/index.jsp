<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">

  	<script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
  	<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<title>首页</title>
</head>
<body>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	
	<script type="text/javascript" src="js/header.js"></script>
    <link rel="stylesheet" href="./css/background.css" type="text/css" />
    <link rel="stylesheet" href="./css/index.css" type="text/css" />

<!-- header-->
<%@include file="header.jsp" %>
<div class="container" style="margin-bottom:100px;">

    <div id="musicCarousel" class="carousel slide col-xs-12" data-ride="carousel">
        <!-- 轮播（Carousel）指标 -->
        <ol class="carousel-indicators">
            <li data-target="#musicCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#musicCarousel" data-slide-to="1"></li>
            <li data-target="#musicCarousel" data-slide-to="2"></li>
        </ol>
        <!-- 轮播（Carousel）项目 -->
        <div class="carousel-inner">
            <div class="item active">
                <a href="javascript:playById(1)"><img id="music_1" value='{ "name":"爸爸妈妈", "artist":"李荣浩", "url":"musicFile/爸爸妈妈.mp3" }'
                    src="images/李荣浩.jpg" alt="First slide" ></a>
            </div>
            <div class="item">
                <a href="javascript:playById(2)"><img id="music_2" value='{ "name":"喜欢你", "artist":"Beyond", "url":"musicFile/Beyond - 喜欢你.flac" }'
                                    src="images/beyond.jpg" alt="Second slide" ></a>
            </div>
            <div class="item">
                <a href="javascript:playById(3)"><img id="music_3" value='{ "name":"bad guy", "artist":"Billie", "url":"musicFile/bad guy.mp3" }'
                                                    src="images/billie.jpg" alt="Third slide" ></a>
            </div>

        </div>
        <!-- 轮播（Carousel）导航 -->
        <a class="carousel-control left" href="#musicCarousel" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left"></span></a>
        <a class="carousel-control right" href="#musicCarousel" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right"></span></a>
    </div>

</div>
<%@include file="footer.jsp" %>
</body>
</html>