package com.twehs.service.classify;

import java.sql.SQLException;
import java.util.List;

//import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.pojo.Content;
import com.twehs.service.classify.dto.ProductsDTO;
import com.twehs.utils.pubutil.Page;

public interface ProductsService {

	public List findPageProducts(Page page, String title, Integer typeid)
			throws SQLException;

	public Content FindProductss(Integer productsid) throws SQLException;
	
	public boolean saveOrUpdateProducts(ProductsDTO dto) throws SQLException;

	public boolean deleteProducts(Integer contentid) throws SQLException;

	public List findTypeType(Integer menuid) throws SQLException;
	public Integer fingMenuidByContentid(Integer contentid) throws SQLException;

}
