var origin_x = 0;
var origin_y = 0;
var width ;
var height ;
var flag ;
var ball_diameter;
function game(ws,msg){
	var lookWidth = $(window).width();
	var lookHeight = $(window).height();
	init(lookWidth,lookHeight);
	flag = msg;
	console.log("flag : "+flag);
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
	        x = x < origin_x ? origin_x: x+width/4 > origin_x+width?origin_x+width*3/4:x;
	        startX = endX;
        	$(".airBan").css("left");
        	$(".myBan").css("left",x);
        	x = (x-origin_x)/width;
        	ws.send('{t: "c", c: "'+x+'"}');
	}
	function endTouchScroll(event){
        //计算滑动距离
       
	}
	return width;
}
function realBan(x){
	var realX = x*10000*width/10000+origin_x;
	$(".airBan").css("left",realX);
}
function realBrick(obj,clearflag){
	if(flag == clearflag){
		obj.cb.forEach(function(id){
			id+=36;
			$("#"+id+"").remove();
		});
	}else {
		obj.cb.forEach(function(id){
			$("#"+id+"").remove();
		});
	}

	if(flag == Constants.PlayerStatus.PLAYER_FLAG_A){
		$(document).attr("title","比分 "+obj.sa+" ： "+obj.sb);
	}else{
		$(document).attr("title","比分 "+obj.sb+" ： "+obj.sa);
	}
}
function realGameOfTime(obj){		
	$("#time").text(obj.time);
	if(flag == Constants.PlayerStatus.PLAYER_FLAG_A){
		$(".ball").css({"top":origin_y+obj.ballY*height-ball_diameter/2,"left":origin_x+obj.ballX*width-ball_diameter/2});
	}else {
		obj.ballY = 1-obj.ballY;
		$(".ball").css({"top":origin_y+obj.ballY*height-ball_diameter/2,"left":origin_x+obj.ballX*width-ball_diameter/2});
	}
}
function init(lookWidth,lookHeight){
	interfaceInit(lookWidth,lookHeight);;
	initBang(width,height);
	initBricks(width,height);
	initBall(width,height);
	$(document).attr("title","比分 0 ： 0");
}
//球初始化
function initBall(){
	ball_diameter = height*32/1000;
	$(".ball").css({"width":height*32/1000,"top":origin_y+height*468/1000,"left":origin_x});
	$(".ball").css("display","block");
}
//砖块初始化
function initBricks(){
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
function initBang(){
	var bangWidth = width/4;
	var bangHeight = height*0.03;
	$(".myBan").css({"width":bangWidth,"height":bangHeight,"top":origin_y+height*0.688,"left":origin_x+width*3/8});
	$(".airBan").css({"width":bangWidth,"height":bangHeight,"top":origin_y+height*0.282,"left":origin_x+width*3/8});
}

//游戏界面初始化
function interfaceInit(lookWidth,lookHeight){
	width = lookHeight/16*9;
	height =lookHeight;
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
	$("#time").css({"top":origin_y+height/2-$("#time").height()/2,"left":origin_x+vsWidth*0.4});
	
	return [width,height];
}
