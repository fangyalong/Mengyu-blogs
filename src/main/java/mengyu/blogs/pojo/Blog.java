package mengyu.blogs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName t_blog
 */
@TableName(value ="t_blog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 首图
     */
    private String firstPicture;

    /**
     * 标志
     */
    private String flag;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 赞赏开启
     */
    private Integer appreciation;

    /**
     * 版权开启
     */
    private Integer shareStatement;

    /**
     * 评论开启
     */
    private Integer commentabled;

    /**
     * 推荐开启
     */
    private Integer published;

    /**
     * 是否发布
     */
    private Integer recommend;

    /**
     * 
     */
    private Timestamp createTime;

    /**
     * 修改时间
     */
    private Timestamp updateTime;

    /**
     * 对应type的id
     */
    private String typeId;

    /**
     * 对应tag的id
     */
    private String tagId;

    /**
     * 博客描述
     */
    private String description;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 对应用户的id
     */
    private String userId;



    @TableField(exist = false)
    private String tagIds;


    @TableField(exist = false)
    private List<Tag> tags;

    @TableField(exist = false)
    private Type type;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}