package com.popant.baseweb.base.service.impl;

import com.popant.baseweb.base.generator.po.Permission;
import com.popant.baseweb.base.generator.po.Role;
import com.popant.baseweb.base.generator.po.UserInfo;
import com.popant.baseweb.base.service.IPermissionService;
import com.popant.baseweb.base.service.IRoleService;
import com.popant.baseweb.base.service.IUserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userRealm")
public class UserRealm extends AuthorizingRealm {
	private Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IPermissionService permissionService;
	public UserRealm() {
		super();
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		String username = String.valueOf(principals.getPrimaryPrincipal());

		final UserInfo user = userInfoService.selectByUsername(username);
		final List<Role> roleInfos = roleService.selectRolesByUserId(user.getUserId());
		for (Role role : roleInfos) {
			// 添加角色
			logger.debug(String.format("用户%s获取到角色%s", user.getUserName(), role.getRoleEngName()));
			authorizationInfo.addRole(role.getRoleEngName());

			final List<Permission> permissions = permissionService.selectPermissionsByRoleId(role.getRoleId());
			for (Permission permission : permissions) {
				// 添加权限
				logger.debug(String.format("==>用户%s获取到权限%s", user.getUserName(), permission.getPermissionEngName()));
				authorizationInfo.addStringPermission(permission.getPermissionEngName());
			}
		}
		return authorizationInfo;

	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		String username = String.valueOf(authcToken.getPrincipal());
		String password = new String((char[]) authcToken.getCredentials());
		// 通过数据库进行验证
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(username);
        userInfo.setPassword(password);
		final UserInfo authentication = userInfoService.authentication(userInfo);
		if (authentication == null) {
			throw new AuthenticationException("用户名或密码错误.");
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
		return authenticationInfo;
	}

}
