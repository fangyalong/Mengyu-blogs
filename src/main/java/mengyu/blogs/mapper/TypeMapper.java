package mengyu.blogs.mapper;

import mengyu.blogs.pojo.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
* @author 龙少
* @description 针对表【t_type】的数据库操作Mapper
* @createDate 2022-09-11 12:25:57
* @Entity mengyu.blogs.pojo.Type
*/
@Repository
public interface TypeMapper extends BaseMapper<Type> {

    List<Type> selectAllByNameAndNameCount();

    List<Type> selectTypeAndPaging(Map map);

}




