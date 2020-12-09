package com.icbms.core.service.app.impl;

import com.icbms.common.constant.Constant;
import com.icbms.common.exception.MyException;
import com.icbms.core.util.ShiroUtils;
import com.icbms.core.service.app.ApiUserService;
import com.icbms.core.service.sys.impl.UserServiceImpl;
import com.icbms.repository.dao.app.ApiUserDao;
import com.icbms.repository.dao.sys.UserDao;
import com.icbms.repository.domain.app.ApiUserEntity;
import com.icbms.repository.domain.sys.UserEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


@Service("userApiService")
public class ApiUserServiceImpl extends UserServiceImpl implements ApiUserService {
	@Autowired
	private ApiUserDao apiUserDao;

	@Autowired
	private UserDao userDao;

	@Override
	public ApiUserEntity userInfo(String id) {
		if(StringUtils.isEmpty(id)){
			throw new MyException("用户id不能为空");
		}
		return apiUserDao.userInfo(id);
	}

	@Override
	public String login(UserEntity userEntity) {
		if(StringUtils.isEmpty(userEntity.getLoginName())){
			throw new MyException("登陆用户名不能为空!");
		}
		if(StringUtils.isEmpty(userEntity.getPassWord())){
			throw new MyException("登陆密码不能为空!");
		}
		UserEntity user = queryByLoginName(userEntity.getLoginName());
		if(user == null){
			throw new MyException("登陆用户名或密码错误");
		}
		//密码错误
		if(!user.getPassWord().equals(ShiroUtils.EncodeSalt(userEntity.getPassWord(),user.getSalt()))){
			throw new MyException("登陆用户名或密码错误");
		}
		return user.getId();
	}

	@Override
	public int updatePassword(UserEntity newUser,UserEntity oldUser) {
		if(newUser == null){
			throw new MyException("用户信息不能为空!");
		}
		if(StringUtils.isEmpty(newUser.getNewPassWord())){
			throw new MyException("新密码不能为空");
		}
		String newPassWord = ShiroUtils.EncodeSalt(newUser.getPassWord(),oldUser.getSalt());
		if(Constant.SUPERR_USER.equals(oldUser.getId())){
			throw new MyException("不能修改超级管理员密码!");
		}
		if(!newPassWord.equals(oldUser.getPassWord())){
			throw new MyException("密码不正确");
		}
		Map<String,Object> params = new HashMap<>();
		//生成salt
		String salt = RandomStringUtils.randomAlphanumeric(20);
		params.put("id",oldUser.getId());
		params.put("salt",salt);
		params.put("passWord",ShiroUtils.EncodeSalt(newUser.getNewPassWord(),salt));
		return userDao.updatePassword(params);
	}

	@Override
	public UserEntity checkUserAcct(String loginName, String password) {
		UserEntity user = queryByLoginName(loginName);
		//密码错误
		if(user==null ||(!user.getPassWord().equals(ShiroUtils.EncodeSalt(password,user.getSalt())))){
			return new UserEntity();
		}
		return user;
	}
}
