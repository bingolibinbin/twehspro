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
import com.twehs.service.classify.ExperiencesService;
import com.twehs.service.classify.NewsService;
import com.twehs.service.classify.ProjectsService;
import com.twehs.service.classify.dto.CommentDTO;
import com.twehs.utils.pubutil.Page;

import org.apache.velocity.tools.generic.DateTool;

public class ExperienceController extends MultiActionController {


	private ExperiencesService experiencesService;


	private CommentService commentService;
	

	/*
	 * 显示新闻列表
	 * 
	 */
	public ModelAndView experiencesListAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		// 实现新闻类别显示和新闻列表显示

		List<Content> datelist = new ArrayList<Content>();
		ModelAndView project = new ModelAndView("experienceList.html");
		
		

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

		List<Type> listTypeExp = experiencesService.findTypeType(); //技术菜单显示
		
		
		List<Content> list = experiencesService.findPageExperiences(page, title, typeid); //技术列表
		DateTool dateexp = new DateTool();
		// 日期这里采用手动加载的toolbox的方式
		project.addObject("listTypeExp", listTypeExp);
		// 新闻类别
		project.addObject("dateexp", dateexp);
		project.addObject("list", list);
		return project;
	}

	/*
	 *  这里实现新闻内容显示和评论模块
	 *  这里主要新闻评论保存
	 * 
	 */	
	public ModelAndView experiencesShow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		ModelAndView mv1 = new ModelAndView("experience.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		// 这里只有一页，所以分页实际上不需要，只是为了重用以前的分页查询方法；
		// Integer typeid = null;
		Integer expssid = null;
		
		List<Type> listTypeExp = experiencesService.findTypeType(); //左边菜单栏
		mv1.addObject("listTypeExp", listTypeExp);

		String expsid = request.getParameter("contentid");

		if (expsid != null) {
			expssid = Integer.valueOf(expsid);
			// System.out.println(typeid);
		} else {
			expssid = null;
		}

		Content experience = experiencesService.FindExperience(expssid);
		mv1.addObject("experience", experience);

		// 显示所有评论模块；

		DateTool dateexp = new DateTool();
		// String title = "新闻";
		Integer contentid = 107;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // 这里需要修改，需要加入动态获取的用户输入参数；
		List<Comment> listcomment = commentService.findByContentid(contentid);
		mv1.addObject("dateexp", dateexp);
		mv1.addObject("listcomment", listcomment);
		
		return mv1;
	}
	
    /*
     * 评论模块
     * 
     */
	public ModelAndView experiencesComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 获取并加入评论内容
		// 显示出评论内容
		ModelAndView mv2 = new ModelAndView("experience.html");
		DateTool dateexp = new DateTool();
		List<Type> listTypeExp = experiencesService.findTypeType(); //左边菜单栏
		mv2.addObject("listTypeExp", listTypeExp);

		Integer cintentid = 107;
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
		Content experience = experiencesService.FindExperience(cintentid);
		
		//加载所有评论
		Integer contentid = 107;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // 这里需要修改，需要加入动态获取的用户输入参数；
		List<Comment> listcomment = commentService.findByContentid(contentid);
		//注意：：这里本质上只需要加载对应页面需要的变量值即可，和他是否使用哪个方法没有必然关系，映入对应页面的方法也是为了加载改方法的变量值进入页面
		mv2.addObject("experience", experience);
		mv2.addObject("dateexp", dateexp);
		mv2.addObject("listcomment", listcomment);
		
		return mv2;

	}
	
	
	
	
	
	public ExperiencesService getExperiencesService() {
		return experiencesService;
	}

	public void setExperiencesService(ExperiencesService experiencesService) {
		this.experiencesService = experiencesService;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

}
