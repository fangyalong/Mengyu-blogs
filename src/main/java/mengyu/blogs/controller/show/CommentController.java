package mengyu.blogs.controller.show;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import mengyu.blogs.pojo.Comment;
import mengyu.blogs.pojo.Message;
import mengyu.blogs.pojo.QQ;
import mengyu.blogs.pojo.User;
import mengyu.blogs.service.CommentService;
import mengyu.blogs.service.QQService;
import mengyu.blogs.util.CODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/business")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private QQService qqService;

    @ResponseBody
    @RequestMapping("/selectAllComment")
    public Object selectAllComment() {
        return null;
    }

    @ResponseBody
    @PostMapping("/saveComment")
    public Object saveComment(Comment comment, @RequestParam String qq, HttpSession session) {
        comment.setTypeComment(0);
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
    @GetMapping("/comments")
    public Object comments(String blogId){

        List<Comment> comments = commentService.selectByBlogIdAndParentCommentIdIsNull(Integer.parseInt(blogId));
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
