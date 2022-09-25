package mengyu.blogs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_comment
 */
@TableName(value ="t_comment")
@Data
public class Comment implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /*** 昵称*/
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 对应blog的id
     */
    private Integer blogId;

    /**
     * 对应的父评论
     */
    private Integer parentCommentId;

    private Integer adminiStrator;

    private Integer typeComment;


    @TableField(exist = false)
    private ArrayList<Comment> sonComments=new ArrayList<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}