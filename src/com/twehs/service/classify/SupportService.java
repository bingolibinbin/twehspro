package com.twehs.service.classify;

import java.sql.SQLException;
import java.util.List;

import com.twehs.pojo.Content;
import com.twehs.service.classify.dto.SupportDTO;
import com.twehs.utils.pubutil.Page;

public interface SupportService {

	public List findPageSupports(Page page, String title, Integer typeid)
			throws SQLException;
	
	public Content FindSupports(Integer supportsid) throws SQLException;

	// 杩欓噷闇�鍔犲弬鏁帮紱
	public boolean saveOrUpdateSupports(SupportDTO dto) throws SQLException;

	public boolean deleteSupports(Integer contentid) throws SQLException;

	public List findTypeType() throws SQLException;

}
