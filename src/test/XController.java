package test;

//TODO @Component 方案2
public class XController {

    //TODO 方案2 @Autowired
    private XService xService=new XService();

    public int add(int a, int b) {
        return this.xService.add(a,b);
    }
}
