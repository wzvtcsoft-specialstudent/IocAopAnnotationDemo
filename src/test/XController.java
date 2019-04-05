package test;

@Component
public class XController {

    @Autowired
    private XService xService;

    public int add(int a, int b) {
        return this.xService.add(a,b);
    }
}
