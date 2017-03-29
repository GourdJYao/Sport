package com.yaojian.service;

import java.util.List;

import com.yaojian.model.User;


public interface UserService {

	void save(User user);
	boolean update(User user);
	boolean delete(int id);
	User findById(int id);
	User findByUser(User user);
	List<User> findAll();
	User findByToken(String token);

}
