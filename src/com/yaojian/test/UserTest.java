package com.yaojian.test;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yaojian.mapper.UserMapper;
import com.yaojian.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/spring-common.xml")
public class UserTest {

	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testAdd(){
		User user = new User();
		user.setUsername("yaojian");
		user.setPassword("yaojian");
		user.setPassword("yaojian");
		userMapper.save(user);
		
	}
	
	@Test
	public void testFindAll(){
		List<User> findAllList = userMapper.findAll();
		System.out.println(findAllList.size());
	}
	
	@Test
	public void testFindById(){
		List<User> userList = userMapper.findAll();
		if(userList!=null&&userList.size()>0){
			User user=userMapper.findById(userList.get(0).getId());
			System.out.println(user.getId());
			System.out.println(user.getUsername());
		}
	}


	@Test
	public void testUpdate(){
		List<User> userList = userMapper.findAll();
		if(userList!=null&&userList.size()>0){
			User user=userMapper.findById(userList.get(0).getId());
			user.setUsername("GourdJYao");
			userMapper.update(user);
		}
	}
	
	@Test
	public void testDelete(){
//		userMapper.delete(1);
	}
}
