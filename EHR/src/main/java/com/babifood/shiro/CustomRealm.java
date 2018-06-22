package com.babifood.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.babifood.entity.LoginEntity;
import com.babifood.entity.RoleAuthorityEntity;
import com.babifood.entity.UserRoleEntity;
import com.babifood.service.LoginService;
import com.babifood.service.NewUsersService;
@Service
public class CustomRealm extends AuthorizingRealm{
	@Autowired
	LoginService loginService;
	@Autowired
	NewUsersService newUsersService;
	// 设置realm的名称
	@Override
	public void setName(String name) {
		super.setName("CustomRealm");
	}	
	/**
	 * 授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		List<String> permissions = new ArrayList<>();
		// TODO Auto-generated method stub
		//从 principals获取主身份信息
		//将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
		LoginEntity userinfo = (LoginEntity) principals.getPrimaryPrincipal();
		StringBuffer strRole = new StringBuffer();
		List<UserRoleEntity> roleList = userinfo.getRoleList();
		for (int i = 0;i<roleList.size();i++) {
			strRole.append("'");
			strRole.append(roleList.get(i).getRole_id());
			if(i==roleList.size()-1){
				strRole.append("'");
			}else{
				strRole.append("',");
			}
		}
		//根据身份信息获取权限信息
		//从数据库获取到权限数据
		List<RoleAuthorityEntity> authorityList = newUsersService.loadRoleAuthority(strRole.toString());
		if(authorityList!=null){
			for (RoleAuthorityEntity roleAuthorityEntity : authorityList) {
				permissions.add(roleAuthorityEntity.getAuthority_code());
			}
		}
		//将上边查询到授权信息填充到simpleAuthorizationInfo对象中
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissions);
		return simpleAuthorizationInfo;
	}
	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		// token是用户输入的用户名和密码 
		// 第一步从token中取出用户名
		String userCode = (String) token.getPrincipal();
		// 第二步：根据用户输入的userCode从数据库查询
		LoginEntity login = loginService.findLoginWhereUserName(userCode);
		// 如果查询不到返回null
		if(login==null){
			return null;
		}
		// 从数据库查询到密码
		String password = login.getPassword();
		//获取用户对应的角色信息
		login.setRoleList(newUsersService.loadRoleWhereUser(login.getUser_id()));
		/**
		 * 第一个参数是用户信息
		 * 第二个参数是用户密码
		 * 
		 * 第三个参数是Realm的名称
		 */
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				login, password,this.getName());

		return simpleAuthenticationInfo;
	}
	//清除缓存
	public void clearCached() {
		PrincipalCollection principas = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principas);
	}

}
