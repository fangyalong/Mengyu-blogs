package mengyu.blogs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @TableName t_blog_tag
 */
@TableName(value ="t_blog_tag")
@Data
@AllArgsConstructor
public class BlogTag implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 博客id
     */

    private Integer blogId;

    /**
     * 标签id
     */

    private Integer tagId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}