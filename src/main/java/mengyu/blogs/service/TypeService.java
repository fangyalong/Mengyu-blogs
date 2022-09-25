package mengyu.blogs.service;

import mengyu.blogs.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
* @author 龙少
* @description 针对表【t_type】的数据库操作Service
* @createDate 2022-09-11 12:25:57
 * @Transactional 将类中的所有方法添加上事务
*/
@Transactional
public interface TypeService extends IService<Type> {

   List<Type> selectAllByNameAndNameCount();

   List<Type> selectTypeAndPaging(Map map);

}
