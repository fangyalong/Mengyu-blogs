package mengyu.blogs.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mengyu.blogs.pojo.Message;
import mengyu.blogs.pojo.Tag;
import mengyu.blogs.service.TagService;
import mengyu.blogs.util.CODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagAdminController {

    @Autowired
    private TagService tagService;

    private Page<Tag> page=null;


    @GetMapping("/listTags")
    public String listTags(@RequestParam(defaultValue = "0") int pageNum,
                            Model model) {
        page = new Page<>();
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem id = OrderItem.asc("id");
        orderItemList.add(id);

        page.setSize(2).setCurrent(pageNum).setOrders(orderItemList);

        Page<Tag> tags = tagService.page(page);

        model.addAttribute("tags", tags);
        model.addAttribute("page", page);
        return "admin/tags";
    }


    @ResponseBody
    @PostMapping(value = "/selectTag")
    public Object selectTag(@RequestParam Integer tagId) {
        Tag tag = tagService.getById(tagId);
        Message message = null;

        if (tag != null) {
            message = new Message(CODE.SUCCESS, null, tag);
        } else {
            message = new Message(CODE.FAIL, "没有查到对应的标签", null);
        }
        return message;
    }


    @GetMapping("/goUpdateTag/{id}/{name}")
    public String goUpdateTag(@PathVariable Integer id, @PathVariable String name,
                               Model model) {
        Tag tag = new Tag(id,name);
        model.addAttribute("tag", tag);
        return "admin/tags-input";
    }

    @GetMapping ("/goSaveTag")
    public String goSaveTag() {
        return "admin/tags-input";
    }

    @ResponseBody
    @DeleteMapping("/deleteTag")
    public Object deleteTag(@RequestParam Integer tagId) {
        boolean isDelete = false;
        Message message = null;
        try {
            isDelete = tagService.removeById(tagId);
        } catch (Exception e) {
            message = new Message(CODE.FAIL, "无法删除", null);
            return message;
        }

        if (isDelete) {
            message = new Message(CODE.SUCCESS, null, null);
        } else {
            message = new Message(CODE.FAIL, "没有对应的标签", null);
        }
        return message;
    }


    @ResponseBody
    @PostMapping("/flipOverTag")
    public Object flipOverTag(@RequestParam(defaultValue = "0") int pageNum,
                           Model model) {
        Page<Tag> page = new Page<>();
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem id = OrderItem.asc("id");
        orderItemList.add(id);

        page.setSize(1).setCurrent(pageNum).setOrders(orderItemList);

        Page<Tag> tags = tagService.page(page);
        Message message;
        if (tags != null) {
            message = new Message(CODE.SUCCESS, null, tags);
            model.addAttribute("tags", tags);
            model.addAttribute("page", page);
        } else {
            message = new Message(CODE.FAIL, "未查找到对应的数据", null);
        }

        return message;
    }

    @ResponseBody
    @PostMapping("/saveTag")
    public Object saveTag(@RequestParam String tagName) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", tagName);
        Tag isTag = tagService.getOne(queryWrapper);
        Message message;

        if (isTag == null) {
            Tag tag =new Tag(null, tagName);
            boolean isSave=false;
            try {
                isSave = tagService.save(tag);
            } catch (Exception e) {

                message = new Message(CODE.FAIL, "添加失败", null);
                return  message;
            }

            if (isSave) {
                message = new Message(CODE.SUCCESS, null, null);
            } else {
                message = new Message(CODE.FAIL, "添加失败", null);
            }
            return message;
        } else {
            message = new Message(CODE.FAIL, "添加失败,已经存在该标签", null);
            return message;
        }
    }

    @ResponseBody
    @PutMapping("/updateTag")
    public Object updateTag(@RequestParam Integer tagId,@RequestParam String tagName){

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", tagName);
        Tag isTag = tagService.getOne(queryWrapper);
        Message message=null;

        if(isTag==null){
            Tag tag= new Tag(tagId,tagName);
            Boolean  isUpdate=false;
            try {
                isUpdate = tagService.updateById(tag);

            } catch (Exception e) {

                message=new Message(CODE.FAIL,"更新失败",null);
                return message;
            }
            long current = page.getCurrent();
            if(isUpdate){
                message=new Message(CODE.SUCCESS,null,current);
            }else {
                message=new Message(CODE.FAIL,"更新失败",null);

            }
            return message;

        }else{
            message=new Message(CODE.FAIL,"已存在该标签,更新失败",null);
            return   message;

        }

    }

}

