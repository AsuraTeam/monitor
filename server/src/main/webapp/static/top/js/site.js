
(function(){
	var menus = [
		{title:'首页', href:'index.html'},
		{title:'下载', href:'download.html'},
		{title:'API 文档', href:'api.html'},
		{title:'五分钟入门', href:'introduction-in-5-minutes.html'},
			{title:'在线演示', href:'demo/helloworld.html'}
		//{title:'捐赠', href:'donate.html', tip:'觉得还不错？捐点小钱儿支持我的开发吧！', target:'_blank'}
		//https://me.alipay.com/jtopo
	];

	function drawMenus(menus){
		var ul = $('#nav_menu').empty();				
		$.each(menus, function(i, e){
			var url = e.href;
			if(location.href.indexOf('/demo/') != -1){
				url = '../'+e.href;
			}
			var li = $('<li>').addClass('menu-item').appendTo(ul);	
			var a = $('<a>').attr('href', url).attr('title', e.tip == null ? '':e.tip).html(e.title).appendTo(li);

			if(location.href.indexOf(e.href) != -1){
				a.addClass('active');
			}		
			if(e.target){
				a.attr('target', e.target);
			}
		});
	}
	
	$(document).ready(function(){
		drawMenus(menus);		
	});

})($ || jQuery);
