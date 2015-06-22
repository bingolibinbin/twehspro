package com.twehs.service.classify.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.twehs.dao.content.CommentDAO;
import com.twehs.dao.content.ContentDAO;
import com.twehs.dao.content.MessageDAO;
import com.twehs.pojo.Comment;
import com.twehs.pojo.Company;
import com.twehs.pojo.Content;
import com.twehs.service.classify.CommentService;
import com.twehs.service.classify.dto.CommentDTO;
import com.twehs.service.classify.dto.MessageDTO;
import com.twehs.service.classify.dto.NewsDTO;
import com.twehs.service.company.dto.CompanyDTO;
import com.twehs.utils.pubutil.Page;

public class CommentServiceImpl implements CommentService {

	// private ContentDAO contentDAO;
	private CommentDAO commentDAO;

	/*
	 * 鍒嗛〉鏌ヨ
	 */
	public List findPageComments(Page page, String title) throws SQLException {

		/*
		 * List list1 = contentDAO.selectByContentid(title); List<Comment>
		 * dtoListAll= new ArrayList<Comment>(); Iterator<Content> itr =
		 * list1.iterator(); while (itr.hasNext()) { Content
		 * itr1=(Content)itr.next(); Integer citr = itr1.getContentid();
		 * List<Comment> listcomment = commentDAO.selectByPaper(page.getStart(),
		 * page.getLimit(), citr);
		 * 
		 * @SuppressWarnings("rawtypes") Iterator<Comment> itr2 =
		 * listcomment.iterator(); while(itr2.hasNext()) {
		 * dtoListAll.add(itr2.next()); } } List<CommentDTO> dtoList =
		 * CommentDTO.createDtos(dtoListAll); int total = commentDAO.countAll();
		 * //杩欓噷countAll鏄嚜宸辨敼鍐欑殑锛屾湰韬笉鎻愪緵杩欎釜鍑芥暟 page.setTotal(total);
		 * page.setRoot(dtoList);
		 */

		List<Comment> listComment = commentDAO.selectByPaper(page.getStart(),
				page.getLimit(), title);
	/*	@SuppressWarnings("rawtypes")
		List<CommentDTO> dtoList = CommentDTO.createDtos(listComment);
		int total = commentDAO.countAll(); // 杩欓噷countAll鏄嚜宸辨敼鍐欑殑锛屾湰韬笉鎻愪緵杩欎釜鍑芥暟
		page.setTotal(total);
		page.setRoot(dtoList);*/
		return listComment;

	}
	
	
	
	public List findByContentid(Integer contentid) throws SQLException {

	

		List<Comment> listComment = commentDAO.selectByContentid(contentid);
	/*	@SuppressWarnings("rawtypes")
		List<CommentDTO> dtoList = CommentDTO.createDtos(listComment);
		int total = commentDAO.countAll(); // 杩欓噷countAll鏄嚜宸辨敼鍐欑殑锛屾湰韬笉鎻愪緵杩欎釜鍑芥暟
		page.setTotal(total);
		page.setRoot(dtoList);*/
		return listComment;

	}
	
	
	
	

	/*
	 * 鍒犻櫎鎿嶄綔
	 */
	public boolean deleteComments(Integer commentid) throws SQLException {
		try {
			// System.out.println("鍒犻櫎鎿嶄綔:"+contentid);
			commentDAO.deleteByPrimaryKey(commentid);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * 鏆傛椂杩樻病鍐�
	 */
	// checkComments
	public boolean checkComments(List commentList) throws SQLException {
		// commentDAO.updateByPrimaryKey(null); //鑷繁鍐欏搴旂殑鏂规硶锛�
		if (commentList.size() != 0)

		{
			// System.out.println("service灞�+commentList.get(0));
			commentDAO.updateByCheck(commentList);
			return true;
		} else
			return false;
	}
	
	
	public boolean saveOrUpdateComment(CommentDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		Comment comment = new Comment();
		if (dto.getCommentid() != null) {
			comment = (Comment) commentDAO.selectByPrimaryKey(dto
					.getCommentid());
			if (comment != null) {
				// 鏇存柊鎿嶄綔锛�
				
				
//				comment.setCommentid(dto.getCommentid());
				comment.setContentid(dto.getCintentid());
				comment.setTitle(dto.getTitle());
				comment.setCommentor(dto.getCommentor());
				comment.setCommentscontent(dto.getCommentscontent());
				comment.setDisplay(dto.isDisplay());
				comment.setCheck(dto.isCheck());
				comment.setOperatetime(dto.getOperatetime());
				
				commentDAO.updateByPrimaryKeySelective(comment); // 鏁版嵁搴撴洿鏂�

				return true;
			}
		} else {

			// 鎻掑叆鎿嶄綔
			Comment comment2 = new Comment();
			// company2.setCompanyid(dto.getCompanyid());
		
//			comment2.setCommentid(dto.getCommentid());
			comment2.setContentid(dto.getCintentid());
			comment2.setTitle(dto.getTitle());	
			comment2.setCommentor(dto.getCommentor());
			comment2.setCommentscontent(dto.getCommentscontent());
			comment2.setDisplay(dto.isDisplay());
			comment2.setCheck(dto.isCheck());
			comment2.setOperatetime(dto.getOperatetime());

			commentDAO.insertwithid(comment2);
			return true;
		}
		return false;
	}

	
	
	
	

	public CommentDAO getCommentDAO() {
		return commentDAO;
	}

	public void setCommentDAO(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

}
