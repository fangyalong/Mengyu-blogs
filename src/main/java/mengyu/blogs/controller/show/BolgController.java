package mengyu.blogs.controller.show;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import mengyu.blogs.pojo.Blog;
import mengyu.blogs.pojo.Comment;
import mengyu.blogs.pojo.Message;
import mengyu.blogs.service.BlogService;
import mengyu.blogs.service.CommentService;
import mengyu.blogs.util.CODE;
import mengyu.blogs.util.MailDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BolgController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private JavaMailSender mailSender;

    @ResponseBody
    @PostMapping("/emailAlert")
    public Object emailAlert( Comment comment){
        System.out.println(comment);
        Message message=new Message();
        try {
            SimpleMailMessage message1 = new SimpleMailMessage();
            Blog blog = blogService.getByIdBlog(comment.getBlogId());
            String url = MailDataUtils.DOMAIN_NAME + "/blog/" + blog.getId();
            if (comment.getTypeComment() != null) {
                url = MailDataUtils.DOMAIN_NAME + "/toAbout";
            }
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
                        "\n文章:" + blog.getTitle() +
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
                        "\n文章:" + blog.getTitle() +
                        "\n文章地址:<a href=" + url + ">" + url + "</a>");
                System.out.println("发送成功2");
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
}
