package com.glw.miaosha.service;

import com.glw.miaosha.dao.UserDao;
import com.glw.miaosha.doman.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author glw
 * @date 2019/4/30 14:09
 */
@Service
public class UserService {

    @Autowired
    public UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

    //@Transactional
    public boolean ts() {
        User u1 = new User();
        u1.setId(3);
        u1.setName("lbw");

        User u2 = new User();
        u2.setId(2);
        u2.setName("lbwzz");

        userDao.insert(u1);
        userDao.insert(u2);
        return true;
    }
}
