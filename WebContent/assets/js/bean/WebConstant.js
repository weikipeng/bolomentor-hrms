$.ajaxSetup({
    async : false
});
WebConstant = function(){
	this.COPYRIGHT = "2014 &copy; 伯乐通版权所有.";
	this.TITLE = "伯乐通工作管理系统V0.61";
//	this.COPYRIGHT = "2014 © 伯乐通版权所有.";
	
	var initHtml = function(){
		//边栏
		$(".page-sidebar").empty();
		$(".page-sidebar").load("company_index.html .page-sidebar > ul");
		
		$(".header").empty();
		$(".header").load("company_index.html .header > div");

		$("#page_content_header").empty();
		$("#page_content_header").load("company_index.html #page_content_header > div");
		
		$(".page-sidebar .active").removeClass("active");
		$(".page-sidebar .selected").removeClass("selected");
	}
	return{

		init:function(){
//			$(".copyright").text(COPYRIGHT);
//			$(".footer").text(COPYRIGHT);

			if(!App.isPage("company_index")){
				console.log("initHtml ----------- ");
  				initHtml();
  			}
			$(".copyright").html(COPYRIGHT);
  			$(".footer").html(COPYRIGHT);
  			$(document).attr('title', TITLE);
  			$("#favicon").attr("href","logo.png");
  			//定义顶部横幅Logo的链接
  			var headLogoLayout = $('.brand');
  			headLogoLayout.attr("href","company_index.html");
  			//定义顶部横幅Logo
  			var headLogo = headLogoLayout.children("img");
  			headLogo.attr({src:"logo.png",alt:"logo",style:"height: 25px;"});
  			if(!App.isPage("login")){
	  			UserDao.initNowUser();
  			}
		},
		initPersonEditPage:function(){
			var nowPage ='#sidebar_person_add';
			var tHtml = $(nowPage);
			var liHtml = tHtml.parent().closest("li");
			
			liHtml.find(".title").next().addClass("selected");
			liHtml.addClass("active");
			tHtml.addClass("active");
			
			$("#wiki_page_path_parent").text("人才");
		},
		
		initPersonIndexPage:function(){
			var nowPage ='#sidebar_person_index';
			var tHtml = $(nowPage);
			var liHtml = tHtml.parent().closest("li");
			
			liHtml.find(".title").next().addClass("selected");
			liHtml.addClass("active");
			tHtml.addClass("active");
			
			$("#wiki_page_path_parent").text("人才");
		}
	};
}();
