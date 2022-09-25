package mengyu.blogs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_link
 */
@TableName(value ="t_link")
@Data
public class Link implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 网站链接
     */
    private String href;

    /**
     * 
     */
    private Integer count;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}