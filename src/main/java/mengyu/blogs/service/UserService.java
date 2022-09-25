package mengyu.blogs.service;

import mengyu.blogs.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 龙少
* @description 针对表【t_user】的数据库操作Service
* @createDate 2022-09-11 12:26:00
*/
@Transactional
public interface UserService extends IService<User> {

    User checkUser(String username,String password);

    User getUser();

    void updateUserBrowses(User user);


}
