package mengyu.blogs.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mengyu.blogs.pojo.Message;
import mengyu.blogs.pojo.Type;
import mengyu.blogs.service.TypeService;
import mengyu.blogs.util.CODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TypeAdminController {

    @Autowired
    private TypeService typeService;

    private Integer staticPageNum=0;


    @GetMapping("/listTypes")
    public String typesList(@RequestParam(defaultValue = "1") int pageNum,
                            Model model) {
        int num=0,size=2,totleNum=0;
        Map map=new HashMap();
        num=(pageNum-1)*size;
        staticPageNum=pageNum;
        map.put("num",num);
        map.put("size",size);
        List<Type> types = typeService.selectTypeAndPaging(map);
        Integer count = Math.toIntExact(typeService.count());
        if(count%size==0){
            totleNum=count/size;
        }else{
            totleNum=count/size+1;
        }
        model.addAttribute("types", types);
        model.addAttribute("totleNum", totleNum);
        model.addAttribute("pageNum", pageNum);

        return "admin/types";
    }


    @ResponseBody
    @PostMapping(value = "/selectType")
    public Object selectType(@RequestParam Integer typeId) {
        Type type = typeService.getById(typeId);
        Message message = null;

        if (type != null) {
            message = new Message(CODE.SUCCESS, null, type);
        } else {
            message = new Message(CODE.FAIL, "没有查到对应的类型", null);
        }
        return message;
    }


    @GetMapping("/goUpdateType/{id}/{name}")
    public String goUpdateType(@PathVariable Integer id, @PathVariable String name,
                               Model model) {
        Type type = new Type(id, name,null);
        model.addAttribute("type", type);
        return "admin/types-input";
    }

    @GetMapping ("/goSaveType")
    public String goSaveType() {
        return "admin/types-input";
    }

    @ResponseBody
    @DeleteMapping("/deleteType")
    public Object deleteType(@RequestParam Integer typeId) {
        boolean isDelete = typeService.removeById(typeId);
        Message message = null;
        if (isDelete) {
            message = new Message(CODE.SUCCESS, null, null);
        } else {
            message = new Message(CODE.FAIL, "没有对应的类型", null);
        }
        return message;
    }


    @ResponseBody
    @PostMapping("/flipOver")
    public Object flipOver(@RequestParam(defaultValue = "0") int pageNum,
                           Model model) {
        Page<Type> page = new Page<>();
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem id = OrderItem.asc("id");
        orderItemList.add(id);

        page.setSize(1).setCurrent(pageNum).setOrders(orderItemList);

        Page<Type> types = typeService.page(page);
        Message message;
        if (types != null) {
            message = new Message(CODE.SUCCESS, null, types);
            model.addAttribute("types", types);
            model.addAttribute("page", page);
        } else {
            message = new Message(CODE.FAIL, "未查找到对应的数据", null);
        }

        return message;
    }

    @ResponseBody
    @PostMapping("/saveType")
    public Object saveType(@RequestParam(defaultValue = "") String typeName) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", typeName);
        Type isType = typeService.getOne(queryWrapper);
        Message message;

        if (isType == null) {
            Type type =  new Type(null, typeName,null);
            boolean isSave=false;
            try {

                isSave = typeService.save(type);

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
            message = new Message(CODE.FAIL, "添加失败,已经存在该类型", null);
             return message;
        }
    }

    @ResponseBody
    @PutMapping("/updateType")
    public Object updateType(@RequestParam Integer typeId,@RequestParam String typeName){
        int num=0,size=2;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", typeName);
        Type isType = typeService.getOne(queryWrapper);
        Message message=null;

        if(isType==null){
            Type  type= new Type(typeId,typeName,null);
            Boolean  isUpdate=false;
            try {
                isUpdate = typeService.updateById(type);
            } catch (Exception e) {

                message=new Message(CODE.FAIL,"更新失败",null);
                return message;
            }
            Map map=new HashMap();
            num=(staticPageNum-1)*size;
            map.put("num",num);
            map.put("size",size);

            List<Type> types = typeService.selectTypeAndPaging(map);
            System.out.println(types);
            if(types.size()<=0){
                staticPageNum=1;
            }


            if(isUpdate){
                message=new Message(CODE.SUCCESS,null,staticPageNum);
                return message;
            }else {
                message=new Message(CODE.FAIL,"更新失败",null);
                return message;
        }

        }else{
            message=new Message(CODE.FAIL,"已存在该类型,更新失败",null);
            return   message;

        }

    }

}