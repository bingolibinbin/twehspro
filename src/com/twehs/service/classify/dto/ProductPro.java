package com.twehs.service.classify.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ProductPro {

	
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
	private Integer menuuid;
	
	
	
	
	public ProductPro() {
		super();
	}
	
	

	

	public ProductPro(Integer contentid, Integer typeid, String typename,
			boolean topline, String title, boolean recommender,
			Date operatetime, boolean display, String smallpicaddress,
			String midpicaddress, String bigpicaddress, String specification,
			BigDecimal price, String content, Integer order, Integer menuuid) {
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
		this.menuuid = menuuid;
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
	public void setContentid(Integer contentid) {
		this.contentid = contentid;
	}
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public boolean isTopline() {
		return topline;
	}
	public void setTopline(boolean topline) {
		this.topline = topline;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isRecommender() {
		return recommender;
	}
	public void setRecommender(boolean recommender) {
		this.recommender = recommender;
	}
	public Date getOperatetime() {
		return operatetime;
	}
	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public String getSmallpicaddress() {
		return smallpicaddress;
	}
	public void setSmallpicaddress(String smallpicaddress) {
		this.smallpicaddress = smallpicaddress;
	}

	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getMenuuid() {
		return menuuid;
	}
	public void setMenuuid(Integer menuuid) {
		this.menuuid = menuuid;
	}
	
	
}
