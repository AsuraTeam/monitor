
	
// 页面工具栏
function showJTopoToobar(stage){
	var toobarDiv = $('<div class="jtopo_toolbar">').html(''
		+'<input type="radio" name="modeRadio" value="normal" checked id="r1"/>'
		+'<label for="r1"> 默认</label>'
		+'&nbsp;<input type="radio" name="modeRadio" value="select" id="r2"/><label for="r2"> 框选</label>'
		+'&nbsp;<input type="radio" name="modeRadio" value="edit" id="r4"/><label for="r4"> 加线</label>'
		+'&nbsp;&nbsp;<input type="button" id="centerButton" value="居中显示"/>'
		+'<input type="button" id="fullScreenButton" value="全屏显示"/>'
		+'<input type="button" id="zoomOutButton" value=" 放 大 " />'
		+'<input type="button" id="zoomInButton" value=" 缩 小 " />'
		+'&nbsp;&nbsp;<input type="checkbox" id="zoomCheckbox"/><label for="zoomCheckbox">鼠标缩放</label>'
		+'&nbsp;&nbsp;<input type="text" id="findText" style="width: 100px;" value="" onkeydown="enterPressHandler(event)">'
		+ '<input type="button" id="findButton" value=" 查 询 ">' 
		+ '&nbsp;&nbsp;<input type="button" id="cloneButton" value="选中克隆">'
		+'&nbsp;&nbsp;<input type="button" id="exportButton" value="导出PNG">'
		+ '&nbsp;&nbsp;<input type="button" id="printButton" value="导出PDF">');	
		
	$('#content').prepend(toobarDiv);

	// 工具栏按钮处理
	$("input[name='modeRadio']").click(function(){			
		stage.mode = $("input[name='modeRadio']:checked").val();
	});
	$('#centerButton').click(function(){
		stage.centerAndZoom(); //缩放并居中显示
	});
	$('#zoomOutButton').click(function(){
		stage.zoomOut();
	});
	$('#zoomInButton').click(function(){
		stage.zoomIn();
	});
	$('#cloneButton').click(function(){
		stage.saveImageInfo();
	});
	$('#exportButton').click(function() {
	    stage.saveImageInfo();
	});
	$('#printButton').click(function() {
	    stage.saveImageInfo();
	});
	$('#zoomCheckbox').click(function(){
		if($('#zoomCheckbox').is(':checked')){
			stage.wheelZoom = 1.2; // 设置鼠标缩放比例
		}else{
			stage.wheelZoom = null; // 取消鼠标缩放比例
		}
	});
	$('#fullScreenButton').click(function(){
		runPrefixMethod(stage.canvas, "RequestFullScreen")
	});

	window.enterPressHandler = function (event){
		if(event.keyCode == 13 || event.which == 13){
			$('#findButton').click();
		}
	};
	
	// 查询
	$('#findButton').click(function(){
		var text = $('#findText').val().trim();
		//var nodes = stage.find('node[text="'+text+'"]');
		var scene = stage.childs[0];
		var nodes = scene.childs.filter(function(e){ 
			return e instanceof JTopo.Node; 
		});
		nodes = nodes.filter(function(e){
			if(e.text == null) return false;
			return e.text.indexOf(text) != -1;
		});
		
		if(nodes.length > 0){
			var node = nodes[0];
			node.selected = true;
			var location = node.getCenterLocation();
			// 查询到的节点居中显示
			stage.setCenter(location.x, location.y);
			
			function nodeFlash(node, n){
				if(n == 0) {
					node.selected = false;
					return;
				};
				node.selected = !node.selected;
				setTimeout(function(){
					nodeFlash(node, n-1);
				}, 300);
			}
			
			// 闪烁几下
			nodeFlash(node, 6);
		}
	});
}

var runPrefixMethod = function(element, method) {
	var usablePrefixMethod;
	["webkit", "moz", "ms", "o", ""].forEach(function(prefix) {
		if (usablePrefixMethod) return;
		if (prefix === "") {
			// 无前缀，方法首字母小写
			method = method.slice(0,1).toLowerCase() + method.slice(1);
		}
		var typePrefixMethod = typeof element[prefix + method];
		if (typePrefixMethod + "" !== "undefined") {
			if (typePrefixMethod === "function") {
				usablePrefixMethod = element[prefix + method]();
			} else {
				usablePrefixMethod = element[prefix + method];
			}
		}
	}
);

return usablePrefixMethod;
};
/*
runPrefixMethod(this, "RequestFullScreen");
if (typeof window.screenX === "number") {
var eleFull = canvas;
eleFull.addEventListener("click", function() {
	if (runPrefixMethod(document, "FullScreen") || runPrefixMethod(document, "IsFullScreen")) {
		runPrefixMethod(document, "CancelFullScreen");
		this.title = this.title.replace("退出", "");
	} else if (runPrefixMethod(this, "RequestFullScreen")) {
		this.title = this.title.replace("点击", "点击退出");
	}
});
} else {
alert("浏览器不支持");
}*/
