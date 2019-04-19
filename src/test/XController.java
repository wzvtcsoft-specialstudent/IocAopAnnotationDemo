package test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

//TODO @Component 方案2
@Component
public class XController {

   @Deprecated
   @MySign
    private XService xService;


    public XService getxService() {
        return xService;
    }

    public int add(int a, int b) {
        return this.xService.add(a,b);
    }
}
