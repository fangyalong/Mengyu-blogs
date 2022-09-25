package mengyu.blogs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mengyu.blogs.pojo.Tag;
import mengyu.blogs.service.TagService;
import mengyu.blogs.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 龙少
* @description 针对表【t_tag】的数据库操作Service实现
* @createDate 2022-09-11 12:25:48
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{



}




