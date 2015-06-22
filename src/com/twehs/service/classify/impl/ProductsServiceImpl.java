package com.twehs.service.classify.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.twehs.dao.content.ContentDAO;
import com.twehs.dao.power.MenuDAO;
import com.twehs.dao.power.TypeDAO;
import com.twehs.pojo.Content;
import com.twehs.pojo.ContentOperateTime;
import com.twehs.pojo.Menu;
import com.twehs.pojo.Type;
//import com.twehs.service.classify.NewsService;
import com.twehs.service.classify.ProductsService;
import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.service.classify.dto.ProductsDTO;
import com.twehs.utils.pubutil.Page;
import com.twehs.utils.pubutil.ComboData;

public class ProductsServiceImpl implements ProductsService {

	private ContentDAO productsDAO;
	private TypeDAO typeDAO;
	private MenuDAO menuDAO;
	
	
	
	






	public Content FindProductss(Integer productsid) throws SQLException{
		
		Content products = productsDAO.selectByPrimaryKey(productsid);
		
		return products;
		
	}
	
	
	
	
	

	/*
	 * 鍒嗛〉鏌ヨ浜у搧
	 */
	public List findPageProducts(Page page, String title, Integer typeid)
			throws SQLException {

		if (typeid != null || (title != null && title.length() != 0))
		// 绾︽潫鏄剧ず鐨勬儏鍐�
		{

			List<Content> listNews = productsDAO.selectByPaper(page.getStart(),
					page.getLimit(), title, typeid);
		/*	@SuppressWarnings("rawtypes")
			List<NewsDTO> dtoList = NewsDTO.createDtos(listNews);
			int total = productsDAO.countAll();
			// 杩欓噷countAll鏄嚜宸辨敼鍐欑殑锛屾湰韬笉鎻愪緵杩欎釜鍑芥暟
			page.setTotal(total);
			page.setRoot(dtoList);*/
			return listNews;
			

		} else// 鍏ㄩ儴鏂伴椈鏄剧ず鐨勬儏鍐碉紱
		{
			// 濡傛灉鏄叏閮ㄦ樉绀虹殑鎯呭喌鍒欐樉绀哄叏閮ㄦ柊闂荤殑鍐呭锛涘嵆menuid=20鐨勬墍鏈塩ontent杈撳嚭鏄剧ず锛�
			int total = 0;
			List<Type> typeList = typeDAO.listAll(30); // 鏄剧ず鏂伴椈鐨勬墍鏈夊垎绫�
			// 閫氳繃鎵�湁鐨則ypeid鍦╟ontent閲岄潰鎵惧嚭鎵�湁鐨刢ontentid;
			List<Content> dtoListAll = new ArrayList<Content>();
			// 浣滀负鎬籲ewscontent涓哄悗闈age杈撳嚭鍋氬噯澶囷紱
			for (Type type : typeList) {
				List<Content> newslist = productsDAO.selectByPaper(
						page.getStart(), page.getLimit(), title,
						type.getTypeid());
				total = total + productsDAO.countByTypeid(type.getTypeid()); // 姹傚彇pagesize
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

	// 杩欓噷闇�鍔犲弬鏁帮紱

	/*
	 * 淇濆瓨鍜屾洿鏂颁骇鍝�
	 */
	public boolean saveOrUpdateProducts(ProductsDTO dto) throws SQLException {
		Content news1 = new Content();
		if (dto.getContentid() != null) {
			news1 = (Content) productsDAO.selectByPrimaryKey(dto.getContentid());
			if (news1.getContentid() != null) {
				// 鏇存柊鏂伴椈
				ContentOperateTime products3 = new ContentOperateTime();
				products3.setContentid(dto.getContentid());
				products3.setTypeid(dto.getTypeid());
				products3.setTypename(dto.getTypename());
				products3.setTitle(dto.getTitle());
				products3.setTopline(dto.getTopline());
				products3.setRecommender(dto.getRecommender());
				products3.setOperatetime(dto.getOperatetime());
				products3.setDisplay(dto.getDisplay());
				products3.setSmallpicaddress(dto.getSmallpicaddress());
				products3.setSmallpicname(dto.getMidpicaddress());
				products3.setSource(dto.getBigpicaddress());
				products3.setSpecification(dto.getSpecification());
				products3.setPrice(dto.getPrice());
				products3.setContent(dto.getContent());
				products3.setOrder(dto.getOrder());

				productsDAO.updateByPrimaryKeySelective(products3);
				return true;
			}
		} else {
			// 娣诲姞鏂伴椈
			ContentOperateTime products2 = new ContentOperateTime();
			products2.setContentid(dto.getContentid());
			products2.setTypeid(dto.getTypeid());
			products2.setTypename(dto.getTypename());
			products2.setTitle(dto.getTitle());
			products2.setTopline(dto.getTopline());
			products2.setRecommender(dto.getRecommender());
			products2.setOperatetime(dto.getOperatetime());
			products2.setDisplay(dto.getDisplay());
			products2.setSmallpicaddress(dto.getSmallpicaddress());
			products2.setSmallpicname(dto.getMidpicaddress());
			products2.setSource(dto.getBigpicaddress());
			products2.setSpecification(dto.getSpecification());
			products2.setPrice(dto.getPrice());
			products2.setContent(dto.getContent());
			products2.setOrder(dto.getOrder());
			productsDAO.insertwithid(products2);
			return true;
		}
		return false;

	}

	/*
	 * 鍒犻櫎浜у搧
	 */
	public boolean deleteProducts(Integer contentid) throws SQLException {
		try {

			productsDAO.deleteByPrimaryKey(contentid);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * 瀵绘壘鍒嗙被
	 */
	public List findTypeType(Integer menuid) throws SQLException {
		List list = new ArrayList();
		@SuppressWarnings("unchecked")
		// List<Type> typeList = typeDAO.listAll(menuid);
	
		List<Type> typeList = typeDAO.listAll(menuid);
		
		return typeList;
	}

	
	
	
	public Integer fingMenuidByContentid(Integer contentid) throws SQLException{
		Content produ = productsDAO.selectByPrimaryKey(contentid);
		Type type  = typeDAO.selectByPrimaryKey(produ.getTypeid());
		return (type.getMenuid());
	}
	
	
	
	
	public ContentDAO getProductsDAO() {
		return productsDAO;
	}

	public void setProductsDAO(ContentDAO productsDAO) {
		this.productsDAO = productsDAO;
	}

	public TypeDAO getTypeDAO() {
		return typeDAO;
	}

	public void setTypeDAO(TypeDAO typeDAO) {
		this.typeDAO = typeDAO;
	}

	
	public MenuDAO getMenuDAO() {
		return menuDAO;
	}


	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

}
