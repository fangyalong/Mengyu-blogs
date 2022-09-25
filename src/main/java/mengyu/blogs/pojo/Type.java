package mengyu.blogs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;

/**
 * 
 * @TableName t_type
 */
@TableName(value ="t_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type implements Serializable {
    /**
     * 
     */

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 类型名
     */
    @NonNull
    private String name;


    private Integer nameCount;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Type(String name){
        this.name=name;
    }
}