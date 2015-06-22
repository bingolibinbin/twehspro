// JavaScript Document

	
		
		function Favorite(sURL, sTitle) {
		try {
		
			window.external.addFavorite(sURL, sTitle);
		}
		catch (e) {
		try {
			window.sidebar.addPanel(sTitle, sURL, "");
		}
		catch (e) {
			alert("加入收藏失败,请手动添加.");
		}
		}
		}



