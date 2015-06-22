
    imgUrl1="images/a-01.jpg";
    imgtext1="11"
    imgLink1=escape("http://www.tuwa-ie.com/");
    //imgLink1="";
    
    imgUrl2="images/a-02.jpg";
    imgtext2="22"
    imgLink2=escape("http://www.tuwa-ie.com/");
    //imgLink2="";
    
  
    imgUrl3="images/a-03.jpg";
    imgtext3="33"
    imgLink3=escape("http://www.tuwa-ie.com/");
    //imgLink3="";
	
	imgUrl4="images/a-04.jpg";
    imgtext4="44"
    imgLink4=escape("http://www.tuwa-ie.com/");
    //imgLink4="";
	
	imgUrl5="images/a-05.jpg";
    imgtext5="55"
    imgLink5=escape("http://www.tuwa-ie.com/");
    //imgLink5="";

    
     var focus_width=1200
     var focus_height=440
     var text_height=0
     var swf_height = focus_height+text_height
     
     var pics=imgUrl1+"|"+imgUrl2+"|"+imgUrl3+"|"+imgUrl4+"|"+imgUrl5
     var links=imgLink1+"|"+imgLink2+"|"+imgLink3+"|"+imgLink4+"|"+imgLink5
     var texts=imgtext1+"|"+imgtext2+"|"+imgtext3+"|"+imgtext4+"|"+imgtext5
	 
     var flashCode = '<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/hotdeploy/flash/swflash.cab#version=6,0,0,0" width="'+ focus_width +'" height="'+ swf_height +'">';
     flashCode = flashCode + '<param name="allowScriptAccess" value="sameDomain"><param name="movie" value="focus2.swf"><param name="quality" value="high"><param name="bgcolor" value="#F0F0F0">';
     flashCode = flashCode + '<param name="menu" value="false"><param name=wmode value="opaque">';
     flashCode = flashCode + '<param name="FlashVars" value="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'">';
     flashCode = flashCode + '<embed src="focus2.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="'+ focus_width +'" height="'+ swf_height +'" FlashVars="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'"></embed>';
     flashCode = flashCode + '</object>';
	
     document.write(flashCode)