package mengyu.blogs.handler;

import mengyu.blogs.pojo.Blog;
import mengyu.blogs.pojo.User;
import mengyu.blogs.service.BlogService;
import mengyu.blogs.service.UserService;
import mengyu.blogs.util.RedisUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Set;


@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ListenHandler {

    private final BlogService blogService;
    private final RedisUtils redisUtil;
    private final UserService userService;

    private static final String VIEW_KEY = "views";
    private static final String NAME = "mengyu";
    private static final String VIEW_KEY1 = "viewsAbout";


    @Autowired
    public ListenHandler(BlogService blogService, RedisUtils redisUtil, UserService userService) {
        this.blogService = blogService;
        this.redisUtil = redisUtil;
        this.userService = userService;
    }

    @PostConstruct
    public void init() throws Exception{
        log.info("数据初始化开始...");
        //将数据库中的数据写入redis
        List<Blog> blogList = blogService.list();
        User user = userService.getById(1);
        blogList.forEach(blog -> {
            //将浏览量写入redis
            redisUtil.zAdd(VIEW_KEY, blog.getId().toString(), blog.getViews());
        });
        redisUtil.zAdd(VIEW_KEY1,NAME,user.getBrowses());
        log.info("数据已写入redis...");
    }

    @Scheduled(cron = "*/30 * * * * ?")
    public void updateNum() {
        log.info("周期任务开始执行...");
        Set<ZSetOperations.TypedTuple<String>> viewNum = redisUtil.zReverseRangeWithScores(VIEW_KEY, 0, 10);
        Set<ZSetOperations.TypedTuple<String>> viewNum1 = redisUtil.zReverseRangeWithScores(VIEW_KEY1, 0, 10);

        writeNum(viewNum, VIEW_KEY);
        writeNum1(viewNum1, VIEW_KEY1);
        log.info("周期任务执行完毕,redis写入数据库完毕");
    }


    private void writeNum(Set<ZSetOperations.TypedTuple<String>> set, String fieldName) {
        set.forEach(item -> {
            Long id = Long.valueOf(item.getValue());
            Integer num = item.getScore().intValue();
            Blog blog = blogService.getById(id);
            //更新数据库
            if (blog!=null){
                blog.setViews(num);
                blogService.updateNumById(blog);
            }
            log.info("{} 更新完毕", fieldName);
        });
    }

    private void writeNum1(Set<ZSetOperations.TypedTuple<String>> set, String fieldName) {
        set.forEach(item -> {
            String username= item.getValue();
            Integer num = item.getScore().intValue();
            User user = userService.getById(1);
            //更新数据库
            if (user!=null){
                user.setBrowses(num);
                userService.updateUserBrowses(user);
            }
            log.info("{} 更新完毕", fieldName);
        });
    }



    @PreDestroy
    public void afterDestroy()  {
        log.info("开始关闭...");
        //将redis中的数据写入数据库
        Set<ZSetOperations.TypedTuple<String>> viewNum = redisUtil.zReverseRangeWithScores(VIEW_KEY, 0, 10);
        Set<ZSetOperations.TypedTuple<String>> viewNum1 = redisUtil.zReverseRangeWithScores(VIEW_KEY1, 0, 10);

        writeNum(viewNum, VIEW_KEY);
        writeNum(viewNum1, VIEW_KEY1);

        log.info("redis写入数据库完毕");
    }
}
