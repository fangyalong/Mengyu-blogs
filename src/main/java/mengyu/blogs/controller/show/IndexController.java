package mengyu.blogs.controller.show;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mengyu.blogs.mapper.BlogTagMapper;
import mengyu.blogs.pojo.Blog;
import mengyu.blogs.pojo.Comment;
import mengyu.blogs.pojo.Tag;
import mengyu.blogs.pojo.Type;
import mengyu.blogs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private QQService qqService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogTagService blogTagService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = {"/","Index"})
    public String Index(@RequestParam(defaultValue = "1") String pageNum,Model model){
        int num=0,size=5;
        Page<Blog> page=new Page<>();
        page.setSize(size).setCurrent(Integer.parseInt(pageNum));
        Page<Blog> page1 = blogService.page(page);
        long fansCount = qqService.count();
        long commentCount = commentService.count();
        Map map=new HashMap<>();
        num=(Integer.parseInt(pageNum)-1)*size;
        map.put("num",num);
        map.put("size",size);
        List<Blog> blogs = blogService.queryBlogs(map);
        for(int i=0;i<blogs.size();i++){
            Blog blog = blogs.get(i);
            Type byId = typeService.getById(blog.getTypeId());
            blog.setType(byId);
        }
        Integer pageViewCount = blogService.getPageViewCount();
        model.addAttribute("pageViewCount",pageViewCount);

        model.addAttribute("blogs",blogs);
        model.addAttribute("page1",page1);
        model.addAttribute("fansCount",fansCount);
        model.addAttribute("commentCount",commentCount);
        return "html/index";
    }


    @GetMapping("/blog")
    public String blog(@RequestParam String blogId,Model model){
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("blog_id",blogId);
        List<Comment> comments = commentService.list(queryWrapper);
        Blog byIdBlog = blogService.getByIdBlog(Integer.parseInt(blogId));
        List<Tag> tags = blogTagService.queryByBlodIdTag(Integer.parseInt(blogId));

        model.addAttribute("comments",comments);
        model.addAttribute("blog",byIdBlog);

        model.addAttribute("tags",tags);
        return "html/blog";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query,Model model,@RequestParam(defaultValue = "1") Integer pageNum){

        int num=0,size=5,totleNum=0;
        num=(pageNum-1)*size;
        Map map=new HashMap();
        map.put("num",num);
        map.put("size",size);
        map.put("query",query);
        List<Blog> blogs = blogService.selectByLike(map);
        int count = blogService.selectByLikeCount(query);

        if(count%size==0){
            totleNum=count/size;
        }else{
            totleNum=count/size+1;
        }
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totleNum",totleNum);
        model.addAttribute("blogs",blogs);
        model.addAttribute("total",count);
        model.addAttribute("query",query);

        return "html/search";
    }

    @ResponseBody
    @GetMapping("/TagShowList")
    public Object TagShowList(){
        List<Tag> tags = tagService.list();
        return tags;
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        System.out.println("123456");
        List<Blog> newBlos = blogService.getNBlos(3);
        model.addAttribute("newBlos",newBlos);
        return "common/introduce :: newblogList";
    }
}
