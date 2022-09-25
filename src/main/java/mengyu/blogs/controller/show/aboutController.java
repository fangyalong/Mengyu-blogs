package mengyu.blogs.controller.show;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import mengyu.blogs.pojo.*;
import mengyu.blogs.service.CommentService;
import mengyu.blogs.service.QQService;
import mengyu.blogs.service.UserService;
import mengyu.blogs.util.CODE;
import mengyu.blogs.util.MailDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/Index")
public class aboutController {

    @Autowired
    private UserService userService;

    @Autowired
    private QQService qqService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/about")
    private String about(Model model){
        User user = userService.getUser();
        Integer commentnum = commentService.selectAboutCount();
        model.addAttribute("user",user);
        model.addAttribute("commentnum",commentnum);
        return "html/about";

    }

    @ResponseBody
    @PostMapping("/saveComment")
    public Object saveComment(Comment comment, @RequestParam String qq, HttpSession session) {
        comment.setTypeComment(1);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAdminiStrator(1);
        } else {
            comment.setAdminiStrator(0);
        }
        if (comment.getParentCommentId() == -1) {
            comment.setParentCommentId(null);
        }
        Message message=null;
        try {
            QQ newQQ=new QQ(null,qq,comment.getEmail(),comment.getAvatar());
            QueryWrapper<QQ> qqQueryWrapper=new QueryWrapper<>();
            qqQueryWrapper.eq("bank",newQQ.getBank());
            QQ isQQ= qqService.getOne(qqQueryWrapper);
            boolean save1=true;
            if(isQQ==null){
                save1 = qqService.save(newQQ);
            }

            boolean save = commentService.saveComment(comment);

            if(save&&save1){
                message=new Message(CODE.SUCCESS,"保存成功",null);
            }else{
                message=new Message(CODE.FAIL,"保存失败",null);
            }
        } catch (Exception e) {
            message=new Message(CODE.FAIL,"保存失败",null);
            return message;
        }
        return message;
    }

    @ResponseBody
    @PostMapping("/emailAlert")
    public Object emailAlert( Comment comment){

        Message message=new Message();
        try {
            SimpleMailMessage message1 = new SimpleMailMessage();

              String  url = MailDataUtils.DOMAIN_NAME + "/toAbout";

            if (comment.getParentCommentId() != null && comment.getParentCommentId() != -1) {


                Comment parentComment = commentService.selectAllById(comment.getParentCommentId());
                //发件人
                message1.setFrom(MailDataUtils.MAIL_SENDER);

                //收件人
                message1.setTo(parentComment.getEmail());
                //邮件标题
                message1.setSubject(MailDataUtils.MAIL_TITLE);
                //邮件内容
                message1.setText("评论人:" + comment.getNickname() +
                        "\n评论内容:" + comment.getContent() +
                        "\n评论位置:关于我页"  +
                        "\n文章地址:" + url);
                mailSender.send(message1);
            } else {
                //发件人
                message1.setFrom(MailDataUtils.MAIL_SENDER);
                //抄送人
                message1.setTo(MailDataUtils.MAIL_CC);
                //邮件标题
                message1.setSubject(MailDataUtils.MAIL_TITLE);
                //邮件内容
                message1.setText("评论人:" + comment.getNickname() +
                        "\n评论内容:" + comment.getContent() +
                        "\n文章:关于我页"  +
                        "\n文章地址:<a href=" + url + ">" + url + "</a>");
                mailSender.send(message1);
            }
            message.setMessage(CODE.SUCCESS);
            return message;
        } catch (MailException e) {
            e.printStackTrace();
            message.setCode(CODE.FAIL);
            message.setMessage("系统繁忙,请稍后再试");
            return message;
        }

    }

    @ResponseBody
    @GetMapping("/comments")
    public Object comments(){

        List<Comment> comments = commentService.selectByAboutCommentCommentIdIsNull();
        querySonComments(comments);
        return comments;

    }

    //递归查询子评论
    public void querySonComments(List<Comment> list){
        if(list.size()>0){
            for(Comment comment:list){
                List<Comment> sonCommentsList = commentService.selectByParentCommentId(comment.getId());
                if(sonCommentsList.size()>0){
                    List<Comment> CommentList=comment.getSonComments();
                    for (Comment sonComments:sonCommentsList){
                        CommentList.add(sonComments);
                    }
                    querySonComments(CommentList);
                }
            }
        }

    }
}


