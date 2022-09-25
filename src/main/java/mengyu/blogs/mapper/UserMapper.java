package mengyu.blogs.mapper;

import mengyu.blogs.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 龙少
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2022-09-11 12:26:00
* @Entity mengyu.blogs.pojo.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

    User getUser();

}




