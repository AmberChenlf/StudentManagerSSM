package com.ischoolbar.programmer.dao;

import com.ischoolbar.programmer.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author bingqiong.cbb
 * @date 2019-10-22 19:37
 **/
@Repository
public interface UserDao {
     User findByUsername(String username);

}
