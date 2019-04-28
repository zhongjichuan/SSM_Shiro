package com.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.Factory;

public class CryptogramRealm extends AuthorizingRealm {

	public String getName(){
		return "CryptogramRealm";
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		// 假设从数据库取出数据 zhangsan dc5c3a487b9e7ec7e8680234af418631
		// 返回身份验证过程中提交的帐户标识username，凭据password
		String username = (String) token.getPrincipal(); // 得到用户名
		if (!"zhangsan".equals(username)) {
			return null; // 如果用户名错误
		}
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现；
		// 1标识 2数据库密码/凭据，3盐（用户名）  4realm的名字
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, "dc5c3a487b9e7ec7e8680234af418631", ByteSource.Util.bytes("zhangsan"),getName());
		return info;
	}

	public static void main(String[] args) {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-cryptogram.ini");
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
		System.out.println(subject.isAuthenticated());
		// 退出
		subject.logout();
		System.out.println(subject.isAuthenticated());
	}
}
