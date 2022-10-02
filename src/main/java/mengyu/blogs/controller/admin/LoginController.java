package mengyu.blogs.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import mengyu.blogs.pojo.Message;
import mengyu.blogs.pojo.User;
import mengyu.blogs.service.UserService;
import mengyu.blogs.util.CODE;
import mengyu.blogs.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    private UserService userService;

    @GetMapping({"/loginView","/"})
    public String loginView(){
        return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){

        User user = userService.checkUser(username, password);

        if(user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/admin/toIndex";
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误!");
            return "redirect:/admin/";
        }
    }


    @GetMapping("/toIndex")
    public String toIndex(HttpSession session){
        User user = (User) session.getAttribute("user");
        if ( user!=null){
            return "admin/index";
        }else {
            return "redirect:/admin/";
        }
    }









    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response){

        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/admin/";
    }

    @ResponseBody
    @PutMapping("/updatePassword")
    public Object updatePassword(@RequestParam String user,@RequestParam String password,HttpSession session){
        Message message=null;
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",user);
        User one = userService.getOne(queryWrapper);
        if(one!=null){
            User user1=new User();
            user1.setId(one.getId());
            String code = MD5Utils.code(password);
            user1.setPassword(code);
            userService.updateById(user1);
            session.invalidate();
            message=new Message(CODE.SUCCESS,null,null);
        }else{
            message=new Message(CODE.FAIL,"更新失败",null);
        }
        return message;
    }

    @GetMapping("/updatePassword")
    public String updatePassword(){
        return "admin/updatePassword";
    }

//废弃
    @GetMapping("/isLogin")
    public String isLogin(HttpSession session){
        User user = (User) session.getAttribute("user");

        if(user!=null){
            return "admin/index";
        }else{
            return "redirect:/admin/login";
        }
    }
}
