package mengyu.blogs.mapper;

import mengyu.blogs.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
* @author 龙少
* @description 针对表【t_comment】的数据库操作Mapper
* @createDate 2022-09-11 12:25:44
* @Entity mengyu.blogs.pojo.Comment
*/
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    boolean saveComment(Comment comment);

    Comment selectAllById(Integer parentCommentId);

    List<Comment> selectByBlogIdAndParentCommentIdIsNull(Integer blogId);

    List<Comment> selectByParentCommentId(Integer parentCommentId);


   List<Comment> selectByAboutCommentCommentIdIsNull();

   Integer selectAboutCount();
}




