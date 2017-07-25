
(function(){
	var demos = [
		'核心对象', 
		{text:'Hello world', href:'helloworld.html'},
		{text:'节点', href:'node.html'},
		{text:'连线', href:'link.html'},		
		{text:'连线组', href:'bundlelink.html'},
		{text:'文字定位', href:'node-label-position.html'},		
		{text:'容器分组', href:'container.html'},
		{text:'性能测试', href:'performance_test.html'},
		{text:'动画', href:'animate_stepbystep.html'},			
		{text:'动画性能测试', href:'animation_performance.html'},
		{text:'鹰眼', href:'eagle-eye.html'},
		'网络拓扑',
		{text:'设备关系', href:'statictis.html'},
		{text:'PSTN', href:'pstn.html'},
		{text:'矩阵', href:'matrix.html'},		
		'拓扑图形',
		{text:'圆形布局', href:'layout-circle.html'},
		{text:'树形布局', href:'layout-tree.html'},
		{text:'混合布局', href:'layout-mix.html'},
		{text:'树形布局(全自动)', href:'topo.html'},
		{text:'告警', href:'topo-alarm.html'},
		{text:'在线编辑', href:'edit.html'},
		'统计图表',
		{text:'饼图', href:'topo-pieChart.html'},
		{text:'柱状图', href:'topo-barChart.html'},
		//{text:'布局', href:'topo-layout.html'},
		//{text:'布局2', href:'topo-layout2.html'},		
		//{text:'混合布局', href:'topo-layout-mix-2.html'},
		//{text:'布局-动态', href:'topo-layout3.html'},
		'动画效果',
		{text:'重力', href:'animate_grivty.html'},
		{text:'美丽星空', href:'animate_spring_mass.html'},
		{text:'弹性-简单', href:'animate_spring.html'},
		{text:'弹性-弹簧链', href:'animate_spring_chain.html'},
		{text:'弹性-多节点', href:'animate_spring_circle.html'},
		{text:'弹性-控制点', href:'animate_spring_multi_target.html'},	
		'其他',
		{text:'右键菜单', href:'right-menu.html'},
		{text:'在线脑图', href:'naotu.html'},
		{text:'切水果', href:'cutFruit.html'},
		{text:'泡泡堂', href:'paopao.html'},
		{text:'GIS地图', href:'map.html'},
		{text:'序列化反序列化', href:'serialized.html'},
		{text:'场景切换', href:'scence_event.html'},		
		{text:'Gif模拟', href:'animate_gif.html'},
		{text:'Win7桌面', href:'topo-desktop.html'},
		{text:'乌龟绘图', href:'hello-logo.html'},
		{text:'联系作者', href:'contact.html'}
			
	];

	function drawMenus(menus){
		var ul = $('#menu').empty();
		var li = null;
		var children = null;
		$.each(demos, function(i, e){
			if(typeof e == 'string'){
				li = $('<li>').appendTo(ul).addClass('cat-item cat-item-1').appendTo(ul);
				$('<a>').html(e).appendTo(li);
				children = $('<ul>').addClass('children').appendTo(li);
			}else{
				var cli = $('<li>').addClass('cat-item cat-item-5').appendTo(children);
				$('<a>').attr('href', './' + e.href).html(e.text).appendTo(cli);
			}
		});
	}

	String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
    } else {
        return this.replace(reallyDo, replaceWith);
    }};
	
	$(document).ready(function(){
		var canvas = $('#canvas');
		if(canvas.length > 0){
			canvas.css({
				'background-color': '#EEEEEE',
				'border': '1px solid #444'
			});
		}
	
		var content = $('#content');
		drawMenus(demos);	
		var code = $('#code').text();
		code = code.replaceAll('>', '&gt;');
		code = code.replaceAll('<', '&lt;');
		var pre = $('<pre width="600" style="margin-top:3px;">').appendTo($('#canvas').parent().css('width', '850px')).html(code);
		pre.snippet("javascript",{style:"acid",collapse:true});	
		
		window.scroll(0, 110);
	});
})();

