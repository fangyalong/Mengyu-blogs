package mengyu.blogs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Message {
    private String code;//处理成功获取失败的标记
    private String message;//提示信息
    private Object retDate;//其他数据
}
