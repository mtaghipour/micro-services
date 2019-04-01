package com.mt.microservice.microservices.dao;

import com.mt.microservice.microservices.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findOneByUser(User user);

    User findOneById(Integer id);

    User saveUser(User user);

    User delete(Integer id);
}
