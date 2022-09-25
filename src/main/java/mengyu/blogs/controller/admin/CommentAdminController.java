package mengyu.blogs.controller.admin;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mengyu.blogs.pojo.Comment;
import mengyu.blogs.pojo.Message;
import mengyu.blogs.service.CommentService;
import mengyu.blogs.util.CODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public String comments(@RequestParam(defaultValue = "1") Integer pageNum, Model model){
        Page<Comment> page=new Page<>();
        List<OrderItem> list=new ArrayList<>();
        OrderItem orderItem=new OrderItem();
        orderItem.setColumn("create_time");
        orderItem.setAsc(false);
        list.add(orderItem);
        page.setOrders(list);
        page.setCurrent(pageNum).setSize(5);
         page = commentService.page(page);

        model.addAttribute("page",page);
        return "admin/comment";
    }

    @ResponseBody
    @DeleteMapping("/deleteComments")
    public Object deleteComments(@RequestParam String commentId){
        Message message=null;
        try {
            boolean b = commentService.removeById(commentId);
            if (b){
                message=new Message(CODE.SUCCESS,null,null);
            }else{
                message=new Message(CODE.FAIL,"删除失败",null);
            }
        } catch (Exception e) {
            message=new Message(CODE.FAIL,"删除失败",null);
            return message;
        }
        return message;
    }
}
