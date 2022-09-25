package mengyu.blogs.controller.show;

import mengyu.blogs.pojo.Blog;
import mengyu.blogs.pojo.Message;
import mengyu.blogs.pojo.Tag;
import mengyu.blogs.pojo.Type;
import mengyu.blogs.service.BlogService;
import mengyu.blogs.service.BlogTagService;
import mengyu.blogs.service.TagService;
import mengyu.blogs.util.CODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Index")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogTagService blogTagService;

    @GetMapping("/tags")
    public Object tags(){
        return "html/tags";

    }

    @ResponseBody
    @GetMapping("/tagsList")
    public Object tagsList(){
        List<Tag> list = tagService.list();
        Message message=new Message(CODE.SUCCESS,null,list);
        return message;
    }

    @GetMapping("/getTagList")
    public String getTagList(@RequestParam(defaultValue = "-1") Integer tagId, Model model,
                             @RequestParam(defaultValue = "1") Integer pageNum){
        int num=0,size=2,totleNum=0;
        Map map=new HashMap<>();
        num=(pageNum-1)*size;
        map.put("tagId",tagId);
        map.put("num",num);
        map.put("size",size);
        List<Blog> blogs = blogService.queryByTagIds(map);

        for (int i=0;i<blogs.size();i++){
            List<Tag> tags = blogTagService.queryByBlodIdTag(blogs.get(i).getId());
            Blog blog = blogs.get(i);
            blog.setTags(tags);
        }
        Integer count = blogService.getCountByTagId(tagId);
        if(count%size==0){
            totleNum=count/size;
        }else{
            totleNum=count/size+1;
        }

        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totleNum",totleNum);
        model.addAttribute("blogs",blogs);
        model.addAttribute("tagId", tagId);
        return "html/tag";
    }



}
