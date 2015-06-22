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
	 * ��ʾ�����б�
	 * 
	 */
	public ModelAndView experiencesListAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		// ʵ�����������ʾ�������б���ʾ

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

		List<Type> listTypeExp = experiencesService.findTypeType(); //�����˵���ʾ
		
		
		List<Content> list = experiencesService.findPageExperiences(page, title, typeid); //�����б�
		DateTool dateexp = new DateTool();
		// ������������ֶ����ص�toolbox�ķ�ʽ
		project.addObject("listTypeExp", listTypeExp);
		// �������
		project.addObject("dateexp", dateexp);
		project.addObject("list", list);
		return project;
	}

	/*
	 *  ����ʵ������������ʾ������ģ��
	 *  ������Ҫ�������۱���
	 * 
	 */	
	public ModelAndView experiencesShow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		ModelAndView mv1 = new ModelAndView("experience.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		// ����ֻ��һҳ�����Է�ҳʵ���ϲ���Ҫ��ֻ��Ϊ��������ǰ�ķ�ҳ��ѯ������
		// Integer typeid = null;
		Integer expssid = null;
		
		List<Type> listTypeExp = experiencesService.findTypeType(); //��߲˵���
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

		// ��ʾ��������ģ�飻

		DateTool dateexp = new DateTool();
		// String title = "����";
		Integer contentid = 107;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // ������Ҫ�޸ģ���Ҫ���붯̬��ȡ���û����������
		List<Comment> listcomment = commentService.findByContentid(contentid);
		mv1.addObject("dateexp", dateexp);
		mv1.addObject("listcomment", listcomment);
		
		return mv1;
	}
	
    /*
     * ����ģ��
     * 
     */
	public ModelAndView experiencesComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// ��ȡ��������������
		// ��ʾ����������
		ModelAndView mv2 = new ModelAndView("experience.html");
		DateTool dateexp = new DateTool();
		List<Type> listTypeExp = experiencesService.findTypeType(); //��߲˵���
		mv2.addObject("listTypeExp", listTypeExp);

		Integer cintentid = 107;
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
		Content experience = experiencesService.FindExperience(cintentid);
		
		//������������
		Integer contentid = 107;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // ������Ҫ�޸ģ���Ҫ���붯̬��ȡ���û����������
		List<Comment> listcomment = commentService.findByContentid(contentid);
		//ע�⣺�����ﱾ����ֻ��Ҫ���ض�Ӧҳ����Ҫ�ı���ֵ���ɣ������Ƿ�ʹ���ĸ�����û�б�Ȼ��ϵ��ӳ���Ӧҳ��ķ���Ҳ��Ϊ�˼��ظķ����ı���ֵ����ҳ��
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
