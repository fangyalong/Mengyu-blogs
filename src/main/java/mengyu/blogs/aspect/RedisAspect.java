package mengyu.blogs.aspect;

import mengyu.blogs.service.BlogService;
import mengyu.blogs.util.RedisUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class RedisAspect {


    private final RedisUtils redisUtils;


    public RedisAspect( RedisUtils redisUtils) {

        this.redisUtils = redisUtils;
    }

    @Pointcut("execution(public * mengyu.blogs.service.impl.BlogServiceImpl.getByIdBlog(..))")
    public void myPointCut(){}


    @After("myPointCut()")
    public void doAfter(JoinPoint joinPoint) throws Throwable{
        Object[] objs = joinPoint.getArgs();
        Integer id= (Integer) objs[0];
        redisUtils.zIncrementScore("views",id.toString(),1);
    }


    @Pointcut("execution(public * mengyu.blogs.service.impl.UserServiceImpl.getUser(..))")
    public void myAboutPoinCut(){}

    @After("myAboutPoinCut()")
    public void doAfterAbout() throws Throwable{
        System.out.println("更新成功");
        redisUtils.zIncrementScore("viewsAbout","mengyu",1);
    }
}
