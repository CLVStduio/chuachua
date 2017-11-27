var origin_x = 0;
var origin_y = 0;
function game(ws,airId){
	var lookWidth = $(window).width();
	var lookHeight = $(window).height();
	var sizeArr = init(lookWidth,lookHeight);
	
	var doc = document;
	doc.addEventListener("touchstart",  startTouchScroll, false);
	doc.addEventListener("touchmove", moveTouchScroll, false);
	doc.addEventListener("touchend",  endTouchScroll, false);
	var startY, endY, startX, endX;
	function startTouchScroll(event){
	        var touch = event.touches[0];
	        startX = touch.pageX;
	        startY = touch.pageY;
	}
	function moveTouchScroll(event){
	        var touch = event.touches[0];
	        endX = touch.pageX;
	        var x = parseInt($(".myBan").css("left").replace(/px/g,""));
	        x += endX - startX;
	        x = x < origin_x ? origin_x: x+sizeArr[0]/4 > origin_x+sizeArr[0]?origin_x+sizeArr[0]*3/4:x;
	        startX = endX;
        	$(".airBan").css("left");
        	$(".myBan").css("left",x);
        	x = (x-origin_x)/sizeArr[0];
        	ws.send('{t: "c", c: "'+x+'"}');
	}
	function endTouchScroll(event){
        //计算滑动距离
       
	}
	return sizeArr[0];
}
function realTime(x){
	var realX = x*10000*width/10000+origin_x;
	$(".airBan").css("left",realX);
}
function init(lookWidth,lookHeight){
	var sizeArr = interfaceInit(lookWidth,lookHeight);
	var width = sizeArr[0];
	var height = sizeArr[1];
	initBang(width,height);
	initBricks(width,height);
	return sizeArr;
}
//砖块初始化
function initBricks(width,height){
	var bricksWidth = width/9;
	var bricksHeight = bricksWidth*0.6;
	var html = "";
	for(var i=0,k=0,j=0;j<4;i++,k++){
		var num = k+36;
		var y = origin_y + j*bricksHeight;
		var yy = height - (j+1)*bricksHeight;
			var x = origin_x + i*bricksWidth;
			html += '<div id="'+ k +'" class="bricks" style="top:'+y+'px;left:'+ x
						+'px;width:'+bricksWidth+'px;height:'+bricksHeight+'px"> </div>';
			html += '<div id="'+ num +'" class="bricks" style="top:'+yy+'px;left:'+ x
						+'px;width:'+bricksWidth+'px;height:'+bricksHeight+'px"> </div>';
		if(i >= 8){
			i=-1;
			j++;
		}
	}
	$("#Main").append(html);
}
//击打板初始化
function initBang(width,height){
	var bangWidth = width/4;
	var bangHeight = height*0.03;
	$(".myBan").css({"width":bangWidth,"height":bangHeight,"top":origin_y+height*0.7,"left":origin_x+width*3/8});
	$(".airBan").css({"width":bangWidth,"height":bangHeight,"top":origin_y+height*0.3,"left":origin_x+width*3/8});
}

//游戏界面初始化
function interfaceInit(lookWidth,lookHeight){
	var width = lookHeight/16*9;
	var height =lookHeight;
	if(width<lookWidth){
		var difWidth = (lookWidth-width)/2;
		origin_x = difWidth;
		$("#Main").css({"height":lookHeight,"width":width,"margin-left":difWidth});
	}else {
		height = lookWidth/9*16;
		if(height<lookHeight){
			width = lookWidth;
			var difHeight = (lookHeight-height)/2;
			origin_y = difHeight;
			$("#Main").css({"height":height,"width":lookWidth,"margin-top":difHeight});
		}else{
			var ratio = 0.95;
			do{
				height *= ratio;
				width = height/16*9;
				ratio -= 0.05;
			}while(width>lookWidth && height>lookHeight);
			var difWidth = (lookWidth-width)/2;
			var difHeight = (lookHeight-height)/2;
			origin_x = difWidth;
			origin_y = difHeight;
			$("#Main").css({"height":lookHeight,"width":width,"margin-left":difWidth,"margin-top":difHeight});
		}
	}
	//vs图片摆放
	var vsWidth = height/20;
	$(".vs").css({"width":vsWidth,"top":origin_y+height/2-vsWidth/1.5,"left":origin_x+width-vsWidth-vsWidth*0.4});
	//玩家名字
	$("#myName").css({"top":origin_y+height/2+vsWidth*1.5,"left":origin_x+width-vsWidth-vsWidth*0.4});
	$("#airName").css({"top":origin_y+height/2-vsWidth*1.5-$("#airName").height(),"left":origin_x+width-vsWidth-vsWidth*0.4});
	return [width,height];
}
