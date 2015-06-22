package com.twehs.controller.classify;

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
import com.twehs.pojo.Content;
import com.twehs.pojo.Menu;
import com.twehs.pojo.Type;
import com.twehs.service.classify.CommentService;
import com.twehs.service.classify.ExperiencesService;
import com.twehs.service.classify.NewsService;
import com.twehs.service.classify.ProductsService;
import com.twehs.service.classify.dto.CommentDTO;
import com.twehs.service.classify.dto.ProductPro;
import com.twehs.service.menu.MenuService;
import com.twehs.utils.pubutil.Page;

import org.apache.velocity.tools.generic.DateTool;

public class ProductsController extends MultiActionController {

	private ProductsService productsService;
	private CommentService commentService;
	private MenuService menuService;

	/*
	 * �����Ʒ��ͬ�����ʾֻ�ֿܷ�д��ͬ�ķ����������޷�ʵ�֣� 1����ʾȫ����Ʒ �Ͳ�Ʒ�б� 2�����һ����һ��Ŀ¼�������ܼҾӣ� ���ɶ�Ӧ�Ĳ�Ʒ�б�
	 * 3�� ���һ���ڶ���Ŀ¼��Elizin�� ���ɶ�Ӧ�Ĳ�Ʒ�б�
	 */

	/*
	 * 
	 * ��ʾȫ����Ʒ
	 */
	public ModelAndView productsListAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// ʵ�ֲ�Ʒ�����ʾ�Ͳ�Ʒ�б���ʾ
		// ��ʾ�����б� 3������
		// List<Content> datelist = new ArrayList<Content>();
		ModelAndView mv = new ModelAndView("productsList.html");
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
		return mv;

	}

	/*
	 * 
	 * ��ȡһ��Ŀ¼������ʾ��Ӧ��Ʒ eg::���ܼҾ�
	 */
	public ModelAndView productsListMenu(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		String typeyiji = request.getParameter("prod"); // ��ȡһ��Ŀ¼ id
		// ��ȡҳ��һ��Ҫע�������½�ҳ���ǰ���ȡ��
		// ����ǻ��˶�Ӧ�ķ������������½�ҳ��֮���ǻ�ȡ�����κ�ԭ��ҳ�洫����������ֵ��

		ModelAndView mv2 = new ModelAndView("productsList.html");

		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		Integer typeid;
		String title = null;
		Integer typemenuid;

		List<Type> listType2 = new ArrayList<Type>(); // ��ȡ2������ Estin ��
		List<Content> listTypee3 = new ArrayList<Content>(); // ��ȡ���в�Ʒ
		List<ProductPro> listType3 = new ArrayList<ProductPro>(); // ��ȡ���в�Ʒ
		Integer pid = 3010;
		List<Menu> listType1 = menuService.findMenuByPid(pid); // ��ѯ����һ������
		// ע�������listType1�Ǹ�menu���͵ģ��������ݿ����
		mv2.addObject("listType1", listType1); // ����һ����
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
		mv2.addObject("listTypee2", listbingo); // ��һ���б���ʾ���ڶ���Ϊ����Ĳ�Ʒ��ѯ
		// typeyiji ֵ���½�ҳ��֮ǰ��ȡ��,ע�⣬������֮ǰ��ȡ
		if (typeyiji != null) {
			typemenuid = Integer.valueOf(typeyiji);
			// System.out.println(typeid);
		} else {
			typemenuid = null;
		}
		List<Type> listClassify2 = productsService.findTypeType(typemenuid); // ���
																				// Estince
		// ͨ��2��������ʾ��Ӧ�ľ����Ʒ
		for (Type type2 : listClassify2) {
			List<Content> listTypeProducts = productsService.findPageProducts(
					page, title, type2.getTypeid()); // �����Ʒ�б���ʾ

			if (listTypeProducts.size() != 0) {
				for (Content llist : listTypeProducts) {
					listTypee3.add(llist);
					// ������������ŵ�content;
				}
			}
		}

		// �Բ�Ʒÿһ��������һ��ת��������menuid�����Ʒ�У�Ϊ�˲�Ʒ�ؼ���ʹ�ã�
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

		mv2.addObject("listType3", listType3); // ���в�Ʒ
		return mv2;
	}

	/*
	 * 
	 * ��ȡ����Ŀ¼������ʾ��Ӧ��Ʒ
	 */
	public ModelAndView productsListType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		String typeyiji = request.getParameter("typeid"); // ��ȡһ��Ŀ¼ id
		// ��ȡҳ��һ��Ҫע�������½�ҳ���ǰ���ȡ��
		// ����ǻ��˶�Ӧ�ķ������������½�ҳ��֮���ǻ�ȡ�����κ�ԭ��ҳ�洫����������ֵ��

		// System.out.println("typeyiji"+typeyiji);

		ModelAndView mv3 = new ModelAndView("productsList.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		Integer typeid;
		String title = null;
		Integer typemenuid;
		List<Type> listType2 = new ArrayList<Type>(); // ��ȡ2������ Estin ��

		Integer pid = 3010;
		List<Menu> listType1 = menuService.findMenuByPid(pid); // ��ѯ����һ������
		// ע�������listType1�Ǹ�menu���͵ģ��������ݿ����
		mv3.addObject("listType1", listType1); // ����һ������

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
		mv3.addObject("listTypee2", listbingo); // ��һ���б���ʾ���ڶ���Ϊ����Ĳ�Ʒ��ѯ
		// typeyiji ֵ���½�ҳ��֮ǰ��ȡ��,ע�⣬������֮ǰ��ȡ
		// typemenuid ������ȡ��2�������id
		if (typeyiji != null) {
			typemenuid = Integer.valueOf(typeyiji);
			// System.out.println(typeid);
		} else {
			typemenuid = null;
		}
		// ͨ��2��������ʾ��Ӧ�ľ����Ʒ
		List<ProductPro> listType3 = new ArrayList<ProductPro>(); // ��ȡ���в�Ʒ
		List<Content> listTypee3 = productsService.findPageProducts(page,
				title, typemenuid); // �����Ʒ�б���ʾ

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
		mv3.addObject("listType3", listType3); // ���в�Ʒ
		return mv3;

	}

	
	
	
	
	/*
	 * ����ʵ�ֲ�Ʒ������ʾ������ģ�� ������Ҫ��Ʒ���۱���
	 */
	public ModelAndView productsShow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		ModelAndView mv1 = new ModelAndView("productShow.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		// ����ֻ��һҳ�����Է�ҳʵ���ϲ���Ҫ��ֻ��Ϊ��������ǰ�ķ�ҳ��ѯ������
		// Integer typeid = null;
		Integer productid = null;
//////////////////////////////////////////////////////////////////////////////////////////
		
		Integer typeid;
		String title = null;

		List<Type> listType2 = new ArrayList<Type>(); // ��ȡ2������ Estin ��
		List<Content> listTypee3 = new ArrayList<Content>(); // ��ȡ���в�Ʒ
		List<ProductPro> listType3 = new ArrayList<ProductPro>(); // ��ȡ���в�Ʒ
		Integer pid = 3010;
		List<Menu> listType1 = menuService.findMenuByPid(pid); // ��ѯ����һ������
		// ע�������listType1�Ǹ�menu���͵ģ��������ݿ����
		mv1.addObject("listType1", listType1); // ����һ������

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
		mv1.addObject("listTypee2", listbingo);
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
		mv1.addObject("listType3", listType3); // ���в�Ʒ
		
		
		///////////////////////////////////////////////////////////////////////////////
		String productssid = request.getParameter("contentid"); // ��ö�Ӧ��Ʒid

		if (productssid != null) {
			productid = Integer.valueOf(productssid); // id ��String����ת��
			// System.out.println(typeid);
		} else {
			productid = null;
		}

		Content products = productsService.FindProductss(productid); // ͨ��id
																		// ���Ҷ�Ӧ��Ʒ
		
	/*	
		System.out.println("contro Сͼ��ַ"+products.getSmallpicaddress());
		System.out.println("��Ʒ����"+products.getTitle());
		
		*/
		
		mv1.addObject("products", products); // ���ز�Ʒ

		// ��ʾ��������ģ�飻

		DateTool datee = new DateTool();
		// String title = "��Ʒ";

		Integer contentid = 62; // ������ʾ���в�Ʒ���ۣ�

		// ������Ҫ�޸ģ���Ҫ���붯̬��ȡ���û����������

		List<Comment> listcomment = commentService.findByContentid(contentid);
		mv1.addObject("datee", datee);
		mv1.addObject("listcomment", listcomment);

		return mv1;
	}

	/*
	 * ����ģ��
	 */
	public ModelAndView productsComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// ��ȡ��������������
		// ��ʾ����������
		ModelAndView mv2 = new ModelAndView("productShow.html");
		DateTool datee = new DateTool();

		Integer cintentid = 62;
		// ע�⣬�������еĲ�Ʒ���ۣ�ͳһ��contentid=5���棬�൱�����һ��news���ۣ�ÿ��ȫ����ʾ
		// ���velocity������������
		String commentor = new String(request.getParameter("commentor")
				.getBytes("ISO-8859-1"), "utf-8");
		String commentscontent = new String(request.getParameter(
				"commentscontent").getBytes("ISO-8859-1"), "utf-8");
		String title = null;
		// ����title�𲻵�̫������
		Date operatetime = null;
		// ʱ���������Զ��ۼӵģ������Կ�
		boolean check = false;
		boolean display = false;
		Integer commentid = null;

		// ����commentid �ǵײ������ģ�����Ҫ��ֵ���ײ㱣��û���õ�
		CommentDTO dto = new CommentDTO(commentid, cintentid, commentor,
				commentscontent, display, check, operatetime, title);
		// �������
		commentService.saveOrUpdateComment(dto);

		// ��������תҳ��
		// ���ض�Ӧ�Ĳ�Ʒҳ��
		String cintenttid = request.getParameter("contentid");
		if (cintenttid != null) {
			cintentid = Integer.valueOf(cintenttid);
			// System.out.println(typeid);
		} else {
			cintentid = null;
		}
		Content products = productsService.FindProductss(cintentid);

		// ������������
		Integer contentid = 62;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // ������Ҫ�޸ģ���Ҫ���붯̬��ȡ���û����������
		List<Comment> listcomment = commentService.findByContentid(contentid);
		// ע�⣺�����ﱾ����ֻ��Ҫ���ض�Ӧҳ����Ҫ�ı���ֵ���ɣ������Ƿ�ʹ���ĸ�����û�б�Ȼ��ϵ��ӳ���Ӧҳ��ķ���Ҳ��Ϊ�˼��ظķ����ı���ֵ����ҳ��
		mv2.addObject("products", products);
		mv2.addObject("datee", datee);
		mv2.addObject("listcomment", listcomment);

		return mv2;

	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
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
}
