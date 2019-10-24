package com.ischoolbar.programmer.entity;

/**
 * @author bingqiong.cbb
 * @date 2019-10-22 19:31
 * 管理员账户
 **/
public class User {
    private Long id;//primary key increment
    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
