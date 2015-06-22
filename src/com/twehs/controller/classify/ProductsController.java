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
	 * 这里产品不同点击显示只能分开写不同的方法；否则无法实现； 1）显示全部产品 和产品列表 2）获得一个第一级目录名（智能家居） 生成对应的产品列表
	 * 3） 获得一个第二级目录（Elizin） 生成对应的产品列表
	 */

	/*
	 * 
	 * 显示全部产品
	 */
	public ModelAndView productsListAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现产品类别显示和产品列表显示
		// 显示分类列表 3级分类
		// List<Content> datelist = new ArrayList<Content>();
		ModelAndView mv = new ModelAndView("productsList.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		Integer typeid;
		String title = null;

		List<Type> listType2 = new ArrayList<Type>(); // 获取2级分类 Estin 等
		List<Content> listTypee3 = new ArrayList<Content>(); // 获取所有产品
		List<ProductPro> listType3 = new ArrayList<ProductPro>(); // 获取所有产品
		Integer pid = 3010;
		List<Menu> listType1 = menuService.findMenuByPid(pid); // 查询所有一级分类
		// 注意这里的listType1是个menu类型的，由于数据库决定
		mv.addObject("listType1", listType1); // 所有一级分类

		List<List> listbingo = new ArrayList<List>(); // 获取2级分类 Estin 等
		// 这是为了获取所有产品做准备
		for (Menu menutype : listType1) {
			List<Type> listClassify = productsService.findTypeType(menutype
					.getMenuid());
			if (listClassify.size() != 0) {
				listbingo.add(listClassify);
				for (Type llist : listClassify) {
					listType2.add(llist);
					// 添加所有是新闻的content;
				}
			}
		}
		mv.addObject("listTypee2", listbingo);
		// 通过2级分来查询所有产品
		for (Type type2 : listType2) {
			List<Content> listTypeProducts = productsService.findPageProducts(
					page, title, type2.getTypeid()); // 产品列表显示
			if (listTypeProducts.size() != 0) {
				for (Content llist : listTypeProducts) {
					listTypee3.add(llist);
					// 添加所有是新闻的content;
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
		// 唯一区别是获取具体产品的不同；
		mv.addObject("listType3", listType3); // 所有产品
		return mv;

	}

	/*
	 * 
	 * 获取一级目录名，显示对应产品 eg::智能家居
	 */
	public ModelAndView productsListMenu(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		String typeyiji = request.getParameter("prod"); // 获取一级目录 id
		// 获取页面一定要注意是在新建页面的前面获取，
		// 如果是换了对应的方法，再在所新建页面之后是获取不到任何原来页面传过来的连接值的

		ModelAndView mv2 = new ModelAndView("productsList.html");

		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		Integer typeid;
		String title = null;
		Integer typemenuid;

		List<Type> listType2 = new ArrayList<Type>(); // 获取2级分类 Estin 等
		List<Content> listTypee3 = new ArrayList<Content>(); // 获取所有产品
		List<ProductPro> listType3 = new ArrayList<ProductPro>(); // 获取所有产品
		Integer pid = 3010;
		List<Menu> listType1 = menuService.findMenuByPid(pid); // 查询所有一级分类
		// 注意这里的listType1是个menu类型的，由于数据库决定
		mv2.addObject("listType1", listType1); // 所有一级分
		List<List> listbingo = new ArrayList<List>(); // 获取2级分类 Estin 等
		// 这是为了获取所有产品做准备
		for (Menu menutype : listType1) {
			List<Type> listClassify = productsService.findTypeType(menutype
					.getMenuid());
			if (listClassify.size() != 0) {
				listbingo.add(listClassify);
				for (Type llist : listClassify) {
					listType2.add(llist);
					// 添加所有是新闻的content;
				}
			}
		}
		mv2.addObject("listTypee2", listbingo); // 第一是列表显示，第二是为后面的产品查询
		// typeyiji 值在新建页面之前获取的,注意，必须在之前获取
		if (typeyiji != null) {
			typemenuid = Integer.valueOf(typeyiji);
			// System.out.println(typeid);
		} else {
			typemenuid = null;
		}
		List<Type> listClassify2 = productsService.findTypeType(typemenuid); // 获得
																				// Estince
		// 通过2级分类显示对应的具体产品
		for (Type type2 : listClassify2) {
			List<Content> listTypeProducts = productsService.findPageProducts(
					page, title, type2.getTypeid()); // 具体产品列表显示

			if (listTypeProducts.size() != 0) {
				for (Content llist : listTypeProducts) {
					listTypee3.add(llist);
					// 添加所有是新闻的content;
				}
			}
		}

		// 对产品每一个对象做一次转换，接入menuid进入产品中；为了产品控件的使用；
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
		// 唯一区别是获取具体产品的不同；

		mv2.addObject("listType3", listType3); // 所有产品
		return mv2;
	}

	/*
	 * 
	 * 获取二级目录名，显示对应产品
	 */
	public ModelAndView productsListType(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		String typeyiji = request.getParameter("typeid"); // 获取一级目录 id
		// 获取页面一定要注意是在新建页面的前面获取，
		// 如果是换了对应的方法，再在所新建页面之后是获取不到任何原来页面传过来的连接值的

		// System.out.println("typeyiji"+typeyiji);

		ModelAndView mv3 = new ModelAndView("productsList.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		Integer typeid;
		String title = null;
		Integer typemenuid;
		List<Type> listType2 = new ArrayList<Type>(); // 获取2级分类 Estin 等

		Integer pid = 3010;
		List<Menu> listType1 = menuService.findMenuByPid(pid); // 查询所有一级分类
		// 注意这里的listType1是个menu类型的，由于数据库决定
		mv3.addObject("listType1", listType1); // 所有一级分类

		List<List> listbingo = new ArrayList<List>(); // 获取2级分类 Estin 等
		// 这是为了获取所有产品做准备
		for (Menu menutype : listType1) {
			List<Type> listClassify = productsService.findTypeType(menutype
					.getMenuid());
			if (listClassify.size() != 0) {
				listbingo.add(listClassify);
				for (Type llist : listClassify) {
					listType2.add(llist);
					// 添加所有是新闻的content;
				}
			}
		}
		mv3.addObject("listTypee2", listbingo); // 第一是列表显示，第二是为后面的产品查询
		// typeyiji 值在新建页面之前获取的,注意，必须在之前获取
		// typemenuid 是所获取的2级分类的id
		if (typeyiji != null) {
			typemenuid = Integer.valueOf(typeyiji);
			// System.out.println(typeid);
		} else {
			typemenuid = null;
		}
		// 通过2级分类显示对应的具体产品
		List<ProductPro> listType3 = new ArrayList<ProductPro>(); // 获取所有产品
		List<Content> listTypee3 = productsService.findPageProducts(page,
				title, typemenuid); // 具体产品列表显示

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

		// 唯一区别是获取具体产品的不同；
		mv3.addObject("listType3", listType3); // 所有产品
		return mv3;

	}

	
	
	
	
	/*
	 * 这里实现产品内容显示和评论模块 这里主要产品评论保存
	 */
	public ModelAndView productsShow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		ModelAndView mv1 = new ModelAndView("productShow.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		// 这里只有一页，所以分页实际上不需要，只是为了重用以前的分页查询方法；
		// Integer typeid = null;
		Integer productid = null;
//////////////////////////////////////////////////////////////////////////////////////////
		
		Integer typeid;
		String title = null;

		List<Type> listType2 = new ArrayList<Type>(); // 获取2级分类 Estin 等
		List<Content> listTypee3 = new ArrayList<Content>(); // 获取所有产品
		List<ProductPro> listType3 = new ArrayList<ProductPro>(); // 获取所有产品
		Integer pid = 3010;
		List<Menu> listType1 = menuService.findMenuByPid(pid); // 查询所有一级分类
		// 注意这里的listType1是个menu类型的，由于数据库决定
		mv1.addObject("listType1", listType1); // 所有一级分类

		List<List> listbingo = new ArrayList<List>(); // 获取2级分类 Estin 等
		// 这是为了获取所有产品做准备
		for (Menu menutype : listType1) {
			List<Type> listClassify = productsService.findTypeType(menutype
					.getMenuid());
			if (listClassify.size() != 0) {
				listbingo.add(listClassify);
				for (Type llist : listClassify) {
					listType2.add(llist);
					// 添加所有是新闻的content;
				}
			}
		}
		mv1.addObject("listTypee2", listbingo);
		// 通过2级分来查询所有产品
		for (Type type2 : listType2) {
			List<Content> listTypeProducts = productsService.findPageProducts(
					page, title, type2.getTypeid()); // 产品列表显示
			if (listTypeProducts.size() != 0) {
				for (Content llist : listTypeProducts) {
					listTypee3.add(llist);
					// 添加所有是新闻的content;
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
		// 唯一区别是获取具体产品的不同；
		mv1.addObject("listType3", listType3); // 所有产品
		
		
		///////////////////////////////////////////////////////////////////////////////
		String productssid = request.getParameter("contentid"); // 获得对应产品id

		if (productssid != null) {
			productid = Integer.valueOf(productssid); // id 由String类型转换
			// System.out.println(typeid);
		} else {
			productid = null;
		}

		Content products = productsService.FindProductss(productid); // 通过id
																		// 查找对应产品
		
	/*	
		System.out.println("contro 小图地址"+products.getSmallpicaddress());
		System.out.println("产品名称"+products.getTitle());
		
		*/
		
		mv1.addObject("products", products); // 加载产品

		// 显示所有评论模块；

		DateTool datee = new DateTool();
		// String title = "产品";

		Integer contentid = 62; // 这里显示所有产品评论；

		// 这里需要修改，需要加入动态获取的用户输入参数；

		List<Comment> listcomment = commentService.findByContentid(contentid);
		mv1.addObject("datee", datee);
		mv1.addObject("listcomment", listcomment);

		return mv1;
	}

	/*
	 * 评论模块
	 */
	public ModelAndView productsComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 获取并加入评论内容
		// 显示出评论内容
		ModelAndView mv2 = new ModelAndView("productShow.html");
		DateTool datee = new DateTool();

		Integer cintentid = 62;
		// 注意，这里所有的产品评论，统一以contentid=5保存，相当于针对一个news评论，每次全部显示
		// 解决velocity中文乱码问题
		String commentor = new String(request.getParameter("commentor")
				.getBytes("ISO-8859-1"), "utf-8");
		String commentscontent = new String(request.getParameter(
				"commentscontent").getBytes("ISO-8859-1"), "utf-8");
		String title = null;
		// 这里title起不到太大作用
		Date operatetime = null;
		// 时间设置是自动累加的，所以自控
		boolean check = false;
		boolean display = false;
		Integer commentid = null;

		// 这里commentid 是底层自增的，不需要设值，底层保存没有用到
		CommentDTO dto = new CommentDTO(commentid, cintentid, commentor,
				commentscontent, display, check, operatetime, title);
		// 保存操作
		commentService.saveOrUpdateComment(dto);

		// 从这里跳转页面
		// 加载对应的产品页面
		String cintenttid = request.getParameter("contentid");
		if (cintenttid != null) {
			cintentid = Integer.valueOf(cintenttid);
			// System.out.println(typeid);
		} else {
			cintentid = null;
		}
		Content products = productsService.FindProductss(cintentid);

		// 加载所有评论
		Integer contentid = 62;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // 这里需要修改，需要加入动态获取的用户输入参数；
		List<Comment> listcomment = commentService.findByContentid(contentid);
		// 注意：：这里本质上只需要加载对应页面需要的变量值即可，和他是否使用哪个方法没有必然关系，映入对应页面的方法也是为了加载改方法的变量值进入页面
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
