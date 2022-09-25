package mengyu.blogs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mengyu.blogs.pojo.BlogTag;
import mengyu.blogs.pojo.Tag;
import mengyu.blogs.service.BlogTagService;
import mengyu.blogs.mapper.BlogTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 龙少
* @description 针对表【t_blog_tag】的数据库操作Service实现
* @createDate 2022-09-11 12:25:39
*/
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag>
    implements BlogTagService{


    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    public List<Integer> queryTagIds(Integer blogId) {
        return blogTagMapper.queryTagIds(blogId);
    }

    @Override
    public List<String> queryByBlodIdTags(Integer blogId) {
        return blogTagMapper.queryByBlodIdTags(blogId);
    }

    @Override
    public List<Tag> queryByBlodIdTag(Integer blogId) {
        return blogTagMapper.queryByBlodIdTag(blogId);
    }
}




