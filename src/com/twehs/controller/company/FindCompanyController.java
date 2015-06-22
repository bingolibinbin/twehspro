package com.twehs.controller.company;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.twehs.pojo.Company;
import com.twehs.service.company.CompanyService;

//import com.zhuchao.wmw.dao.model.User;

public class FindCompanyController extends MultiActionController {

	protected CompanyService companyService;
	
	public ModelAndView test1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
	
		ModelAndView mv = new ModelAndView("aboutUs.html");

		
		Integer companyidd = 1;
		String companyid = request.getParameter("companyid");
		if (companyid != null) {
			companyidd = Integer.valueOf(companyid);
		} else {
			companyidd = 1;
		}
		
		Company company = companyService.FindCompany(companyidd);
		if (company == null) {
			company.setContent(null);
		}
		mv.addObject("company", company);
		return mv;

	}
	
	
	
	
	
	

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

}
