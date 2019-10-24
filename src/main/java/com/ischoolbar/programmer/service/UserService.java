package com.ischoolbar.programmer.service;

import com.ischoolbar.programmer.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author bingqiong.cbb
 * @date 2019-10-22 19:37
 **/
@Service
public interface UserService {
      User findByUsername(String username);
}
