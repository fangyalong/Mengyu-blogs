package mengyu.blogs.service;

import mengyu.blogs.mapper.TagMapper;
import mengyu.blogs.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
* @author 龙少
* @description 针对表【t_tag】的数据库操作Service
* @createDate 2022-09-11 12:25:48
*/
@Transactional
public interface TagService extends IService<Tag> {





}
