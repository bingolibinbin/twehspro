package com.twehs.service.power.impl;

import java.sql.SQLException;


import java.util.List;

import net.sf.json.JSONArray;

import com.twehs.service.power.UserService;
import com.twehs.service.power.dto.UserDTO;

import com.twehs.pojo.example.VuserMenuExample;
import com.twehs.dao.power.*;
import com.twehs.service.power.dto.UserMenuDTO;
import com.twehs.pojo.Role;
import com.twehs.pojo.User;
import com.twehs.pojo.VuserMenu;
import com.twehs.utils.pubutil.Page;
import com.twehs.dao.power.UserDAO;

public class UserServiceImpl implements UserService {

	private UserDAO userDao;
	private VuserMenuDAO vuserDao;

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public VuserMenuDAO getVuserDao() {
		return vuserDao;
	}

	public void setVuserDao(VuserMenuDAO vuserDao) {
		this.vuserDao = vuserDao;
	}

	public void setBaseDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	/*
	 * 鐧诲綍楠岃瘉
	 */
	@SuppressWarnings("unchecked")
	public UserDTO login(String pass, String code) throws SQLException {

		User user = userDao.selectByPrimarypassword(pass, code); // 鑾峰彇瀵瑰簲鐨勭敤鎴�

		if (user != null) {
			UserDTO dto = UserDTO.createDto(user);

			VuserMenuExample vUserMenuExample = new VuserMenuExample();
			VuserMenuExample.Criteria vUserMenuCriteria = vUserMenuExample
					.createCriteria();
			vUserMenuCriteria.andUseridEqualTo(user.getUserid());
			List<VuserMenu> list = vuserDao.selectByExample(vUserMenuExample);

			JSONArray jsong = JSONArray.fromObject(new UserMenuDTO().getTree(0,
					list));

			dto.setUsermenu(jsong.toString());

			return dto;
		}
		return null;
	}

	/*
	 * 鍒嗛〉鏌ヨ鐢ㄦ埛鍒楄〃
	 */
	@SuppressWarnings("unchecked")
	public void findPageUser(Page page) throws SQLException {
		// 鏌ヨ鐢ㄦ埛鍒楄〃

		List<User> listUser = userDao.selectByPaper(page.getStart(),
				page.getLimit());

		List dtoList = UserDTO.createDtos(listUser);
		int total = userDao.countAll(); // 杩欓噷countAll鏄嚜宸辨敼鍐欑殑锛屾湰韬笉鎻愪緵杩欎釜鍑芥暟
		page.setTotal(total);
		page.setRoot(dtoList);
	}

	public boolean saveOrUpdateUser(UserDTO dto) throws SQLException {
		User user = new User();
		if (dto.getUserid() != null) {
			user = (User) userDao.selectByPrimaryKey(dto.getUserid()); // 鎵惧嚭鏄惁宸茬粡瀛樺湪杩欎釜userid鏇存柊鎿嶄綔
			user.setLogincode(dto.getLogincode());
			user.setPassword(dto.getPassword());
			user.setUsername(dto.getUsername());
			user.setRole(new Role(dto.getRoleid()));
			user.setRoleid(dto.getRoleid());
			user.setState(0);
			user.setBz(dto.getBz());
			userDao.updateByPrimaryKeySelective(user); // 鏁版嵁搴撴洿鏂�
		} else {
			User newUser = userDao.selectByLoginCode(dto.getLogincode()); // 鎵惧嚭logincode瀵瑰簲鐨刄ser
																			// 淇濆瓨鎿嶄綔
			if (newUser != null) {
				return false; // 鐢ㄦ埛鍚嶅凡缁忓瓨鍦�
			}
			user.setLogincode(dto.getLogincode());
			user.setPassword(dto.getPassword());
			user.setUsername(dto.getUsername());
			user.setRole(new Role(dto.getRoleid()));
			user.setRoleid(dto.getRoleid());
			user.setState(0);
			user.setBz(dto.getBz());
			userDao.insert(user); // 鏁版嵁搴撴洿鏂�

		}
		return true;
	}

	/*
	 * 鍒犻櫎鐢ㄦ埛
	 */
	public void deleteUser(Integer userid) {
		try {
			userDao.deleteByPrimaryKey(userid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
