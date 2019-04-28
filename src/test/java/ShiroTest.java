import static org.junit.Assert.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;




@ContextConfiguration
public class ShiroTest {

	@Test
	public void test() {
		//1.创建SecurityManager工厂对象：加载配置文件，创建工厂对象
		//Factory<SecurityManager> factory = new IniSecurityManagerFactory("F:/worksapce/SSM/src/main/resources/shiro.ini");
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		//2.通过工厂对象创建SecurityManager
		SecurityManager securityManager = factory.getInstance();
		//3.将securityManager绑定到当前运行环境中，让系统随时可以访问securityManager对象
		SecurityUtils.setSecurityManager(securityManager);
		//4.创建当前登录的主体
		Subject subject = SecurityUtils.getSubject();
		//5.收集主提登录的账号密码
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");
		try {
			subject.login(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//判断登录是否成功
		System.out.println(subject.isAuthenticated());
		//退出
		subject.logout();
		System.out.println(subject.isAuthenticated());
	}

	
	//自定义realm进行验证
	@Test
	public void test2() {
		// 1.创建SecurityManager工厂对象：加载配置文件，创建工厂对象
		// Factory<SecurityManager> factory = new
		// IniSecurityManagerFactory("F:/worksapce/SSM/src/main/resources/shiro.ini");
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
		System.out.println(subject.isAuthenticated());
		// 退出
		subject.logout();
		System.out.println(subject.isAuthenticated());
	}
	
	//MD5加密
	@Test
	public void test3(){
		String password = "123";
		Md5Hash md5Hash1 = new Md5Hash(password);
		System.out.println(md5Hash1);
		//盐，散列次数
		Md5Hash md5Hash2 = new Md5Hash(password, "zhangsan", 3);
		System.out.println(md5Hash2);
		
	}
	
	//权限
	@Test
	public void test4() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiroConfig/shiro-RBAC.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan",
				"123");
		try {
			subject.login(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 判断登录是否成功
		System.out.println("登录成功"+subject.isAuthenticated());
		
		//是否拥有权限
		//System.out.println(subject.isPermitted("user:delete"));
		System.out.println(subject.isPermittedAll("user:create","user:update","user:delete"));
		
		//是否拥有角色
		//System.out.println(subject.hasRole("role2"));
		// 退出
		subject.logout();
		//System.out.println(subject.isAuthenticated());
	}
}
