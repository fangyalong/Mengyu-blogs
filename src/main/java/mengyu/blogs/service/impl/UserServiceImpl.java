package mengyu.blogs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mengyu.blogs.pojo.User;
import mengyu.blogs.service.UserService;
import mengyu.blogs.mapper.UserMapper;
import mengyu.blogs.util.MD5Utils;
import mengyu.blogs.util.MarkDownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 龙少
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-09-11 12:26:00
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;


    @Override
    public User checkUser(String username, String password) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username).eq("password", MD5Utils.code(password));
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User getUser() {
        User user = userMapper.getUser();
        String introduce = user.getIntroduce();
        String s = MarkDownUtils.markdownToHtml(introduce);
        user.setIntroduce(s);
        return user;
    }

    @Override
    public void updateUserBrowses(User user) {
        userMapper.updateById(user);
    }
}




