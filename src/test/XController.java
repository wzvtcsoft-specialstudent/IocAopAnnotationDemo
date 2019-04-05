package test;

//TODO @Component 方案2
public class XController {

    @Deprecated
    private XService xService=new XService();

    public int add(int a, int b) {
        return this.xService.add(a,b);
    }
}
