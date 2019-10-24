package com.ischoolbar.programmer.controller;

import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.service.UserService;
import com.ischoolbar.programmer.util.Cpacha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author bingqiong.cbb
 * @date 2019-10-17 20:02
 * 系统主页控制器
 **/
@Controller
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private UserService userService;
    /**
     * 方法名上的注释作用，表明路径
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("helloWorld");
        modelAndView.addObject("user","chenlf");
        return modelAndView;
    }

    /**
     * 登录页面
     * login
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("system/login");
        modelAndView.addObject("user","chenlf");
        return modelAndView;
    }

    /**
     * 登录表单提交
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody//返回的对象作为json方式，返回页面
    public Map<String,String> login(@RequestParam(value = "password",required = true)String password,
                                    @RequestParam(value = "username",required = true) String username,
                                    @RequestParam(value = "verifyCode",required = true) String verifyCode,
                                    HttpServletRequest request) {
        Map<String,String> ret = new HashMap<String,String>();
        if(StringUtils.isEmpty(username)){
            ret.put("type","error");
            ret.put("msg","用户名不能为空");
            return ret;
        }
        if(StringUtils.isEmpty(password)){
            ret.put("type","error");
            ret.put("msg","密码不能为空");
            return ret;
        }
        if(StringUtils.isEmpty(verifyCode)){
            ret.put("type","error");
            ret.put("msg","验证码不能为空");
            return ret;
        }
        String vcode = (String)request.getSession().getAttribute("loginCpacha");
        if(StringUtils.isEmpty(vcode)){
            ret.put("type","error");
            ret.put("msg","长时间未操作，会话已失效，请刷新后重试");
            return ret;
        }
        if(!verifyCode.toUpperCase().equals(vcode.toUpperCase())){
            ret.put("type","error");
            ret.put("msg","验证码错误");
            return ret;
        }
        //清除session中的验证码
        request.getSession().setAttribute("loginCpacha",null);

//        从数据库中查找用户
        User user = userService.findByUsername(username);
        if(user==null){
            ret.put("type","error");
            ret.put("msg","用户不存在");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","登录成功");
        return ret;
    }

    /**
     * 显示验证码
     * @param request
     * @param response
     */

    @RequestMapping(value = "/get_cpacha",method = RequestMethod.GET)
    public void getCpacha(HttpServletRequest request,HttpServletResponse response){
        Cpacha cpacha = new Cpacha(4, 98,33);
        String generatorVcode = cpacha.generatorVcode();
        request.getSession().setAttribute("loginCpacha",generatorVcode);
        BufferedImage generatorRotateVcodeImage = cpacha.generatorRotateVcodeImage(generatorVcode, true);

        try {
            ImageIO.write(generatorRotateVcodeImage,"gif", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
