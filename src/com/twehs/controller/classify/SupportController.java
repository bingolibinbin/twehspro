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
import com.twehs.pojo.Type;
import com.twehs.service.classify.CommentService;
import com.twehs.service.classify.SupportService;
import com.twehs.service.classify.dto.CommentDTO;
import com.twehs.utils.pubutil.Page;
import org.apache.velocity.tools.generic.DateTool;

public class SupportController extends MultiActionController {

	private SupportService supportsService;
	private CommentService commentService;
	

	/*
	 * 显示新闻列表
	 * 
	 */
	public ModelAndView supportsListAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		// 实现新闻类别显示和新闻列表显示

		List<Content> datelist = new ArrayList<Content>();
		ModelAndView support = new ModelAndView("supportsList.html");

		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		Integer typeid;
		String title = null;

		String type = request.getParameter("typeid");
		if (type != null) {
			typeid = Integer.valueOf(type);
			// System.out.println(typeid);
		} else {
			typeid = null;
		}

		List<Type> listTypeSupport = supportsService.findTypeType(); //技术菜单显示
		
		
		List<Content> list = supportsService.findPageSupports(page, title, typeid); //技术列表
		DateTool datesupport = new DateTool();
		// 日期这里采用手动加载的toolbox的方式
		support.addObject("listTypeSupport", listTypeSupport);
		// 新闻类别
		support.addObject("datesupport", datesupport);
		support.addObject("list", list);
		return support;
	}

	/*
	 *  这里实现新闻内容显示和评论模块
	 *  这里主要新闻评论保存
	 * 
	 */	
	public ModelAndView supportsShow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		ModelAndView mv1 = new ModelAndView("support.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		// 这里只有一页，所以分页实际上不需要，只是为了重用以前的分页查询方法；
		// Integer typeid = null;
		Integer supportsssid = null;
		
		List<Type> listTypeSupport = supportsService.findTypeType(); //左边菜单栏
		mv1.addObject("listTypeSupport", listTypeSupport);

		String supportsid = request.getParameter("contentid");

		if (supportsid != null) {
			supportsssid = Integer.valueOf(supportsid);
			// System.out.println(typeid);
		} else {
			supportsssid = null;
		}

		Content support = supportsService.FindSupports(supportsssid);
		mv1.addObject("support", support);

		// 显示所有评论模块；

		DateTool datesupport = new DateTool();
		// String title = "新闻";
		Integer contentid = 95;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // 这里需要修改，需要加入动态获取的用户输入参数；
		List<Comment> listcomment = commentService.findByContentid(contentid);
		mv1.addObject("datesupport", datesupport);
		mv1.addObject("listcomment", listcomment);
		
		return mv1;
	}
	
    /*
     * 评论模块
     * 
     */
	public ModelAndView supportsComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 获取并加入评论内容
		// 显示出评论内容
		ModelAndView mv2 = new ModelAndView("support.html");
		DateTool datesupport = new DateTool();

		Integer cintentid = 95;
		// 注意，这里所有的新闻评论，统一以contentid=5保存，相当于针对一个news评论，每次全部显示
        //解决velocity中文乱码问题
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
		//保存操作
		commentService.saveOrUpdateComment(dto);
		
		
		// 从这里跳转页面
		//加载对应的新闻页面
		String cintenttid = request.getParameter("contentid");
		if (cintenttid != null) {
			cintentid = Integer.valueOf(cintenttid);
			// System.out.println(typeid);
		} else {
			cintentid = null;
		}
		Content news = supportsService.FindSupports(cintentid);
		
		//加载所有评论
		Integer contentid = 95;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // 这里需要修改，需要加入动态获取的用户输入参数；
		List<Comment> listcomment = commentService.findByContentid(contentid);
		//注意：：这里本质上只需要加载对应页面需要的变量值即可，和他是否使用哪个方法没有必然关系，映入对应页面的方法也是为了加载改方法的变量值进入页面
		mv2.addObject("news", news);
		mv2.addObject("datesupport", datesupport);
		mv2.addObject("listcomment", listcomment);
		
		return mv2;

	}
	
	
	
	
	
	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	public SupportService getSupportsService() {
		return supportsService;
	}

	public void setSupportsService(SupportService supportsService) {
		this.supportsService = supportsService;
	}

}
