import test.XController;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
    public static void main(String[] args) {
        XController xc=(XController)Application.getContext().get("test.XController");

        int sum=xc.add(3,5);
        System.out.println(sum==8);//此处应该输出true

    }

    private static Map<String,Object> getContext() {
        //TODO 1.遍历所有装载的Class，找到有@Component的类。
        //TODO 2.实例化他们并放到一个Map中。
        //TODO 3.检查所有的实例化类，看看他们是否有@Autowired属性，
        //TODO 4.如果有，则在map中找到同类型的bean，并且赋值给相应属性。

        return null;
    }

}
