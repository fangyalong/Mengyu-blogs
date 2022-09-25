package mengyu.blogs.mapper;

import mengyu.blogs.pojo.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @author 龙少
* @description 针对表【t_blog】的数据库操作Mapper
* @createDate 2022-09-11 12:24:18
* @Entity mengyu.blogs.pojo.Blog
*/
@Repository
public interface BlogMapper extends BaseMapper<Blog> {

    List<Blog> QueryAllBlog(Map map);

    List<Blog> QueryBlogs(Map map);

    Blog getByIdBlog(Integer blogId);

    Integer conditionCount(Map map);

    int insertBlog(Blog blog);

    int selectByLikeCount(String query);

    List<Blog> selectBylike(Map map);

    int updateViewsById (@Param ( "views" ) Integer views , @Param ( "id" ) Integer id);

    Integer pageViewCount();

    List<Blog> QueryByTypeId(Map map);

   Integer getCountByTypeId(Integer typeId);

    List<Blog> queryByTagIds(Map map);

    Integer getCountByTagId(Integer tagId);

    List<Blog> selectByYear(String year);

    List<String> selectGroupYear();

    List<Blog> getNBlos();
}




