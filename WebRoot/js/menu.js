$(document).ready(function(e) {
	$(".links_ul").hide();
    $("#aboutme").hover(function(){
		$(this).find(".links_ul").html("<li><a href='javascript:void(0);'>公司动态</a></li>");
		$(this).find(".links_ul").show();
	},function(){
		$(this).find(".links_ul").hide();
		$(this).find(".links_ul").empty("li");
	});
	
	$("#news_").hover(function(){
		$(this).find(".links_ul").html("<li><a href='javascript:void(0);'>行业新闻</a></li>");
		$(this).find(".links_ul").show();
	},function(){
		$(this).find(".links_ul").hide();
		$(this).find(".links_ul").empty("li");
	});
	
	$("#products_center").hover(function(){
		$(this).find(".links_ul").html("<li><a href='javascript:void(0);'>照明</a></li>");
		$(this).find(".links_ul").show();
	},function(){
		$(this).find(".links_ul").hide();
		$(this).find(".links_ul").empty("li");
	});
	
	$("#technaloge").hover(function(){
		$(this).find(".links_ul").html("<li><a href='javascript:void(0);'>售后服务</a></li>");
		$(this).find(".links_ul").show();
	},function(){
		$(this).find(".links_ul").hide();
		$(this).find(".links_ul").empty("li");
	});
	
	$("#cases").hover(function(){
		$(this).find(".links_ul").html("<li><a href='javascript:void(0);'>照明案列</a></li>"
			+"<li><a href='javascript:void(0);'>南昌****政府安防布线</a></li>");
		
		$(this).find("li").css({"display":"block"});
		$(this).find(".links_ul").show();
	},function(){
		$(this).find(".links_ul").hide();
		$(this).find(".links_ul").empty("li");
	});
	
	$("#exp").hover(function(){
		$(this).find(".links_ul").html("<li><a href='javascript:void(0);'>体验产品</a></li>");
		$(this).find(".links_ul").show();
	},function(){
		$(this).find(".links_ul").hide();
		$(this).find(".links_ul").empty("li");
	});
	
	$("#linkus").hover(function(){
		$(this).find(".links_ul").html("<li><a href='javascript:void(0);'>公司详情</a></li>");
		$(this).find(".links_ul").show();
	},function(){
		$(this).find(".links_ul").hide();
		$(this).find(".links_ul").empty("li");
	});
});