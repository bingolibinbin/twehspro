package com.twehs.utils.system;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 鏃ユ湡杞崲
 * 
 * @author sitanwo
 * 
 */
public class DateTime {

	/**
	 * @param YYYY-MM-DD
	 *            鐨勫瓧绗︿覆
	 * @return YYYY-MM-DD 鏍煎紡鐨勬棩鏈�
	 */
	public static Date toDate(String date) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = (Date) bartDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * @param YYYY-MM-DD
	 *            鐨勫瓧绗︿覆
	 * @return YYYY-MM-DD 鏍煎紡鐨勬棩鏈�
	 */
	public static String toString(Date date) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String d = bartDateFormat.format(date);
		return d;
	}
	
	/**
	 * @author sitanwo
	 * @param date
	 *            Date瀵硅薄
	 * @return YYYY骞碝M鏈圖D鏃�鏍煎紡鐨勬棩鏈�
	 */
	public static String toChineseString(Date date) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy骞碝M鏈坉d鏃");
		return format.format(date);
	}
	
	/**鍒ゅ畾鏌愭棩鏈熸槸涓�勾绗嚑鍛細
	 * @param Date mydate  鏃ユ湡鍙傛暟
	 * oracle鏁版嵁搴撲腑濡備綍鏌ヨ
	 * select to_char(sysdate,'fmww') from dual; 
	 * select to_char(to_date('20071126','YYYYMMDD'),'fmww') from dual; 
	 * @return int 鏈勾搴︾鍑犲懆
	 */
	public static Long getWEEK_OF_YEAR(Date mydate) {
		  Calendar   c   =   Calendar.getInstance()   ;   
		  c.setTime(mydate);   
		  return  new Long(c.get(Calendar.WEEK_OF_YEAR));		
	}
	
	/**鍒ゅ畾鏌愭棩鏈熸槸涓�勾绗嚑鍛細
	 * @param Date mydate  鏃ユ湡鍙傛暟
	 * oracle鏁版嵁搴撲腑濡備綍鏌ヨ
	 * select to_char(sysdate,'fmww') from dual; 
	 * select to_char(to_date('20071126','YYYYMMDD'),'fmww') from dual; 
	 * @return String 骞村害-绗嚑鍛�
	 */
	public static String getWEEK_OF_YEAR_STR(Date mydate) {
		  Calendar   c   =   Calendar.getInstance()   ;   
		  c.setTime(mydate);  
		  c.setFirstDayOfWeek(Calendar.MONDAY); //璁剧疆涓�槦鏈熺殑绗竴澶╂槸鍝竴澶�
		  int weekofyesr = c.get(Calendar.WEEK_OF_YEAR);
		  String yearnum = new SimpleDateFormat("yyyy").format(new Date());;
		  if(weekofyesr<10){
			  return yearnum+"-0"+weekofyesr;
		  }else{
			  return yearnum+"-"+weekofyesr;	
		  }
		 	
	}
	
	/**
	 * 鏃ユ湡澶╂暟鍔犲噺
	 */
	@SuppressWarnings("static-access")
	public static String getNewDay(String date, int dateValue) { 
		Calendar calObj = Calendar.getInstance(); 
		SimpleDateFormat sfObj = new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			calObj.setTime(sfObj.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		calObj.add(calObj.DATE, dateValue); 
		return sfObj.format(calObj.getTime()); 
	}
	
	/**
	 * 璁＄畻涓や釜鏃ユ湡涔嬮棿鐨勫懆
	 */
	@SuppressWarnings("unchecked")
	public static List getWeeksBetweenDates(Date d1, Date d2) {
		List list = new ArrayList();
		Calendar dateFrom = Calendar.getInstance();
		Calendar dateTo = Calendar.getInstance();
		dateFrom.setTime(d1);
		dateTo.setTime(d2);
		dateFrom.setFirstDayOfWeek(Calendar.MONDAY);  //璁剧疆涓�槦鏈熺殑绗竴澶╂槸鍛ㄤ竴
		dateTo.setFirstDayOfWeek(Calendar.MONDAY);
		int yearFrom = dateFrom.get(Calendar.YEAR);  //寮�鏃ユ湡鐨勫勾
		int yearTo = dateTo.get(Calendar.YEAR);      //缁撴潫鏃ユ湡鐨勫勾
		int weekFrom = dateFrom.get(Calendar.WEEK_OF_YEAR); //寮�鏃ユ湡鐨勫懆
		int weekTo = dateTo.get(Calendar.WEEK_OF_YEAR);     //缁撴潫鏃ユ湡鐨勫懆
		if(weekFrom==1&&dateFrom.get(Calendar.MONTH)==Calendar.DECEMBER)//濡傛灉鏄涓�懆骞朵笖鏄�2鏈堝垯涓轰笅涓�勾鐨勫懆涓�
		{
			yearFrom++;
		}
		if(weekTo==1&&dateTo.get(Calendar.MONTH)==Calendar.DECEMBER)//12鏈�
		{
			yearTo++;
		}
		if(weekFrom<10){
			list.add(yearFrom+"-0"+weekFrom);
		}else{
			list.add(yearFrom+"-"+weekFrom);	
		}
		while(!(yearFrom==yearTo&&weekFrom==weekTo)){
			dateFrom.add(Calendar.WEEK_OF_YEAR, 1);
			weekFrom = dateFrom.get(Calendar.WEEK_OF_YEAR);
			yearFrom = dateFrom.get(Calendar.YEAR);
			if(weekFrom==1&&dateFrom.get(Calendar.MONTH)==Calendar.DECEMBER)//12鏈�
			{
				yearFrom++;
			}
			if(weekFrom<10){
				list.add(yearFrom+"-0"+weekFrom);
			}else{
				list.add(yearFrom+"-"+weekFrom);	
			}
		}		
		return list;
	}
    /**
     * 寰楀埌杩欎釜鏈堟湁澶氬皯澶�
     * @param year
     * @param month
     * @return
     */
    public static Integer getDaysForThisMonth(int year,int month){
    	Calendar cal = Calendar.getInstance();   
    	cal.set(year, month-1, 1);
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//鏈湀澶氬皯澶�
    	return day;
    }
    /**
     * 寰楀埌褰撳墠鏈堜唤鏍煎紡涓篩YYY-MM锛�
     * 杩斿洖瀛楃涓�
     * */
    public static String getYearMonth(){
    	 Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //鑾峰彇涓滃叓鍖烘椂闂�

		  String year = String.valueOf(c.get(Calendar.YEAR));    //鑾峰彇骞�
		  String month =String.valueOf(c.get(Calendar.MONTH) + 1<10?"0"+(c.get(Calendar.MONTH) + 1):c.get(Calendar.MONTH) + 1);    //鑾峰彇鏈堜唤锛�琛ㄧず1鏈堜唤
		  
		  return year + "-" + month;
    }
    /**
     * startmonth杞崲涓篩YYY-MM-01锛�
     * 杩斿洖date绫诲瀷
     * */
    public static String startmonthToDate(String startmonth){
 
		return startmonth + "-01";
    }
    
    /**
     * endmonth杞崲涓篩YYY-MM + 褰撴湀鐨勬渶鍚庝竴澶╋紝
     * 杩斿洖date绫诲瀷
     * */
    public static String endmonthToDate(String endmonth){
    	String y = endmonth.substring(0,4);
    	String d = endmonth.substring(5,7);
    	
    	int year = Integer.parseInt(y);
    	int day = Integer.parseInt(d);
    	
    	int lastday = getDaysForThisMonth(year,day);
    	
    	return endmonth +"-" + lastday;
    	
    }
}
