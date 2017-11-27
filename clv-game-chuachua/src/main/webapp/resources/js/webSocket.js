var ws;
var url = "127.0.0.1:8080"+ctx;
var width;
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
			console.log("打开连接。。。");
			ws.send('{t: "b", c: ""}');
		};
		ws.onmessage = function (evnt) {
			eval("obj="+event.data);
			if(obj.t == "b"){
				//匹配信息
				if(obj.s == "s"){	
					console.log(obj.msg);
					$("#match").css("display","none");
					$("#game").css("display","block");
					width = game(ws,obj.msg);
				}else if(obj.s == "f"){
					console.log(obj.msg);
					ws.send('{t: "b", c: ""}');
				}
			}else if(obj.t == "c"){
				//游戏信息
				if(obj.s == "s"){	
					var x = parseFloat(obj.msg);
					realTime(x);
				}else if(obj.s == "f"){
					console.log(obj.msg);
					ws.send('{t: "b", c: ""}');
				}
			}else if(obj.t == "d"){
				//结算信息
			}
		};
		ws.onerror = function (evnt) {
		};
		ws.onclose = function (evnt) {
		}
	}