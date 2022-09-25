package mengyu.blogs.controller.show;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mengyu.blogs.pojo.Blog;
import mengyu.blogs.pojo.Type;
import mengyu.blogs.service.BlogService;
import mengyu.blogs.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Index")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "-1") Integer typeId,
                        Model model) {
        int num=0,size=5,totleNum=0;
        Page<Blog> page = new Page();
        page.setSize(size).setCurrent(pageNum);
        List<Type> types = typeService.selectAllByNameAndNameCount();
        types.forEach(System.out::println);
        if(typeId==-1){
            Type bigType = types.get(0);
            typeId= bigType.getId();
        }

        Map map=new HashMap<>();
        num=(pageNum-1)*size;
        map.put("typeId",typeId);
        map.put("num",num);
        map.put("size",size);
        List<Blog> blogs = blogService.queryByTypeId(map);
        Integer count = blogService.getCountByTypeId(typeId);
        if(count%size==0){
            totleNum=count/size;
        }else{
            totleNum=count/size+1;
        }
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totleNum",totleNum);
        model.addAttribute("blogs",blogs);
        model.addAttribute("typeId", typeId);
        model.addAttribute("types", types);
         return "html/types";
    }
}


