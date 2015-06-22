package com.twehs.service.company.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.twehs.pojo.Company;
import com.twehs.pojo.Menu;
import com.twehs.service.company.CompanyService;
import com.twehs.pojo.example.CompanyExample;
import com.twehs.pojo.example.CompanyExample.Criteria;
import com.twehs.dao.company.CompanyDAO;
//import com.twehs.dao.power.impl.MenuDAOImpl;
import com.twehs.service.company.dto.CompanyDTO;
//import com.twehs.service.menu.dto.MenuDTO;
//import com.twehs.service.power.dto.UserDTO;
import com.twehs.utils.pubutil.Page;

public class CompanyServiceImpl implements CompanyService {

	private CompanyDAO companyDAO;

	/*
	 * 
	 * 鍒嗛〉鏌ヨ
	 */
	public void findPageCompanys(Page page) throws SQLException {
		// TODO Auto-generated method stub
		List<Company> listCompany = companyDAO.selectByPaper(page.getStart(),
				page.getLimit());
		@SuppressWarnings("rawtypes")
		List dtoList = CompanyDTO.createDtos(listCompany);
		int total = companyDAO.countAll(); // 杩欓噷countAll鏄嚜宸辨敼鍐欑殑锛屾湰韬笉鎻愪緵杩欎釜鍑芥暟
		page.setTotal(total);
		page.setRoot(dtoList);

	}

	
	public Company FindCompany(Integer companyid) throws SQLException{
		
		Company company = companyDAO.selectByPrimaryKey(companyid);
		
		return company;
		
	}
	
	
	/*
	 * 
	 * 淇濆瓨鍜屾洿鏂�
	 */
	public boolean saveOrUpdateCompany(CompanyDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		Company company1 = new Company();
		if (dto.getCompanyid() != null) {
			company1 = (Company) companyDAO.selectByPrimaryKey(dto
					.getCompanyid());
			if (company1 != null) {
				// 鏇存柊鎿嶄綔锛�
				company1.setCompanyname(dto.getCompanyname());
				company1.setProfession(dto.getProfession());
				company1.setArea(dto.getArea());
				company1.setWebsite(dto.getWebsite());
				company1.setConnector(dto.getConnector());
				company1.setPhoneno(dto.getPhoneno());
				company1.setMobileno(dto.getMobileno());
				company1.setFax(dto.getFax());
				company1.setPostcode(dto.getPostcode());
				company1.setEmail(dto.getEmail());
				company1.setContent(dto.getContent());
				companyDAO.updateByPrimaryKeySelective(company1); // 鏁版嵁搴撴洿鏂�

				return true;
			}
		} else {

			// 鎻掑叆鎿嶄綔
			Company company2 = new Company();
			// company2.setCompanyid(dto.getCompanyid());
			company2.setCompanyname(dto.getCompanyname());
			System.out.println("service:companyname" + dto.getCompanyname());
			company2.setProfession(dto.getProfession());
			company2.setArea(dto.getArea());
			company2.setWebsite(dto.getWebsite());
			company2.setConnector(dto.getConnector());
			company2.setPhoneno(dto.getPhoneno());
			company2.setMobileno(dto.getMobileno());
			company2.setFax(dto.getFax());
			company2.setPostcode(dto.getPostcode());
			company2.setEmail(dto.getEmail());
			company2.setContent(dto.getContent());

			companyDAO.insertwithid(company2);
			return true;
		}

		return false;

	}

	/*
	 * 
	 * 鍒犻櫎鎿嶄綔
	 */
	public boolean deleteCompanys(Integer companyid) {
		try {
			companyDAO.deleteByPrimaryKey(companyid);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

}
