function toggleFavorite(musicID,userID){
	var str = "toggleFavorite.do?musicId="+musicID+"&userId="+userID;
	var xmlhttp;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
	    	var obj = $("#love_"+musicID);
			var objClass = obj.attr("class");
			if(objClass=="noFavorite"){
				obj.attr("class","favorite");
	    	}else if(objClass=="favorite"){
	    		obj.attr("class","noFavorite");
	    	}
	    }
	}
	xmlhttp.open("GET",str,true);
	xmlhttp.send();
}