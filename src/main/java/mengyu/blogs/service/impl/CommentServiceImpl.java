package mengyu.blogs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mengyu.blogs.pojo.Comment;
import mengyu.blogs.service.CommentService;
import mengyu.blogs.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 龙少
* @description 针对表【t_comment】的数据库操作Service实现
* @createDate 2022-09-11 12:25:44
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean saveComment(Comment comment) {
        return commentMapper.saveComment(comment);
    }

    @Override
    public List<Comment> selectByParentCommentId(Integer parentCommentId) {
        return commentMapper.selectByParentCommentId(parentCommentId);
    }

    @Override
    public Comment selectAllById(Integer parentCommentId) {
        return commentMapper.selectAllById(parentCommentId);
    }

    @Override
    public Integer selectAboutCount() {
        return commentMapper.selectAboutCount();
    }

    @Override
    public List<Comment> selectByBlogIdAndParentCommentIdIsNull(Integer blogId) {
        return commentMapper.selectByBlogIdAndParentCommentIdIsNull(blogId);
    }

    @Override
    public List<Comment> selectByAboutCommentCommentIdIsNull() {
        return commentMapper.selectByAboutCommentCommentIdIsNull();
    }
}




