package mengyu.blogs.service;

import mengyu.blogs.pojo.BlogTag;
import com.baomidou.mybatisplus.extension.service.IService;
import mengyu.blogs.pojo.Tag;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
* @author 龙少
* @description 针对表【t_blog_tag】的数据库操作Service
* @createDate 2022-09-11 12:25:39
*/
@Transactional
public interface BlogTagService extends IService<BlogTag> {


    List<Integer> queryTagIds(Integer blogId);

    List<String> queryByBlodIdTags(Integer blogId);

    List<Tag> queryByBlodIdTag(Integer blogId);


}
