package com.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class MyRealm extends AuthorizingRealm {

	public String getName(){
		return "MyRealm";
	}
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		//参数principals:用户认证信息
		//SimpleAuthenticationInfo:认证方法返回封装认证信息中的第一个参数：用户信息（username）
		String username = (String)principals.getPrimaryPrincipal();
		
		//查询数据库，得到用户的角色，权限
		List<String> roles = new ArrayList<String>();
		List<String> permissions = new ArrayList<String>();
		roles.add("role1");
		permissions.add("user:delete");
		
		//返回用户在数据库中的权限和角色
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissions);
		info.addRoles(roles);
		return info;
	}

	
	//认证,token为前台传过来的username，password
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		// 假设从数据库取出数据 zhangsan 123
		// 返回身份验证过程中提交的帐户标识username，凭据password
		String username = (String) token.getPrincipal(); // 得到用户名
		String password = new String((char[]) token.getCredentials()); // 得到密码
		if (!"zhangsan".equals(username)) {
			throw new UnknownAccountException(); // 如果用户名错误
		}
		
		//先进行username判断是否存在，然后将username和数据库passwrod封装成info，之后再其他方法中与token进行密码匹配。
		
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现；
		// 1共同标识 2数据库密码/凭据，3realm的名字
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("zhangsan", "123", getName());
		return info;
	}

	public static void main(String[] args) {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-myrealm.ini");
		// 2.通过工厂对象创建SecurityManager
		SecurityManager securityManager = factory.getInstance();
		// 3.将securityManager绑定到当前运行环境中，让系统随时可以访问securityManager对象
		SecurityUtils.setSecurityManager(securityManager);
		// 4.创建当前登录的主体
		Subject subject = SecurityUtils.getSubject();
		// 5.收集主提登录的账号密码
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan",
				"123");
		try {
			subject.login(token);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 判断登录是否成功
		System.out.println("登录：："+subject.isAuthenticated());
		System.out.println("角色验证"+subject.hasRole("role1"));
		System.out.println("权限验证"+subject.isPermitted("user:delete"));
		// 退出
		subject.logout();
		System.out.println("退出"+subject.isAuthenticated());
	}
}
