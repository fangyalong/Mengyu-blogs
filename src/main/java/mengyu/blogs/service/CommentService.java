package mengyu.blogs.service;

import mengyu.blogs.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 龙少
* @description 针对表【t_comment】的数据库操作Service
* @createDate 2022-09-11 12:25:44
*/
@Transactional
public interface CommentService extends IService<Comment> {

    boolean saveComment(Comment comment);

    List<Comment> selectByParentCommentId(Integer parentCommentId);

    Comment selectAllById(Integer parentCommentId);

    Integer selectAboutCount();

    List<Comment> selectByBlogIdAndParentCommentIdIsNull(Integer blogId);

    List<Comment> selectByAboutCommentCommentIdIsNull();
}
