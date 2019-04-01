package com.mt.microservice.microservices.services;

import com.mt.microservice.microservices.dao.UserService;
import com.mt.microservice.microservices.entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Adam", new Date()));
        users.add(new User(2, "Jimmy", new Date()));
        users.add(new User(3, "James", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    @Override
    public User findOneByUser(User user) {

        if (users.get(user.getId()) != null) {
            return user;
        }

        return null;
    }

    @Override
    public User findOneById(Integer id) {

        if (users.stream().anyMatch(u -> u.getId().equals(id))) {
            return users.stream().filter(u -> u.getId().equals(id)).findAny().get();
        }
        return null;
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User delete(Integer id) {

        Iterator<User> userIterator = users.iterator();

        if (userIterator.hasNext()) {
            User user = userIterator.next();
            if (user.getId().equals(id)) {
                userIterator.remove();
                return user;
            }
        }

        /*
            users.removeIf(user -> user.getId().equals(id));
        */

        return null;
    }
}
