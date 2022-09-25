package mengyu.blogs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mengyu.blogs.pojo.Type;
import mengyu.blogs.service.TypeService;
import mengyu.blogs.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author 龙少
* @description 针对表【t_type】的数据库操作Service实现
* @createDate 2022-09-11 12:25:57
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> selectAllByNameAndNameCount() {
        return typeMapper.selectAllByNameAndNameCount();
    }

    @Override
    public List<Type> selectTypeAndPaging(Map map) {
        return typeMapper.selectTypeAndPaging(map);
    }
}




