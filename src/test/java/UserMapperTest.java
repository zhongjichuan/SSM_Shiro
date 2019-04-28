import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import com.demo.entity.User;
import com.demo.mapper.UserMapper;
import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class UserMapperTest {

	@Resource
	private UserMapper userMapper;
	@Test
	public void test() {
		System.out.println("wojinlaile");
		//List<User> findAll = userMapper.findAll();
		//System.out.println(userMapper.selectCount(null)+"++++++++++++===========================================");
		//System.out.println(userMapper.findAll().size()+"----------------------");
//		User user1 = new User();
//		//user1.setMail("44");
//		user1.setAge(33);
//		user1.setName("王五4");
//		userMapper.insert(user1);
	//System.out.println(userMapper.selectOne(user1).getMail()+"[[[[[[[[[[[[[[[[[[[[[");
		for(User user : userMapper.selectAll()){
			System.out.println(user.getName());
		}
	}

	@Test
	public void test2(){
		int a = 1;
		String b = a+"ll";
		User user = new User();
		user.setName("admin7");
		//user.setAge(44);
		String mail = null;
		user.setMail(mail);
		user.setPassword(mail);
		userMapper.insertSelective(user);//是添加user中的非空属性
		//userMapper.insert(user);
	}
	
	@Test
	public void test3(){
		User user = new User();
		user.setPassword(DigestUtils.md5DigestAsHex(("admin").getBytes()));
		user.setName("admin");
		userMapper.updateByPrimaryKeySelective(user);
		System.out.println(user.getAge()+1);
	}
	@Resource
	public UserService userService;
	@Test
	public void test4(){
		User user = new User();
		user.setName("admin");
		user.setPassword("admin");
		User user2 = userService.validateUser(user);
		if(user2 != null)
			System.out.println(user2.getMail()+"======================");
	}
	
	@Test
	public void test5(){
		
		PageHelper.startPage(2,5 );
		List<User> userList = userMapper.findAll();
		for(User user : userList){
			System.out.println(user.getName());
		}
		PageInfo pageInfo = new PageInfo(userList);
		System.out.println(pageInfo);
	}
	
	@Test
	public void test6(){
		User user = userMapper.selectByPrimaryKey("zhangsan");
		System.out.println(user.getAge()+"========================");
	}
}

