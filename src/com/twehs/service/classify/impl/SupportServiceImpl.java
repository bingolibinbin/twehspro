package com.twehs.service.classify.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.twehs.dao.content.ContentDAO;
import com.twehs.dao.power.TypeDAO;
import com.twehs.pojo.Content;
import com.twehs.pojo.ContentOperateTime;
import com.twehs.pojo.Type;
import com.twehs.service.classify.NewsService;
import com.twehs.service.classify.SupportService;
import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.service.classify.dto.SupportDTO;
import com.twehs.utils.pubutil.ComboData;
import com.twehs.utils.pubutil.Page;
import com.twehs.service.menu.dto.TypeDTO;

   public class SupportServiceImpl implements SupportService {

	private ContentDAO supportsDAO;
	private TypeDAO typeDAO;

	
	
	
   public Content FindSupports(Integer supportsid) throws SQLException{
		
		Content products = supportsDAO.selectByPrimaryKey(supportsid);
		
		return products;
		
	}
	
	
	
	
	/*
	 * 鍔ㄦ�鏌ヨ
	 */
	public List findPageSupports(Page page, String title, Integer typeid)
			throws SQLException {

		if (typeid != null || (title != null && title.length() != 0)) // 绾︽潫鏄剧ず鐨勬儏鍐�
		{
			List<Content> listNews = supportsDAO.selectByPaper(page.getStart(),
					page.getLimit(), title, typeid);
		/*	@SuppressWarnings("rawtypes")
			List<NewsDTO> dtoList = SupportDTO.createDtos(listNews);
			int total = supportsDAO.countAll();
			// 杩欓噷countAll鏄嚜宸辨敼鍐欑殑锛屾湰韬笉鎻愪緵杩欎釜鍑芥暟
			page.setTotal(total);
			page.setRoot(dtoList);*/
			return listNews;

		} else// 鍏ㄩ儴鏂伴椈鏄剧ず鐨勬儏鍐碉紱
		{
			int total = 0;
			List<Type> typeList = typeDAO.listAll(60); // 鏄剧ず鏀寔鐨勬墍鏈夊垎绫�
			List<Content> dtoListAll = new ArrayList<Content>();

			for (Type type : typeList) {
				List<Content> newslist = supportsDAO.selectByPaper(
						page.getStart(), page.getLimit(), title,
						type.getTypeid());
				total = total + supportsDAO.countByTypeid(type.getTypeid()); // 姹傚彇pagesize
				if (newslist.size() != 0) {
					for (Content llist : newslist) {
						dtoListAll.add(llist);
						// 娣诲姞鎵�湁鏄柊闂荤殑content;
					}
				}
			}

		/*	@SuppressWarnings("rawtypes")
			List<SupportDTO> dtoList = SupportDTO.createDtos(dtoListAll);
			page.setTotal(total);
			page.setRoot(dtoList);*/
			
			return dtoListAll;
		}
	}

	/*
	 * 淇濆瓨鍜屾洿鏂�
	 */
	public boolean saveOrUpdateSupports(SupportDTO dto) throws SQLException {
		Content sup1 = new Content();
		if (dto.getContentid() != null) {
			sup1 = (Content) supportsDAO.selectByPrimaryKey(dto.getContentid());
			if (sup1 != null) {
				// 鏇存柊鏂伴椈
				ContentOperateTime sup3 = new ContentOperateTime();
				sup3.setContentid(dto.getContentid());
				sup3.setTypeid(dto.getTypeid());
				sup3.setTypename(dto.getTypename());
				sup3.setTitle(dto.getTitle());
				sup3.setOperatetime(dto.getOperatetime());
				sup3.setDisplay(dto.isDisplay());
				sup3.setContent(dto.getContent());
				supportsDAO.updateByPrimaryKeySelective(sup3);
				return true;
			}
		} else {
			// 娣诲姞鏂伴椈
			ContentOperateTime sup2 = new ContentOperateTime();
			sup2.setContentid(dto.getContentid());
			sup2.setTypeid(dto.getTypeid());
			sup2.setTypename(dto.getTypename());
			sup2.setTitle(dto.getTitle());
			sup2.setOperatetime(dto.getOperatetime());
			sup2.setDisplay(dto.isDisplay());
			sup2.setContent(dto.getContent());
			supportsDAO.insertwithid(sup2);
			return true;
		}
		return false;
	}

	/*
	 * 鍒犻櫎鏂伴椈
	 */
	public boolean deleteSupports(Integer contentid) {
		try {

			supportsDAO.deleteByPrimaryKey(contentid);
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
		List<Type> typeList = typeDAO.listAll(60);
		// 杩欓噷20 琛ㄧずmenuid=20,鍗虫煡璇㈡墍鏈夋爮鐩槸鏂伴椈涓嬮潰鐨勫垎绫�
	/*	for (Type type : typeList) {
			ComboData dto = new ComboData();
			dto.setValue(type.getTypeid().toString());
			dto.setText(type.getTypename());
			list.add(dto);
		}*/
		return typeList;
	}

	public TypeDAO getTypeDAO() {
		return typeDAO;
	}

	public void setTypeDAO(TypeDAO typeDAO) {
		this.typeDAO = typeDAO;
	}

	public ContentDAO getSupportsDAO() {
		return supportsDAO;
	}

	public void setSupportsDAO(ContentDAO supportsDAO) {
		this.supportsDAO = supportsDAO;
	}

}
