package test;

//TODO @Component 方案2
@Component
public class XController {

   @Deprecated
   @MySign
    private XService xService;

    public int add(int a, int b) {
        return this.xService.add(a,b);
    }
}
