package com.twehs.service.classify;

import java.sql.SQLException;
import java.util.List;

import com.twehs.pojo.Content;
import com.twehs.service.classify.dto.ExperiencesDTO;
import com.twehs.utils.pubutil.Page;

public interface ExperiencesService {

	public List findPageExperiences(Page page, String title, Integer typeid)
			throws SQLException;

	public boolean saveOrUpdateExperiences(ExperiencesDTO dto)
			throws SQLException;
	
	public Content FindExperience(Integer experienceid) throws SQLException;

	public boolean deleteExperiences(Integer contentid) throws SQLException;

	public List findTypeType() throws SQLException;

}
