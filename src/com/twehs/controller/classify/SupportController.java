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
	 * ��ʾ�����б�
	 * 
	 */
	public ModelAndView supportsListAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		// ʵ�����������ʾ�������б���ʾ

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

		List<Type> listTypeSupport = supportsService.findTypeType(); //�����˵���ʾ
		
		
		List<Content> list = supportsService.findPageSupports(page, title, typeid); //�����б�
		DateTool datesupport = new DateTool();
		// ������������ֶ����ص�toolbox�ķ�ʽ
		support.addObject("listTypeSupport", listTypeSupport);
		// �������
		support.addObject("datesupport", datesupport);
		support.addObject("list", list);
		return support;
	}

	/*
	 *  ����ʵ������������ʾ������ģ��
	 *  ������Ҫ�������۱���
	 * 
	 */	
	public ModelAndView supportsShow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		ModelAndView mv1 = new ModelAndView("support.html");
		Page page = new Page();
		page.setStart(0);
		page.setLimit(30);
		// ����ֻ��һҳ�����Է�ҳʵ���ϲ���Ҫ��ֻ��Ϊ��������ǰ�ķ�ҳ��ѯ������
		// Integer typeid = null;
		Integer supportsssid = null;
		
		List<Type> listTypeSupport = supportsService.findTypeType(); //��߲˵���
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

		// ��ʾ��������ģ�飻

		DateTool datesupport = new DateTool();
		// String title = "����";
		Integer contentid = 95;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // ������Ҫ�޸ģ���Ҫ���붯̬��ȡ���û����������
		List<Comment> listcomment = commentService.findByContentid(contentid);
		mv1.addObject("datesupport", datesupport);
		mv1.addObject("listcomment", listcomment);
		
		return mv1;
	}
	
    /*
     * ����ģ��
     * 
     */
	public ModelAndView supportsComment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// ��ȡ��������������
		// ��ʾ����������
		ModelAndView mv2 = new ModelAndView("support.html");
		DateTool datesupport = new DateTool();

		Integer cintentid = 95;
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
		Content news = supportsService.FindSupports(cintentid);
		
		//������������
		Integer contentid = 95;
		// List<Comment> listcomment = commentService.findPageComments(page,
		// title); // ������Ҫ�޸ģ���Ҫ���붯̬��ȡ���û����������
		List<Comment> listcomment = commentService.findByContentid(contentid);
		//ע�⣺�����ﱾ����ֻ��Ҫ���ض�Ӧҳ����Ҫ�ı���ֵ���ɣ������Ƿ�ʹ���ĸ�����û�б�Ȼ��ϵ��ӳ���Ӧҳ��ķ���Ҳ��Ϊ�˼��ظķ����ı���ֵ����ҳ��
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
