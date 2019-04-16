package test;

//TODO @Component 方案2

public class XController {

//    @Deprecated
    @Component
    private XService xService;

    public int add(int a, int b) {
        return this.xService.add(a,b);
    }
}
