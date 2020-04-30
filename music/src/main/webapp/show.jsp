<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*,entity.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	
	<script type="text/javascript" src="js/header.js"></script>
	<script type="text/javascript" src="js/show.js"></script>
    <link rel="stylesheet" href="./css/background.css" type="text/css" />
    <link rel="stylesheet" href="./css/show.css" type="text/css" />
    
  	<script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
  	<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  	
  	
	<link type="text/css" rel="stylesheet" href="fileinput/css/fileinput.css" />
	<script type="text/javascript" src="fileinput/js/fileinput.js"></script>
	<script type="text/javascript" src="fileinput/js/locales/zh.js"></script>
  	
	<title>歌单</title>
</head>
<body>
<!-- header-->
<%@include file="header.jsp" %>
<div class="container" style="margin-bottom:100px;">
	<p id="whereami"></p>
	<table class="table" style='background-color:rgba(255,255,255,0.8)'>
		<thead>
			<tr>
			<th>歌名</th>
			<th>歌手</th>
			<th>&nbsp;&nbsp;&nbsp;播放</th>
			<% if(user!=null){%><th>&nbsp;&nbsp;&nbsp;收藏</th><%}%>
			</tr>
		</thead>
		<tbody>
		<%
		List<Music> musics = (List<Music>)request.getSession().getAttribute("musics");
			//request.setAttribute("singer",)
		if(musics!=null){
		    for(int i=0;i<musics.size();i++){
			    Music m = musics.get(i);
		%>
		<tr>
			<td><%=m.getTitle() %></td>
			<td><a href="findMusic.do?str=<%=m.getSinger()%>"><%=m.getSinger() %></a></td>
			<!--<td><a href="<%=m.getSinger()%>.getSinger"><%=m.getSinger() %></a></td>-->
			<!--<td><a href="#"><audio src="musicFile/<%=m.getUrl()%>" controls="controls" controlsList="nodownload" preload="meta" loop="loop"></audio></a></td>-->
			<td><button type="button" id="music_<%=i%>" value='<%=m.toJSON()%>'
				onclick="playById(<%=i%>)" class="btn btn-lg glyphicon glyphicon-play" ></button></td>
		<% if(user!=null){
				String c;
				if(m.getFavTag()) c = "favorite"; else c = "noFavorite";%>
		    <td id="love_<%=m.getId()%>" class="<%=c%>">
		    	<button type="button" onclick="toggleFavorite(<%=m.getId()%>,<%=user.getId()%>)" class="btn btn-lg glyphicon glyphicon-heart"></button></td>
	        <%}%>		
		</tr>
		<%
			}
		}
		%>
	</table>
	<form class="form" action="${pageContext.request.contextPath}/UploadMusic.do" enctype="multipart/form-data" method="post">
		<input id="uploadMusic" type="file" name="file" class="file" data-allowed-file-extensions='["mp3","flac"]' 
		 	data-language="zh" data-show-preview=false data-drop-zone-enabled=false accept="audio/*" />
	</form>
       <%
           String message = (String)request.getAttribute("message");
           if(message!=null){
           %>

           <script type="text/javascript">
                   alert("<%=message%>");
           </script>

           <%
           }
       %>
</div>

<%@include file="footer.jsp" %>	
</body>
</html>
