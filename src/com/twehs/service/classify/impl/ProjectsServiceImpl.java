package com.twehs.service.classify.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.twehs.dao.content.ContentDAO;
import com.twehs.dao.power.TypeDAO;
import com.twehs.pojo.Content;
import com.twehs.pojo.ContentOperateTime;
import com.twehs.pojo.Type;
//import com.twehs.service.classify.NewsService;
import com.twehs.service.classify.ProductsService;
import com.twehs.service.classify.ProjectsService;
//import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.service.classify.dto.ProjectsDTO;
import com.twehs.utils.pubutil.Page;
import com.twehs.utils.pubutil.ComboData;

public class ProjectsServiceImpl implements ProjectsService {

	private ContentDAO projectsDAO;
	private TypeDAO typeDAO;

	
    public Content FindProjects(Integer projectsid) throws SQLException{
		
		Content products = projectsDAO.selectByPrimaryKey(projectsid);
		
		return products;
		
	}
	
	
	/*
	 * 鍒嗛〉鏌ヨ宸ョ▼
	 */
	public List findPageProjects(Page page, String title, Integer typeid)
			throws SQLException {
		if (typeid != null || (title != null && title.length() != 0)) // 绾︽潫鏄剧ず鐨勬儏鍐�
		{

			List<Content> listNews = projectsDAO.selectByPaper(page.getStart(),
					page.getLimit(), title, typeid);
			return listNews;

		} else// 鍏ㄩ儴鏂伴椈鏄剧ず鐨勬儏鍐碉紱
		{
			int total = 0;
			List<Type> typeList = typeDAO.listAll(40); // 鏄剧ず鏂伴椈鐨勬墍鏈夊垎绫�
			List<Content> dtoListAll = new ArrayList<Content>(); // 浣滀负鎬籲ewscontent涓哄悗闈age杈撳嚭鍋氬噯澶囷紱

			for (Type type : typeList) {
				List<Content> newslist = projectsDAO.selectByPaper(
						page.getStart(), page.getLimit(), title,
						type.getTypeid());
				total = total + projectsDAO.countByTypeid(type.getTypeid()); // 姹傚彇pagesize
				if (newslist.size() != 0) {
					for (Content llist : newslist) {
						dtoListAll.add(llist); // 娣诲姞鎵�湁鏄柊闂荤殑content;
					}
				}
			}

			return dtoListAll;
		}
	}

	public boolean saveOrUpdateProjects(ProjectsDTO dto) throws SQLException {
		Content news1 = new Content();
		if (dto.getContentid() != null) {
			news1 = (Content) projectsDAO
					.selectByPrimaryKey(dto.getContentid());
			if (news1 != null) {
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

				projectsDAO.updateByPrimaryKeySelective(products3);
				return true;
			}
		} else {
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

			projectsDAO.insertwithid(products2);
			return true;
		}
		return false;

	}

	public boolean deleteProjects(Integer contentid) throws SQLException {
		try {

			projectsDAO.deleteByPrimaryKey(contentid);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * 
	 * 鏌ヨ鍒嗙被
	 */
	public List findTypeType() throws SQLException {
		List list = new ArrayList();
		@SuppressWarnings("unchecked")
		List<Type> typeList = typeDAO.listAll(40);
		// 宸ョ▼瀵瑰簲鐨刴enuid=40;
	/*	for (Type type : typeList) {
			ComboData dto = new ComboData();
			dto.setValue(type.getTypeid().toString());
			dto.setText(type.getTypename());
			list.add(dto);
		}*/
		return typeList;
	}

	public ContentDAO getProjectsDAO() {
		return projectsDAO;
	}

	public TypeDAO getTypeDAO() {
		return typeDAO;
	}

	public void setProjectsDAO(ContentDAO projectsDAO) {
		this.projectsDAO = projectsDAO;
	}

	public void setTypeDAO(TypeDAO typeDAO) {
		this.typeDAO = typeDAO;
	}

}
