package com.twehs.service.classify.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.twehs.dao.content.ContentDAO;
import com.twehs.dao.power.TypeDAO;
import com.twehs.pojo.Company;
import com.twehs.pojo.Content;
import com.twehs.pojo.ContentOperateTime;
import com.twehs.pojo.Type;
import com.twehs.service.classify.NewsService;
import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.utils.pubutil.ComboData;
import com.twehs.utils.pubutil.Page;
import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.service.menu.dto.TypeDTO;

public class NewsServiceImpl implements NewsService {

	private ContentDAO newsDAO;
	private TypeDAO typeDAO;
	
	
	
	public List findNews() throws SQLException{
		int total = 0;
		List<Type> typeList = typeDAO.listAll(20); 

		List<Content> dtoListAll = new ArrayList<Content>();

        String title = null;
     //   Integer typeid = null;
        
		for (Type type : typeList) {
			List<Content> newslist = newsDAO.selectByPaper(1,
					30, title, type.getTypeid());
			total = total + newsDAO.countByTypeid(type.getTypeid()); 
			if (newslist.size() != 0) {
				for (Content llist : newslist) {
					dtoListAll.add(llist);
				//输出所有的news
				}
			}
		}
		System.out.println(dtoListAll.size());
		
		return dtoListAll;
		
		
	
	}
	
	
	
	
	public Content FindNewss(Integer newsid) throws SQLException{
		
		Content news = newsDAO.selectByPrimaryKey(newsid);
		
		return news;
		
	}
	
	

	/*
	 * 鍔ㄦ�鏌ヨ
	 */
	public List findPageNews(Page page, String title, Integer typeid)
			throws SQLException {

		if (typeid != null || (title != null && title.length() != 0)) // 绾︽潫鏄剧ず鐨勬儏鍐�
		{

			List<Content> listNews = newsDAO.selectByPaper(page.getStart(),
					page.getLimit(), title, typeid);
//			@SuppressWarnings("rawtypes")
	/*		List<NewsDTO> dtoList = NewsDTO.createDtos(listNews);
			int total = newsDAO.countAll();
			// 杩欓噷countAll鏄嚜宸辨敼鍐欑殑锛屾湰韬笉鎻愪緵杩欎釜鍑芥暟
			page.setTotal(total);
			page.setRoot(dtoList);*/
			return listNews;

		} else// 鍏ㄩ儴鏂伴椈鏄剧ず鐨勬儏鍐碉紱
		{
			// 濡傛灉鏄叏閮ㄦ樉绀虹殑鎯呭喌鍒欐樉绀哄叏閮ㄦ柊闂荤殑鍐呭锛涘嵆menuid=20鐨勬墍鏈塩ontent杈撳嚭鏄剧ず锛�
			int total = 0;
			List<Type> typeList = typeDAO.listAll(20); // 鏄剧ず鏂伴椈鐨勬墍鏈夊垎绫�
			// 閫氳繃鎵�湁鐨則ypeid鍦╟ontent閲岄潰鎵惧嚭鎵�湁鐨刢ontentid;
			List<Content> dtoListAll = new ArrayList<Content>();
			// 浣滀负鎬籲ewscontent涓哄悗闈age杈撳嚭鍋氬噯澶囷紱

			for (Type type : typeList) {
				List<Content> newslist = newsDAO.selectByPaper(page.getStart(),
						page.getLimit(), title, type.getTypeid());
				total = total + newsDAO.countByTypeid(type.getTypeid()); // 姹傚彇pagesize
				if (newslist.size() != 0) {
					for (Content llist : newslist) {
						dtoListAll.add(llist);
						// 娣诲姞鎵�湁鏄柊闂荤殑content;
					}
				}
			}
/*
			@SuppressWarnings("rawtypes")
			List<NewsDTO> dtoList = NewsDTO.createDtos(dtoListAll);
			page.setTotal(total);
			page.setRoot(dtoList);*/
			return dtoListAll;
		}
	}

	
	
	
	
	/*
	 * 淇濆瓨鍜屾洿鏂�
	 */
	public boolean saveOrUpdateNews(NewsDTO dto) throws SQLException {
		Content news1 = new Content();
		if (dto.getContentid() != null) {
			news1 = (Content) newsDAO.selectByPrimaryKey(dto.getContentid());
			if (news1 != null) {
				// 鏇存柊鏂伴椈
				ContentOperateTime news3 = new ContentOperateTime();
				news3.setContentid(dto.getContentid());
				news3.setTitle(dto.getTitle());
				news3.setTopline(dto.isTopline());
				news3.setRecommender(dto.isRecommender());
				news3.setTypeid(dto.getTypeid());
				news3.setTypename(dto.getTypename());
				news3.setSource(dto.getSource());
				news3.setAuthor(dto.getAuthor());
				news3.setContent(dto.getContent());
				news3.setOrder(dto.getOrder());
				news3.setOperatetime(dto.getOperatetime());
				news3.setDisplay(dto.isDisplay());
				newsDAO.updateByPrimaryKeySelective(news3);
				return true;
			}
		} else {
			// 娣诲姞鏂伴椈
			ContentOperateTime news2 = new ContentOperateTime();
			news2.setContentid(dto.getContentid());
			news2.setTitle(dto.getTitle());
			news2.setTopline(dto.isTopline());
			news2.setRecommender(dto.isRecommender());
			news2.setTypeid(dto.getTypeid());
			news2.setTypename(dto.getTypename());
			news2.setSource(dto.getSource());
			news2.setAuthor(dto.getAuthor());
			news2.setContent(dto.getContent());
			news2.setOrder(dto.getOrder());
			news2.setOperatetime(dto.getOperatetime());
			news2.setDisplay(dto.isDisplay());
			newsDAO.insertwithid(news2);
			return true;
		}
		return false;
	}

	/*
	 * 鍒犻櫎鏂伴椈
	 */
	public boolean deleteNews(Integer contentid) {
		try {

			newsDAO.deleteByPrimaryKey(contentid);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * 鏌ヨ鎵�湁鍒嗙被
	 */
	public List findTypeType() throws SQLException {
		List list = new ArrayList();
		@SuppressWarnings("unchecked")
		List<Type> typeList = typeDAO.listAll(20);
		// 杩欓噷20 琛ㄧずmenuid=20,鍗虫煡璇㈡墍鏈夋爮鐩槸鏂伴椈涓嬮潰鐨勫垎绫�
		/*for (Type type : typeList) {
			ComboData dto = new ComboData();
			dto.setValue(type.getTypeid().toString());
			dto.setText(type.getTypename());
			list.add(dto);
		}*/
		return typeList;
	}

	public ContentDAO getNewsDAO() {
		return newsDAO;
	}

	public void setNewsDAO(ContentDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

	public TypeDAO getTypeDAO() {
		return typeDAO;
	}

	public void setTypeDAO(TypeDAO typeDAO) {
		this.typeDAO = typeDAO;
	}
}
