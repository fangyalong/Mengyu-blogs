package mengyu.blogs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @TableName t_tag
 */
@TableName(value ="t_tag")
@Data
@AllArgsConstructor
public class Tag implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 计数
     */
    private Integer count;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Tag(Integer id, String name) {
        this.id=id;
        this.name=name;

    }
}