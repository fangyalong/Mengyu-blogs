package mengyu.blogs.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CONVER {

    public static List<Integer> converToList(String ids) {
        List<Integer> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String replace = ids.replace(",,", ",");
            String[] idarray = replace.split(",");
            for (int i = 0; i < idarray.length; i++){
                    list.add(new Integer(idarray[i]));
            }
        }
        return list;
    }

    public static String converToString(List<Integer> tagIds) {
        StringBuffer ids=new StringBuffer();
        for (int i = 0; i < tagIds.size(); i++){
            Integer tagid = tagIds.get(i);
            if(i== tagIds.size()-1){
                ids.append(tagid);
            }else{
                ids.append(tagid+",");
            }

        }
        return ids.toString();
    }

    public static Integer getId() {
        StringBuffer stringBuffer=new StringBuffer();
        Random random = new Random();
        for (int i=0;i<6;i++){
            int b = random.nextInt(10);
            stringBuffer.append(b);
        }
        int i = Integer.parseInt(stringBuffer.toString());
        return i;
    }

}
