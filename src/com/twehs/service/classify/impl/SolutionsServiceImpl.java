package com.twehs.service.classify.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.twehs.dao.content.ContentDAO;
import com.twehs.dao.power.TypeDAO;
import com.twehs.pojo.Content;
import com.twehs.pojo.ContentOperateTime;
import com.twehs.pojo.Type;

import com.twehs.service.classify.ProjectsService;
import com.twehs.service.classify.SolutionsService;
import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.service.classify.dto.ProjectsDTO;
import com.twehs.service.classify.dto.SolutionsDTO;
import com.twehs.utils.pubutil.Page;
import com.twehs.utils.pubutil.ComboData;

public class SolutionsServiceImpl implements SolutionsService {

	private ContentDAO solutionsDAO;
	private TypeDAO typeDAO;

	/*
	 * 
	 * 鍒嗛〉鏌ヨ瑙ｅ喅鏂规
	 */
	public List findPageSolutions(Page page, String title, Integer typeid)
			throws SQLException {
		if (typeid != null || (title != null && title.length() != 0)) // 绾︽潫鏄剧ず鐨勬儏鍐�
		{

			List<Content> listNews = solutionsDAO.selectByPaper(
					page.getStart(), page.getLimit(), title, typeid);
			/*@SuppressWarnings("rawtypes")
			List<NewsDTO> dtoList = NewsDTO.createDtos(listNews);
			int total = solutionsDAO.countAll();
			// 杩欓噷countAll鏄嚜宸辨敼鍐欑殑锛屾湰韬笉鎻愪緵杩欎釜鍑芥暟
			page.setTotal(total);
			page.setRoot(dtoList);*/
			return listNews;

		} else// 鍏ㄩ儴鏂伴椈鏄剧ず鐨勬儏鍐碉紱
		{
			// 濡傛灉鏄叏閮ㄦ樉绀虹殑鎯呭喌鍒欐樉绀哄叏閮ㄦ柊闂荤殑鍐呭锛涘嵆menuid=20鐨勬墍鏈塩ontent杈撳嚭鏄剧ず锛�
			int total = 0;
			List<Type> typeList = typeDAO.listAll(50); // 鏄剧ず鏂伴椈鐨勬墍鏈夊垎绫�
			// 閫氳繃鎵�湁鐨則ypeid鍦╟ontent閲岄潰鎵惧嚭鎵�湁鐨刢ontentid;
			List<Content> dtoListAll = new ArrayList<Content>();
			// 浣滀负鎬籲ewscontent涓哄悗闈age杈撳嚭鍋氬噯澶囷紱
			for (Type type : typeList) {
				List<Content> newslist = solutionsDAO.selectByPaper(
						page.getStart(), page.getLimit(), title,
						type.getTypeid());
				total = total + solutionsDAO.countByTypeid(type.getTypeid()); // 姹傚彇pagesize
				if (newslist.size() != 0) {
					for (Content llist : newslist) {
						dtoListAll.add(llist); // 娣诲姞鎵�湁鏄柊闂荤殑content;
					}
				}
			}

		/*	@SuppressWarnings("rawtypes")
			List<NewsDTO> dtoList = NewsDTO.createDtos(dtoListAll);
			page.setTotal(total);
			page.setRoot(dtoList);*/
			return dtoListAll;
		}
	}

	/*
	 * 鏇存柊鍜屼繚瀛樿В鍐虫柟妗�
	 */
	// 杩欓噷闇�鍔犲弬鏁帮紱
	public boolean saveOrUpdateSolutions(SolutionsDTO dto) throws SQLException {
		Content news1 = new Content();
		if (dto.getContentid() != null) {
			news1 = (Content) solutionsDAO.selectByPrimaryKey(dto
					.getContentid());
			if (news1 != null) {
				// 鏇存柊瑙ｅ喅鏂规
				ContentOperateTime products3 = new ContentOperateTime();
				products3.setContentid(dto.getContentid());
				products3.setTypeid(dto.getTypeid());
				products3.setTypename(dto.getTypename());
				products3.setTitle(dto.getTitle());
				products3.setOperatetime(dto.getOperatetime());
				products3.setDisplay(dto.isDisplay());
				products3.setSmallpicaddress(dto.getSmallpicaddress());
				products3.setSmallpicname(dto.getSmallpicname());
				products3.setContent(dto.getContent());

				solutionsDAO.updateByPrimaryKeySelective(products3);
				return true;
			}
		} else {
			// 娣诲姞瑙ｅ喅鏂规
			ContentOperateTime products2 = new ContentOperateTime();
			products2.setContentid(dto.getContentid());
			products2.setTypeid(dto.getTypeid());
			products2.setTypename(dto.getTypename());
			products2.setTitle(dto.getTitle());
			products2.setOperatetime(dto.getOperatetime());
			products2.setDisplay(dto.isDisplay());
			products2.setSmallpicaddress(dto.getSmallpicaddress());
			products2.setSmallpicname(dto.getSmallpicname());
			products2.setContent(dto.getContent());

			solutionsDAO.insertwithid(products2);
			return true;
		}
		return false;

	}

	/*
	 * 鍒犻櫎瑙ｅ喅鏂规
	 */
	public boolean deleteSolutions(Integer contentid) throws SQLException {
		try {

			solutionsDAO.deleteByPrimaryKey(contentid);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * 鏌ヨ鍒嗙被
	 */
	public List findTypeType() throws SQLException {
		List list = new ArrayList();
		@SuppressWarnings("unchecked")
		// List<Type> typeList = typeDAO.listAll(menuid);
		Integer menuid = 20;
		List<Type> typeList = typeDAO.listAll(50);
		// System.out.println("typeid"+typeList.get(0)); //鏁版嵁鏈埌杩欓噷鏉�
		for (Type type : typeList) {
			ComboData dto = new ComboData();
			dto.setValue(type.getTypeid().toString());
			dto.setText(type.getTypename());
			list.add(dto);
		}
		return list;
	}

	public ContentDAO getSolutionsDAO() {
		return solutionsDAO;
	}

	public TypeDAO getTypeDAO() {
		return typeDAO;
	}

	public void setSolutionsDAO(ContentDAO solutionsDAO) {
		this.solutionsDAO = solutionsDAO;
	}

	public void setTypeDAO(TypeDAO typeDAO) {
		this.typeDAO = typeDAO;
	}

}
