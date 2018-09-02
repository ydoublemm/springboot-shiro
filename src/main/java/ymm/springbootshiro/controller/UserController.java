package ymm.springbootshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: ymm
 * @Date: 2018/8/29 0:02
 * @Description:
 */
@Controller
public class UserController {

    /*
     * @author: ymm
     * @date: 2018/8/29 0:06
     * @param: [] -> []
     * @return: java.lang.String
     * @Description: 测试
     */
    @RequestMapping("/index")
    public String  hello(Model model){
        return "index";
    }

    @RequestMapping("/add")
    public String  add( ){
        return "/user/add";
    }

    @RequestMapping("/update")
    public String  update( ){
        return "/user/update";
    }

    @RequestMapping("/login")
    public String  login( ){
        return "/login";
    }


    @RequestMapping("/userLogin")
    public String userLogin(String username,String password,Model model){
        //认证操作
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            currentUser.login(token);
        } catch (AuthenticationException e) {
           model.addAttribute("msg", e.getMessage());
        }

        return "/index";
    }


}
