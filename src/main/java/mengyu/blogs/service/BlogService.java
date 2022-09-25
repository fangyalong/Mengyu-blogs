package mengyu.blogs.service;

import mengyu.blogs.pojo.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
* @author 龙少
* @description 针对表【t_blog】的数据库操作Service
* @createDate 2022-09-11 12:24:18
*/
@Transactional
public interface BlogService extends IService<Blog> {

   List<Blog> queryAllBlog(Map map);

   List<Blog> queryBlogs(Map map);

   Blog getByIdBlog(Integer blogId);

   Blog getByIdBlogNo(Integer blogId);

   List<Blog> selectByLike(Map map);

   int selectByLikeCount(String query);

   Integer getCount(Map map);

   int insertBlog(Blog blog);

   Boolean  updateNumById(Blog blog);

   Integer getPageViewCount();

   List<Blog> queryByTypeId(Map map);

   Integer getCountByTypeId(Integer typeId);

   List<Blog> queryByTagIds(Map map);

  Integer getCountByTagId(Integer tagId);

  Map<String,List<Blog>> archiveBlog();

//  获取最新n条博客
   List<Blog> getNBlos(Integer n);


}
