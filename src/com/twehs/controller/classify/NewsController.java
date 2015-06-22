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
//import com.twehs.pojo.Company;
import com.twehs.pojo.Comment;
import com.twehs.pojo.Content;
import com.twehs.pojo.Type;
import com.twehs.service.classify.CommentService;
import com.twehs.service.classify.NewsService;
import com.twehs.service.classify.dto.CommentDTO;
import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.utils.pubutil.Page;

import org.apache.velocity.tools.generic.DateTool;

public class NewsController extends MultiActionController {

	private NewsService newsService;
	private CommentService commentService;

	
	/*
	 * ��ʾ�����б�
	 * 
	 */
	public ModelAndView newsList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		// ʵ�����������ʾ�������б���ʾ

		List<Content> datelist = new ArrayList<Content>();
		ModelAndView mv = new ModelAndView("newsList.html");

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

		List<Type> listType = newsService.findTypeType();
		List<Content> list = newsService.findPageNews(page, title, typeid);
		DateTool datee = new DateTool();
		// ������������ֶ����ص�toolbox�ķ�ʽ
		mv.addObject("listType", listType);
		// �������
		mv.addObject("datee", datee);
		mv.addObject("list", list);
		return mv;
	}

	/*
	 *  ����ʵ������������ʾ������ģ��
	 *  ������Ҫ�������۱���
	 * 
	 */	
	public ModelAndView newsShow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		ModelAndView mv1 = new ModelAndView("newsShow.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		// ����ֻ��һҳ�����Է�ҳʵ���ϲ���Ҫ��ֻ��Ϊ��������ǰ�ķ�ҳ��ѯ������
		// Integer typeid = null;
		Integer newsssid = null;

		String newssid = request.getParameter("contentid");

		if (newssid != null) {
			newsssid = Integer.valueOf(newssid);
			// System.out.println(typeid);
		} else {
			newsssid = null;
		}

		Content news = newsService.FindNewss(newsssid);
		mv1.addObject("news", news);
		
		
		List<Type> listType = newsService.findTypeType();
		mv1.addObject("listType", listType);
		// ��ʾ��������ģ�飻

		DateTool datee = new DateTool();
		// String title = "����";
		Integer contentid = 2;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // ������Ҫ�޸ģ���Ҫ���붯̬��ȡ���û����������

		List<Comment> listcomment = commentService.findByContentid(contentid);
		mv1.addObject("datee", datee);
		mv1.addObject("listcomment", listcomment);

		return mv1;
	}
	
	
	
    /*
     * ����ģ��
     * 
     */
	public ModelAndView newsComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// ��ȡ��������������
		// ��ʾ����������
		ModelAndView mv2 = new ModelAndView("newsShow.html");
		DateTool datee = new DateTool();
		List<Type> listType = newsService.findTypeType();
		mv2.addObject("listType", listType);

		Integer cintentid = 2;
		// ע�⣬�������е��������ۣ�ͳһ��contentid=5���棬�൱�����һ��news���ۣ�ÿ��ȫ����ʾ
        //���velocity������������
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
		//�������
		commentService.saveOrUpdateComment(dto);
		
		
		// ��������תҳ��
		//���ض�Ӧ������ҳ��
		String cintenttid = request.getParameter("contentid");
		if (cintenttid != null) {
			cintentid = Integer.valueOf(cintenttid);
			// System.out.println(typeid);
		} else {
			cintentid = null;
		}
		Content news = newsService.FindNewss(cintentid);
		
		//������������
		Integer contentid = 2;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // ������Ҫ�޸ģ���Ҫ���붯̬��ȡ���û����������
		List<Comment> listcomment = commentService.findByContentid(contentid);
		//ע�⣺�����ﱾ����ֻ��Ҫ���ض�Ӧҳ����Ҫ�ı���ֵ���ɣ������Ƿ�ʹ���ĸ�����û�б�Ȼ��ϵ��ӳ���Ӧҳ��ķ���Ҳ��Ϊ�˼��ظķ����ı���ֵ����ҳ��
		mv2.addObject("news", news);
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

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

}
