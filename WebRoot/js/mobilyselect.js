/* ==========================================================
 * MobilySelect
 * date: 18.1.2010
 * author: Marcin Dziewulski
 * last update: 25.1.2010
 * web: http://www.mobily.pl or http://playground.mobily.pl
 * email: hello@mobily.pl
 * Free to use under the MIT license.
 * ========================================================== */
(function($) {
	$.fn.mobilyselect = function(options) {
		var defaults = {
			collection : "all",
			animation : "absolute",
			duration : 500,
			listClass : "selecterContent",
			btnsClass : "selecterBtns",
			btnActiveClass : "active",
			elements : "li",
			onChange : function() {
			},
			onComplete : function() {
			}
		};
		var sets = $.extend( {}, defaults, options);
		return this
				.each(function() {
					var $t = $(this), list = $t.find("." + sets.listClass), 
					btns = $t.find("." + sets.btnsClass), 
					btn = btns.find("a"), li = list.find(sets.elements), 
					w = li.width(), 
					h = li.height(), 
					l = li.length, finishTime;
					
					if (sets.animation == "absolute") {
						li.css( {
							position : "relative"
						}).children().css( {
							position : "absolute",
							top : 0,
							left : 0
						})
					}
					var select = {
						init : function() {
							this.start();
							this.trigger()
						},
						start : function() {
							
							if (sets.collection != "all") {
								li.hide().filter("." + sets.collection).show();
								
								btn
										.removeClass(sets.btnActiveClass)
										.filter(
												function() {
													return $(this).attr("rel") == sets.collection
													
												})
										.addClass(sets.btnActiveClass)
							}
						},
						trigger : function() {
							btn
									.bind(
											"click",
											function() {
												var $t = $(this), rel = $t
														.attr("rel"), selected = li
														.filter("." + rel), s = li
														.filter(function() {
															return $(this).css(
																	"display") == "block"
														});
												
												if (rel == "all") {
													if (l != s.length) {
														select
																.animation(li,
																		li)
													}
												} else {
													select.animation(li,
															selected)
												}
												btn
														.removeClass(sets.btnActiveClass);
												$t
														.addClass(sets.btnActiveClass);
												sets.onChange.call(this);
												return false
											})
						},
						animation : function(not, selected) {
							switch (sets.animation) {
							case "plain":
								$(not).hide();
								$(selected).show();
								break;
							case "fade":
								$(not).fadeOut(sets.duration);
								setTimeout(function() {
									$(selected).fadeIn(sets.duration)
								}, sets.duration + 400);
								break;
							case "absolute":
								setTimeout(function() {
									$(selected).show().children().animate( {
										top : 0,
										left : 0
									}, sets.duration)
								}, sets.duration + 400);
								$(not).children().animate( {
									top : -h + "px",
									left : -w + "px"
								}, sets.duration, function() {
									$(not).hide()
								});
								break
							}
							if (sets.animation == "absolute"
									|| sets.animation == "fade") {
								finishTime = sets.duration * 2 + 400
							} else {
								finishTime = 100
							}
							setTimeout(function() {
								sets.onComplete.call(this)
							}, finishTime)
						}
					};
					select.init()
				})
	}
}(jQuery));