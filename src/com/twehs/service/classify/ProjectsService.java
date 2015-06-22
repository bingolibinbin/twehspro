package com.twehs.service.classify;

import java.sql.SQLException;
import java.util.List;

import com.twehs.pojo.Content;
import com.twehs.service.classify.dto.ProductsDTO;
import com.twehs.service.classify.dto.ProjectsDTO;
import com.twehs.utils.pubutil.Page;

public interface ProjectsService {

	/*
	 * findPageProjects findTypeType deleteProjects saveOrUpdateProjects
	 */

	public List findPageProjects(Page page, String title, Integer typeid)
			throws SQLException;

	public boolean saveOrUpdateProjects(ProjectsDTO dto) throws SQLException;
	public Content FindProjects(Integer projectsid) throws SQLException;

	public boolean deleteProjects(Integer contentid) throws SQLException;

	public List findTypeType() throws SQLException;
}
