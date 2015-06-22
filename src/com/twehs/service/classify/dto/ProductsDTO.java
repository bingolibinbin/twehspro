package com.twehs.service.classify.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.twehs.pojo.Content;

public class ProductsDTO {


	private Integer contentid;
	private Integer typeid;
	private String typename;
	private boolean topline;
	private String title;
	private boolean recommender;
	private Date operatetime;
	private boolean display;
	private String smallpicaddress;

	private String midpicaddress;
	private String bigpicaddress;
	private String specification;
	private BigDecimal price;
	private String content;
	private Integer order;

	public ProductsDTO() {
		super();
	}

	
	public ProductsDTO(Integer contentid, Integer typeid, String typename,
			boolean topline, String title, boolean recommender,
			Date operatetime, boolean display, String smallpicaddress,
			String midpicaddress, String bigpicaddress, String specification,
			BigDecimal price, String content, Integer order) {
		super();
		this.contentid = contentid;
		this.typeid = typeid;
		this.typename = typename;
		this.topline = topline;
		this.title = title;
		this.recommender = recommender;
		this.operatetime = operatetime;
		this.display = display;
		this.smallpicaddress = smallpicaddress;
		this.midpicaddress = midpicaddress;
		this.bigpicaddress = bigpicaddress;
		this.specification = specification;
		this.price = price;
		this.content = content;
		this.order = order;
	}



	public static ProductsDTO createDto(Content pojo) {
		ProductsDTO dto = null;
		if (pojo != null) {
			dto = new ProductsDTO(pojo.getContentid(), pojo.getTypeid(),
					pojo.getTypename(), pojo.getTopline(), pojo.getTitle(),
					pojo.getRecommender(), pojo.getOperatetime(),
					pojo.getDisplay(), pojo.getSmallpicaddress(),
					pojo.getSmallpicname(),pojo.getSource(), pojo.getSpecification(),
					pojo.getPrice(), pojo.getContent(), pojo.getOrder());

		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	public static List createDtos(Collection pojos) {
		List<ProductsDTO> list = new ArrayList<ProductsDTO>();
		if (pojos != null) {
			Iterator iterator = pojos.iterator();
			while (iterator.hasNext()) {
				Content products = (Content) iterator.next();
				ProductsDTO dto = createDto(products);
				list.add(dto);
			}
		}
		return list;
	}
	
	
	
	
	public String getMidpicaddress() {
		return midpicaddress;
	}

	public void setMidpicaddress(String midpicaddress) {
		this.midpicaddress = midpicaddress;
	}

	public String getBigpicaddress() {
		return bigpicaddress;
	}

	public void setBigpicaddress(String bigpicaddress) {
		this.bigpicaddress = bigpicaddress;
	}

	public Integer getContentid() {
		return contentid;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public String getTypename() {
		return typename;
	}

	public boolean getTopline() {
		return topline;
	}

	public boolean getRecommender() {
		return recommender;
	}

	public Date getOperatetime() {
		return operatetime;
	}

	public boolean getDisplay() {
		return display;
	}

	public String getSmallpicaddress() {
		return smallpicaddress;
	}


	public String getSpecification() {
		return specification;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getContent() {
		return content;
	}

	public Integer getOrder() {
		return order;
	}

	public void setContentid(Integer contentid) {
		this.contentid = contentid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public void setTopline(boolean topline) {
		this.topline = topline;
	}

	public void setRecommender(boolean recommender) {
		this.recommender = recommender;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public void setSmallpicaddress(String smallpicaddress) {
		this.smallpicaddress = smallpicaddress;
	}



	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
