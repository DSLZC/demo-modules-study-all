/**
 * Created by dongsilin on 2017/1/6.
 */
 // 引入鼠标右键核心js
$('<script type="text/javascript">').attr('src', '/plugins/jquery/contextmenu/jquery.contextmenu.r2.js').appendTo('body');				
$(function(){
	// 菜单内容
	$('body').append('<div class="contextMenu" id="tab-Menus">'+
						'<ul>'+
							'<li id="flush"><b class="icon Hui-iconfont" style="color: #39DC46;">&ensp;&#xe68f;</b><span>&nbsp;刷新</span></li>'+
							'<li id="close"><b class="icon Hui-iconfont" style="color: red;">&ensp;&#xe6a6;</b><span>&nbsp;关闭</span></li>'+
							'<li id="closeOther"><b class="icon Hui-iconfont" style="color: red;">&ensp;&#xe6a6;</b><span>&nbsp;关闭其他</span></li>'+
							'<li id="closeAll"><b class="icon Hui-iconfont" style="color: red;">&ensp;&#xe6a6;</b><span>&nbsp;关闭所有</span></li>'+
						'</ul>'+
					'</div>');
					
	var click_tab;
    $(".Hui-tabNav-wp ul").contextMenu('tab-Menus', {
		menuStyle: {
			listStyle: 'none',
			margin: '0px',
			padding: '0px',
			backgroundColor: 'rgb(167, 167, 182)',
			border: '1px solid #999',
			borderRadius: '3px',
			width: '90px'
		},
		itemStyle: {
			margin: '0px',
			color: '#000',
			display: 'block',
			cursor: 'default',
			padding: '1px',
			border: '1px solid #999',
			backgroundColor: 'transparent'
		},
		itemHoverStyle: {
			border: '1px solid #0a246a',
			borderRadius: '3px',
			backgroundColor: '#b6bdd2'
		},
		bindings: {
            'flush': function(e) {
				window.frames[click_tab.index()].location.reload(true);
            },
            'close': function(t) {
				click_tab.children("i").click();
            },
            'closeOther': function(t) {
                $.each(click_tab.siblings(), function(i ,o){
					$(o).children("i").click();
				});
            },
            'closeAll': function(t) {
				$.each(click_tab.siblings(), function(i ,o){
					$(o).children("i").click();
				});
				click_tab.children("i").click();
            }
        },
        onShowMenu: function(e, menu, shadow) {
			var t = $(e.target);
			click_tab = t.is("li")? t : t.parent();
			// 点击菜单外部的iframe关闭菜单
			var activeTabIndex = $(".Hui-tabNav-wp ul li.active").index();
			if(typeof(activeTabIndex) == 'number') $(window.frames[activeTabIndex].document).one('click', function(){
				menu.hide();
				shadow.hide();
			});
            return menu;
        }

    });
	
});
