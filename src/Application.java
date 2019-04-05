import test.XController;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
    public static void main(String[] args) {
       // XController xc=new XController();
        scanElements("test");
        List<Field> fields=findFieldsInClassWithAnnotation(XController.class,Deprecated.class);
        for(Field f:fields){
            //输出该字段的类型
            System.out.println(f.getType());
        }

      /*  XController xc=(XController)Application.getContext().get("XController");

        int sum=xc.add(3,5);
        System.out.println(sum);
        */

    }

    private static Map<String,Object> getContext() {
        //TODO 1.遍历所有装载的Class，找到有@Component的类。
        //TODO 2.实例化他们并放到一个Map中。
        //TODO 3.检查所有的实例化类，看看他们是否有@Autowired属性，
        //TODO 4.如果有，则在map中找到同类型的bean，并且赋值给相应属性。

        return null;
    }

    /**
     * 扫描包下的所有类，并输出所有类上面的注解，如果需要使用，可以自行修改或调试
     * 注意，本方法只能扫描我们自己定义的类，而不能扫描jar里的类，如java.lang
     * @param packageName
     */
     static public void scanElements(String packageName){
        try {
            //Load the classLoader which loads this class.
            ClassLoader classLoader = Application.class.getClassLoader();

            //Change the package structure to directory structure
            String packagePath  = packageName.replace('.', '/');
            URL urls = classLoader.getResource(packagePath);

            //Get all the class files in the specified URL Path.
            File folder = new File(urls.getPath());
            File[] classes = folder.listFiles();

            int size = classes.length;
            List<Class<?>> classList = new ArrayList<Class<?>>();

            for(int i=0;i<size;i++){
                int index = classes[i].getName().indexOf(".");
                String className = classes[i].getName().substring(0, index);
                String classNamePath = packageName+"."+className;
                Class<?> repoClass;
                repoClass = Class.forName(classNamePath);
                Annotation[] annotations = repoClass.getAnnotations();
                for(int j =0;j<annotations.length;j++){
                    System.out.println("Annotation in class "+repoClass.getName()+ " is "+annotations[j].annotationType().getName());
                }
                classList.add(repoClass);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找指定类里带有指定注解的字段～
     * @param targetClazz
     * @param annotationClazz
     * @return
     */
    static public List<Field> findFieldsInClassWithAnnotation(Class targetClazz,Class annotationClazz){
         List<Field> fields=new ArrayList<>();
        for(Field field  : targetClazz.getDeclaredFields())
        {
            if (field.isAnnotationPresent(annotationClazz))
            {
                fields.add(field);
            }
        }
        return fields;
    }
}
