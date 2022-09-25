package mengyu.blogs;

import mengyu.blogs.mapper.UserMapper;
import mengyu.blogs.pojo.Type;
import mengyu.blogs.pojo.User;
import mengyu.blogs.service.TypeService;
import mengyu.blogs.service.UserService;
import mengyu.blogs.util.CONVER;
import mengyu.blogs.util.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;


@SpringBootTest
class BlogsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    UserService UserService;

    @Autowired
    TypeService TypeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testCheckUser() {
        String username = "Mengyu";
        String password = "fyl20221";
//
//        User user = UserService.checkUser(username, password);
//        System.out.println(user);
        System.out.println(MD5Utils.code("123456"));

    }


    @Transactional
    @Test
    public void testTransactional() {
        Type type = new Type(1, "方亚龙",null);
        boolean byId = TypeService.updateById(type);
        boolean b = TypeService.removeById(1000);
        System.out.println("1234456");
    }


    @Test
    public void testconverToString() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        String s = converToString(list);
        System.out.println(s);
    }

    private String converToString(List<Integer> tagIds) {
        StringBuffer ids = new StringBuffer();
        for (int i = 0; i < tagIds.size(); i++) {
            Integer tagid = tagIds.get(i);
            if (i == (tagIds.size() - 1)) {
                ids.append(tagid);
            } else {
                ids.append(tagid + ",");
            }

        }
        return ids.toString();
    }


    @Test
    public void test1() {
        String abc = "1,2,javascript";
        List<Integer> integers = CONVER.converToList(abc);
        System.out.println(integers);
    }

    @Test
    public void testRides(){
      redisTemplate.opsForValue().set("num",1);
      Integer num= (Integer) redisTemplate.opsForValue().get("num");
        System.out.println(num);
    }



           }















