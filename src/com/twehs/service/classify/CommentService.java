package com.twehs.service.classify;

import java.sql.SQLException;
import java.util.List;

import com.twehs.service.classify.dto.CommentDTO;
import com.twehs.utils.pubutil.Page;

public interface CommentService {

	public List findPageComments(Page page, String title) throws SQLException;
	public List findByContentid(Integer contentid) throws SQLException;

	// 杩欓噷闇�鍔犲弬鏁帮紱

	public boolean deleteComments(Integer commentid) throws SQLException;

	// checkComments
	public boolean saveOrUpdateComment(CommentDTO dto) throws SQLException;
	public boolean checkComments(List commentList) throws SQLException;
	// 鏆傛椂涓嶅啓锛岀湅鍏蜂綋鍔熻兘鍐嶅啓锛�

}
