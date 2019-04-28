package com.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import com.demo.entity.User;
import com.demo.service.ShiroUserService;
import com.demo.service.UserService;
@Component
public class UserRealm extends AuthorizingRealm {

	@Resource
	private ShiroUserService shiroUserService;
	
	public String getName(){
		return "UserRealm";
	}
	
	//j授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		System.out.println("========进行授权=======");
		// 参数principals:用户认证信息
		// SimpleAuthenticationInfo:认证方法返回封装认证信息中的第一个参数：用户信息（username）
		//String username = (String) principals.getPrimaryPrincipal();
		User user = (User) principals.getPrimaryPrincipal();
		// 查询数据库，得到用户的角色，权限（此处手动授权）
		List<String> roles = new ArrayList<String>();
		List<String> permissions = new ArrayList<String>();
		//roles.add("admin");
		permissions.add("user:delete");
		permissions.add("user:update");
		
		// 返回用户在数据库中的权限和角色
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissions);
		info.addRoles(roles);
		return info;
	}

	//身份认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		String username = (String) token.getPrincipal(); // 得到用户名
		//查询数据库
		User user = shiroUserService.findUserByUsername(username);
		System.out.println(username+"********************************"+user.getPassword());
		//先进行username判断是否存在，
		if(user==null){
			return null;
		}
		
		//然后将username和数据库passwrod封装成info，之后再其他方法中与token进行密码匹配。
		
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现；
				// 1标识 2数据库密码/凭据，3盐（用户名）  4realm的名字
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getName()),getName());
		return info;
	}

	//清除缓存
	//在角色或权限service中,delete或者update方法去调用realm的清除缓存方法.
	public void clearCached() {
	     PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
	     super.clearCache(principals);
	}
}
