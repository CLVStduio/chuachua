var ws;
var url = "192.168.0.102:8080"+ctx;

window.onload = function connect(){
		if ('WebSocket' in window) {
			ws = new WebSocket("ws://"+url+"/webSocketServer");
		} else if ('MozWebSocket' in window) {
			ws = new MozWebSocket("ws://"+url+"/webSocketServer");
		} else {
			//如果是低版本的浏览器，则用SockJS这个对象，对应了后台“sockjs/webSocketServer”这个注册器，
			//它就是用来兼容低版本浏览器的
			ws = new SockJS("http://"+url+"/sockjs/webSocketServer");
		}
		ws.onopen = function (evnt) {
			$(".logger").html("成功连接");
			ws.send('{t: "b", c: ""}');
		};
		ws.onmessage = function (evnt) {
			eval("obj="+event.data);
			if(obj.t == Constants.ReceiptType.RECEIPT_TYPE_MATCH){
				//匹配信息
				if(obj.s == Constants.ReceiptStatus.ROOM_STATUS_SUCCESS){	
					console.log(obj.msg);
					$("#match").css("display","none");
					$("#game").css("display","block");
					game(ws,obj.msg);
					ws.send('{t: "'+Constants.ReceiptType.RECEIPT_TYPE_GAME
							+'", c: "'+Constants.MessageForPlayer.PLAYER_READY_OVER+'"}');
					console.log("匹配信息 over ");
				}else if(obj.s == "f"){
					console.log(obj.msg);
					ws.send('{t: "'+Constants.ReceiptType.RECEIPT_TYPE_MATCH+'", c: ""}');
				}
			}else if(obj.t == Constants.ReceiptType.RECEIPT_TYPE_GAME){
				//游戏信息
				if(obj.s == Constants.ReceiptStatus.MOVE_BAN){	
					var x = parseFloat(obj.msg);
					realBan(x);
				}else if(obj.s == Constants.ReceiptStatus.GAME_BALL_TIME){
					realGameOfTime(obj.msg);
				}else if(obj.s == Constants.ReceiptStatus.CLEAR_BRICK_TO_B){
					realBrick(obj.msg,1);
				}else if(obj.s == Constants.ReceiptStatus.CLEAR_BRICK_TO_A){
					realBrick(obj.msg,0);
				}else if(obj.s == "f"){
					console.log(obj.msg);
					ws.send('{t: "'+Constants.ReceiptType.RECEIPT_TYPE_MATCH+'", c: ""}');
				}
			}else if(obj.t == Constants.ReceiptType.RECEIPT_TYPE_SETTLE){
				//结算信息
				if(obj.s == Constants.ReceiptStatus.RECEIPT_STATUS_SUCCESS){	
					
				}
			}
		};
		ws.onerror = function (evnt) {
		};
		ws.onclose = function (evnt) {
		}
	}