package mengyu.blogs.controller.show;

import javafx.scene.chart.AreaChart;
import mengyu.blogs.pojo.Blog;
import mengyu.blogs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Index")
public class archivesController {

    @Autowired
    private BlogService blogService;


    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> year = blogService.archiveBlog();
        model.addAttribute("archiveMap",year);
        model.addAttribute("count",blogService.count());
        return "html/archives";
    }
}
