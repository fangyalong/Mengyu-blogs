package mengyu.blogs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mengyu.blogs.mapper.BlogTagMapper;
import mengyu.blogs.pojo.Blog;
import mengyu.blogs.pojo.BlogTag;
import mengyu.blogs.service.BlogService;
import mengyu.blogs.mapper.BlogMapper;
import mengyu.blogs.util.MarkDownUtils;
import mengyu.blogs.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 龙少
* @description 针对表【t_blog】的数据库操作Service实现
* @createDate 2022-09-11 12:24:18
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Blog> queryAllBlog(Map map) {
        return blogMapper.QueryAllBlog(map);
    }

    @Override
    public List<Blog> queryBlogs(Map map) {
        return  blogMapper.QueryBlogs(map);
    }

    @Override
    public Blog getByIdBlog(Integer blogId) {
        Blog byIdBlog = blogMapper.getByIdBlog(blogId);
        String content = byIdBlog.getContent();
        String newContent = MarkDownUtils.markdownToHtmlExtensions(content);
        byIdBlog.setContent(newContent);
        return  byIdBlog;
    }

    @Override
    public Blog getByIdBlogNo(Integer blogId) {
        return blogMapper.getByIdBlog(blogId);
    }

    @Override
    public List<Blog> selectByLike(Map map) {
        List<Blog> blogs = blogMapper.selectBylike(map);

        return blogs;
    }

    @Override
    public int selectByLikeCount(String query) {
        return blogMapper.selectByLikeCount(query);
    }


    @Override
    public Integer getCount(Map map) {
        return blogMapper.conditionCount(map);
    }


    @Override
    public int insertBlog(Blog blog) {

        return  blogMapper.insertBlog(blog);
    }

    @Override
    public Boolean  updateNumById(Blog blog) {
        return blogMapper.updateViewsById(blog.getViews(),blog.getId())>1;
    }

    @Override
    public Integer getPageViewCount() {
        return blogMapper.pageViewCount();
    }

    @Override
    public List<Blog> queryByTypeId(Map map) {
        return blogMapper.QueryByTypeId(map);
    }

    @Override
    public Integer getCountByTypeId(Integer typeId) {
        return blogMapper.getCountByTypeId(typeId);
    }

    @Override
    public List<Blog> queryByTagIds(Map map) {
        return blogMapper.queryByTagIds(map);
    }

    @Override
    public Integer getCountByTagId(Integer tagId) {
        return blogMapper.getCountByTagId(tagId);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.selectGroupYear();
        Map<String,List<Blog>> map =new HashMap<>();
        for(String year:years){
            map.put(year,blogMapper.selectByYear(year));

        }

        return map;
    }

    @Override
    public List<Blog> getNBlos(Integer n) {
        List<Blog> nBlos = blogMapper.getNBlos();
        List<Blog> newBlog=new ArrayList<>();
        for (int i=0;i<n;i++){
            newBlog.add(nBlos.get(i));
        }

        return newBlog;
    }

}




