package com.twehs.service.classify;

import java.sql.SQLException;
import java.util.List;

import com.twehs.pojo.Content;
import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.utils.pubutil.Page;

public interface NewsService {
	
	
	public List findNews() throws SQLException;
	
	public Content FindNewss(Integer newsid) throws SQLException;

	public List findPageNews(Page page, String title, Integer typeid)
			throws SQLException;

	// 杩欓噷闇�鍔犲弬鏁帮紱

	public boolean saveOrUpdateNews(NewsDTO dto) throws SQLException;

	public boolean deleteNews(Integer contentid) throws SQLException;

	public List findTypeType() throws SQLException;

}
