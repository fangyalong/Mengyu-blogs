package mengyu.blogs.mapper;

import mengyu.blogs.pojo.Blog;
import mengyu.blogs.pojo.BlogTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mengyu.blogs.pojo.Tag;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
* @author 龙少
* @description 针对表【t_blog_tag】的数据库操作Mapper
* @createDate 2022-09-11 12:25:39
* @Entity mengyu.blogs.pojo.BlogTag
*/
@Repository
public interface BlogTagMapper extends BaseMapper<BlogTag> {

  List<Integer> queryTagIds(Integer blogId);

  List<String> queryByBlodIdTags(Integer blogId);

  List<Tag> queryByBlodIdTag(Integer blogId);

}




