package mengyu.blogs.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import mengyu.blogs.pojo.*;
import mengyu.blogs.service.BlogTagService;
import mengyu.blogs.service.TagService;
import mengyu.blogs.service.TypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mengyu.blogs.service.BlogService;
import mengyu.blogs.util.CODE;
import mengyu.blogs.util.CONVER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.sql.Timestamp;



@Controller
@RequestMapping("/admin")
public class BlogAdminController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogTagService blogTagService;


    @GetMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "1") String pageNum,
            Model model,
             Blog blog){

        int istj=0;
        long totleNum=1;
        int num=0;
        Map map=new HashMap<>();
        num=(Integer.parseInt(pageNum)-1)*2;
        map.put("blog",blog);
        map.put("num",num);
        map.put("size",2);
        List<Blog> blogs = blogService.queryAllBlog(map);

        Page page=new Page();
        page.setRecords(blogs);
        page.setSize(2).setCurrent(Integer.parseInt(pageNum));

        Page<Blog> page1=new Page(Integer.parseInt(pageNum),2);
        Page<Blog> page2 = blogService.page(page1);
        List<Type> typelists = typeService.list();

        model.addAttribute("istj",istj);
        model.addAttribute("totleNum",totleNum);
        model.addAttribute("blogs",page.getRecords());
        model.addAttribute("page",page);
        model.addAttribute("page2",page2);
        model.addAttribute("typelists",typelists);
        model.addAttribute("blog",blog);
        model.addAttribute("blogs11",blogs.size());
        model.addAttribute("is",0);
        return "admin/blogs";
    }


    @PostMapping ("/blogView")
    public Object blogView(@RequestParam(defaultValue = "1") int pageNum,
                        Model model,
                        Blog blog){
        int size=2;
        int istj=0;
        if(blog.getTitle()==null&&blog.getTypeId()==null&&blog.getRecommend()==null){
            istj=0;
            model.addAttribute("is",0);
        }else{
            istj=1;
            model.addAttribute("is",1);
        }

        long totleNum=0;
        int num=0;
        Map map=new HashMap<>();
        num=(pageNum-1)*2;

        map.put("blog",blog);
        map.put("num",num);
        map.put("size",2);

        List<Blog> blogs = blogService.queryAllBlog(map);
        Page<Blog> page=new Page();
        page.setRecords(blogs);
        page.setSize(2).setCurrent(pageNum);
        long count1 = blogService.getCount(map);
        if(count1%size==0){
            totleNum=count1/size;
        }else{
            totleNum=count1/size+1;
        }

        Page<Blog> page1=new Page<>(pageNum,2);
        Page<Blog> page2 = blogService.page(page1);
        List<Type> typelists = typeService.list();
        model.addAttribute("count1",count1);
        model.addAttribute("totleNum",totleNum);
        model.addAttribute("istj",istj);
        model.addAttribute("blogs",page.getRecords());
        model.addAttribute("page",page);
        model.addAttribute("page2",page2);
        model.addAttribute("typelists",typelists);
        model.addAttribute("blog",blog);
        model.addAttribute("blogs11",blogs.size());

        return "admin/blogs :: blogList";

    }

    @PostMapping("/conditionSelectBlogs")
    public Object conditionSelectBlogs(@RequestParam(defaultValue = "1") int pageNum,
            Model model,Blog blog){
        System.out.println(blog);
        int istj=0;
        if(blog.getTitle()==null&&blog.getTypeId()==null&&blog.getRecommend()==null){
            istj=0;
            model.addAttribute("is",0);
        }else{
            istj=1;
            model.addAttribute("is",1);
        }
        int size=2;
        int num=0;
        long totleNum=0;
        Map map=new HashMap<>();
        num=(pageNum-1)*2;
        map.put("blog",blog);
        map.put("num",num);
        map.put("size",size);
        List<Blog> blogs = blogService.queryAllBlog(map);
        Page<Blog> page=new Page();
        page.setRecords(blogs);
        page.setSize(size).setCurrent(pageNum);
        Page<Blog> page1=new Page<>(pageNum,size);
        Page<Blog> page2 = blogService.page(page1);
        List<Type> typelists = typeService.list();
        long count1 = blogService.getCount(map);

        if(count1%size==0){
            totleNum=count1/size;
        }else{
            totleNum=count1/size+1;
        }

        model.addAttribute("count1",count1);
        model.addAttribute("totleNum",totleNum);
        model.addAttribute("istj",istj);
        model.addAttribute("blogs",page.getRecords());
        model.addAttribute("page",page);
        model.addAttribute("page2",page2);
        model.addAttribute("typelists",typelists);
        model.addAttribute("blog",blog);
        model.addAttribute("blogs11",blogs.size());
        return "admin/blogs :: blogList";
    }

    @GetMapping ("/goSaveBlog")
    public String goSaveBlog(Blog blog,Model model) {
        List<Tag> tags= tagService.list();
        List<Type> types=  typeService.list();

        model.addAttribute("tags",tags);
        model.addAttribute("types",types);
        model.addAttribute("blog",blog);
        return "admin/blogs-input";
    }


    @GetMapping("/goUpdateBlog/{blogId}/{dqy}")
    public String goUpdateBlog(@PathVariable Integer blogId,
                              @PathVariable Integer dqy,
                              Model model) {

        Blog blog = blogService.getByIdBlogNo(blogId);
        List<Type> types = typeService.list();
        List<Tag> tags = tagService.list();
        List<Integer> tagIds = blogTagService.queryTagIds(blogId);
        String tagIdsString = CONVER.converToString(tagIds);
        blog.setTagIds(tagIdsString);
        model.addAttribute("dqy", dqy);
        model.addAttribute("blog", blog);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        return "admin/blogs-input";
    }



    @ResponseBody
    @PostMapping ("/saveBlog")
    public Object saveBlog(Blog  blog, HttpSession session){
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId().toString());
        blog.setUpdateTime(new Timestamp(new Date().getTime()));
        blog.setViews(0);
        blog.setId(CONVER.getId());

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("title",blog.getTitle());
        Blog isBlog = blogService.getOne(queryWrapper);
        Message message;
        boolean save=false;
        if(isBlog==null){
            int isSave=0;
            try {
                isSave  = blogService.insertBlog(blog);
                String tagIds = blog.getTagIds();
                List<Integer> tagId = CONVER.converToList(tagIds);
                for(int i=0;i<tagId.size();i++){
                    Integer oneTagId = tagId.get(i);
                    BlogTag blogTag=new BlogTag(null,blog.getId(),oneTagId);
                       blogTagService.save(blogTag);
                }
            } catch (Exception e) {
                message=new Message(CODE.FAIL,"添加失败",null);
                return message;
            }

            if(isSave!=0&&save==true){
                message = new Message(CODE.SUCCESS, null, null);
            }else {
                message = new Message(CODE.FAIL,"添加失败",null);
            }
            return message;

        }else{
            message = new Message(CODE.FAIL, "添加失败,已经存在该博客", null);
            return message;
        }
    }


    @ResponseBody
    @DeleteMapping("/deleteBlog")
    public Object deleteBlog(@RequestParam Integer blogId){
        QueryWrapper<BlogTag> queryWrapper=new QueryWrapper();
        queryWrapper.eq("blog_id",blogId);
        boolean remove = blogTagService.remove(queryWrapper);
        boolean isDelete = blogService.removeById(blogId);
        Message message = null;
        if (isDelete) {
            message = new Message(CODE.SUCCESS, null, null);
        } else {
            message = new Message(CODE.FAIL, "没有对应的博客", null);
        }
        return message;


    }


    @ResponseBody
    @PutMapping("/updateBlog")
    public Object updateBlog( Blog blog){


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", blog.getId());
        Blog isBlog = blogService.getOne(queryWrapper);
        QueryWrapper<BlogTag> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("blog_id",blog.getId());
        Message message=null;


        blog.setUpdateTime(new Timestamp(new Date().getTime()));
        if(isBlog!=null){
            Boolean  isUpdate=false;
            String tagIds = blog.getTagIds();
            try {
                blogTagService.remove(queryWrapper1);
                List<Integer> tagId = CONVER.converToList(tagIds);
                for(int i=0;i<tagId.size();i++){
                    Integer oneTagId = tagId.get(i);
                    BlogTag blogTag=new BlogTag(null,blog.getId(),oneTagId);
                    blogTagService.save(blogTag);
                }
                isUpdate = blogService.updateById(blog);

            } catch (Exception e) {

                message=new Message(CODE.FAIL,"更新失败",null);
                return message;
            }
            if(isUpdate){
                message=new Message(CODE.SUCCESS,null,null);

            }else {
                message=new Message(CODE.FAIL,"更新失败",null);

            }
              return message;
        }else{
            message=new Message(CODE.FAIL,"已存在该博客,更新失败",null);
            return   message;
        }
    }




}


