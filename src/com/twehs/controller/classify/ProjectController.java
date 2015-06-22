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
import com.twehs.service.classify.SupportService;
import com.twehs.service.classify.dto.CommentDTO;
import com.twehs.utils.pubutil.Page;

import org.apache.velocity.tools.generic.DateTool;

public class ProjectController extends MultiActionController {



	private ProjectsService projectsService;
	private CommentService commentService;
	

	/*
	 * ��ʾ�����б�
	 * 
	 */
	public ModelAndView projectsListAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		// ʵ�����������ʾ�������б���ʾ

		List<Content> datelist = new ArrayList<Content>();
		ModelAndView project = new ModelAndView("projectsList.html");
		
		

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

		List<Type> listTypeProject = projectsService.findTypeType(); //�����˵���ʾ
		
		
		List<Content> list = projectsService.findPageProjects(page, title, typeid); //�����б�
		DateTool dateproject = new DateTool();
		// ������������ֶ����ص�toolbox�ķ�ʽ
		project.addObject("listTypeProject", listTypeProject);
		// �������
		project.addObject("dateproject", dateproject);
		project.addObject("list", list);
		return project;
	}

	/*
	 *  ����ʵ������������ʾ������ģ��
	 *  ������Ҫ�������۱���
	 * 
	 */	
	public ModelAndView projectsShow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		ModelAndView mv1 = new ModelAndView("project.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		// ����ֻ��һҳ�����Է�ҳʵ���ϲ���Ҫ��ֻ��Ϊ��������ǰ�ķ�ҳ��ѯ������
		// Integer typeid = null;
		Integer projectsssid = null;
		
		List<Type> listTypeProject = projectsService.findTypeType(); //��߲˵���
		mv1.addObject("listTypeProject", listTypeProject);

		String projectsid = request.getParameter("contentid");

		if (projectsid != null) {
			projectsssid = Integer.valueOf(projectsid);
			// System.out.println(typeid);
		} else {
			projectsssid = null;
		}

		Content project = projectsService.FindProjects(projectsssid);
		mv1.addObject("project", project);

		// ��ʾ��������ģ�飻

		DateTool dateproject = new DateTool();
		// String title = "����";
		Integer contentid = 100;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // ������Ҫ�޸ģ���Ҫ���붯̬��ȡ���û����������
		List<Comment> listcomment = commentService.findByContentid(contentid);
		mv1.addObject("dateproject", dateproject);
		mv1.addObject("listcomment", listcomment);
		
		return mv1;
	}
	
    /*
     * ����ģ��
     * 
     */
	public ModelAndView projectsComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// ��ȡ��������������
		// ��ʾ����������
		ModelAndView mv2 = new ModelAndView("project.html");
		DateTool dateproject = new DateTool();
		List<Type> listTypeProject = projectsService.findTypeType(); //��߲˵���
		mv2.addObject("listTypeProject", listTypeProject);

		Integer cintentid = 100;
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
		Content project = projectsService.FindProjects(cintentid);
		
		//������������
		Integer contentid = 100;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // ������Ҫ�޸ģ���Ҫ���붯̬��ȡ���û����������
		List<Comment> listcomment = commentService.findByContentid(contentid);
		//ע�⣺�����ﱾ����ֻ��Ҫ���ض�Ӧҳ����Ҫ�ı���ֵ���ɣ������Ƿ�ʹ���ĸ�����û�б�Ȼ��ϵ��ӳ���Ӧҳ��ķ���Ҳ��Ϊ�˼��ظķ����ı���ֵ����ҳ��
		mv2.addObject("project", project);
		mv2.addObject("dateproject", dateproject);
		mv2.addObject("listcomment", listcomment);
		
		return mv2;

	}
	
	
	
	
	
	public ProjectsService getProjectsService() {
		return projectsService;
	}

	public void setProjectsService(ProjectsService projectsService) {
		this.projectsService = projectsService;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
}
