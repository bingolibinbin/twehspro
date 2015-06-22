package com.twehs.controller.first;
//��ҳ������
/*
 *�������У� С�Ƽ����ű���
 * �����Ʒ
 * ����ͨ��
 * �������
 * ���ţ���ҵ���ţ���˾��̬��
 * 
 */
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.twehs.pojo.Comment;
import com.twehs.pojo.Company;
import com.twehs.pojo.Content;
import com.twehs.pojo.Menu;
import com.twehs.pojo.Type;
import com.twehs.service.classify.CommentService;
import com.twehs.service.classify.ExperiencesService;
import com.twehs.service.classify.NewsService;
import com.twehs.service.classify.ProductsService;
import com.twehs.service.classify.ProjectsService;
import com.twehs.service.classify.dto.CommentDTO;
import com.twehs.service.classify.dto.ProductPro;
import com.twehs.service.company.CompanyService;
import com.twehs.service.menu.MenuService;
import com.twehs.utils.pubutil.Page;

import org.apache.velocity.tools.generic.DateTool;


public class DefaultController extends MultiActionController{

	private NewsService newsService;  //����
	private ProductsService productsService;  //��Ʒ
	private MenuService menuService;    //��Ʒ��Ҫ
	private ProjectsService projectsService;   //����
	protected CompanyService companyService;   //���ڹ�˾

	
	
	
	public ModelAndView defaultListAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		
		// List<Content> datelist = new ArrayList<Content>();
		ModelAndView mv = new ModelAndView("default.html");
		
		
		
		/*��Ʒģ��*/
		///////////////////////////////////////////////////////
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		Integer typeid;
		String title = null;

		List<Type> listType2 = new ArrayList<Type>(); // ��ȡ2������ Estin ��
		List<Content> listTypee3 = new ArrayList<Content>(); // ��ȡ���в�Ʒ
		List<ProductPro> listType3 = new ArrayList<ProductPro>(); // ��ȡ���в�Ʒ
		Integer pid = 3010;
		List<Menu> listType1 = menuService.findMenuByPid(pid); // ��ѯ����һ������
		// ע�������listType1�Ǹ�menu���͵ģ��������ݿ����
		mv.addObject("listType1", listType1); // ����һ������

		List<List> listbingo = new ArrayList<List>(); // ��ȡ2������ Estin ��
		// ����Ϊ�˻�ȡ���в�Ʒ��׼��
		for (Menu menutype : listType1) {
			List<Type> listClassify = productsService.findTypeType(menutype
					.getMenuid());
			if (listClassify.size() != 0) {
				listbingo.add(listClassify);
				for (Type llist : listClassify) {
					listType2.add(llist);
					// ������������ŵ�content;
				}
			}
		}
		mv.addObject("listTypee2", listbingo);
		// ͨ��2��������ѯ���в�Ʒ
		for (Type type2 : listType2) {
			List<Content> listTypeProducts = productsService.findPageProducts(
					page, title, type2.getTypeid()); // ��Ʒ�б���ʾ
			if (listTypeProducts.size() != 0) {
				for (Content llist : listTypeProducts) {
					listTypee3.add(llist);
					// ������������ŵ�content;
				}
			}
		}
		if (listTypee3.size() != 0) {
			for (Content prolist : listTypee3) {
				Integer menuidd = productsService.fingMenuidByContentid(prolist
						.getContentid());
				ProductPro products3 = new ProductPro();

				products3.setContentid(prolist.getContentid());
				products3.setTypeid(prolist.getTypeid());
				products3.setTypename(prolist.getTypename());
				products3.setTitle(prolist.getTitle());
				products3.setTopline(prolist.getTopline());
				products3.setRecommender(prolist.getRecommender());
				products3.setOperatetime(prolist.getOperatetime());
				products3.setDisplay(prolist.getDisplay());
				products3.setSmallpicaddress(prolist.getSmallpicaddress());
				products3.setMidpicaddress(prolist.getSmallpicname());
				products3.setBigpicaddress(prolist.getSource());
				products3.setSpecification(prolist.getSpecification());
				products3.setPrice(prolist.getPrice());
				products3.setContent(prolist.getContent());
				products3.setOrder(prolist.getOrder());
				products3.setMenuuid(menuidd);
				listType3.add(products3);
			}
		}
		// Ψһ�����ǻ�ȡ�����Ʒ�Ĳ�ͬ��
		mv.addObject("listType3", listType3); // ���в�Ʒ
///////////////////////////////////////////////////////////////////////////////////
		
		/*����ͨ��ģ��*/
		
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
		
//////////////////////////////////////////////////////////////////
		
		/*���̰���ģ��*/

		Page pageproject = new Page();
		pageproject.setStart(0);
		pageproject.setLimit(30);
		Integer typeidproject;
		String titleproject = null;

		String type = request.getParameter("typeid");
		if (type != null) {
			typeidproject = Integer.valueOf(type);
			// System.out.println(typeid);
		} else {
			typeidproject = null;
		}

		List<Content> listproject = projectsService.findPageProjects(pageproject, titleproject, typeidproject); //�����б�
		DateTool dateproject = new DateTool();
		// ������������ֶ����ص�toolbox�ķ�ʽ
		// �������
		mv.addObject("dateproject", dateproject);
		mv.addObject("listproject", listproject);
		
/////////////////////////////////////////////////////////////////////////////////////////////
		/*������ʾģ��
		 * ע������Ҫ��ʾ�������ţ���ҵ���ź͹�˾��̬
		 * 
		 */
		
		
		Page pageNews = new Page();
		pageNews.setStart(0);
		pageNews.setLimit(30);
		Integer typeidNews;
		String titlenews = null;

		/*String typeNews = request.getParameter("typeid");
		if (typeNews != null) {
			typeidNews = Integer.valueOf(typeNews);
			// System.out.println(typeid);
		} else {
			typeidNews = null;
		}*/
		
		Integer typeidhangye=2;
		Integer typeiddongtai=1;
		
		List<Type> listTypeNews = newsService.findTypeType();
		
		List<Content> listNewshangye = newsService.findPageNews(pageNews, titlenews, typeidhangye);
		List<Content> listNewdongtai = newsService.findPageNews(pageNews, titlenews, typeiddongtai);
		DateTool datee = new DateTool();
		// ������������ֶ����ص�toolbox�ķ�ʽ
		mv.addObject("listTypenews", listTypeNews);
		
		// �������
		mv.addObject("datee", datee);
		mv.addObject("listNewshangye", listNewshangye);  //��ҵ����
		mv.addObject("listNewsdongtai", listNewdongtai);  //��˾��̬
		
		return mv;

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public NewsService getNewsService() {
		return newsService;
	}
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
	public ProductsService getProductsService() {
		return productsService;
	}
	public void setProductsService(ProductsService productsService) {
		this.productsService = productsService;
	}
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public ProjectsService getProjectsService() {
		return projectsService;
	}
	public void setProjectsService(ProjectsService projectsService) {
		this.projectsService = projectsService;
	}
	public CompanyService getCompanyService() {
		return companyService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	
	
	
	
	
	
	
	
	
	
}
