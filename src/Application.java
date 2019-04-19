import org.omg.CORBA.MARSHAL;
import test.Component;
import test.MySign;
import test.XController;
import test.XService;

import java.io.File;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {

    static Map<String ,Object>map=new HashMap<>();
//    static Map<String,String >mapName=new HashMap<>();

    public static void main(String[] args) {

//        scanElements("test");
//        System.out.println(map.size());
//        System.out.println( map.get("XService"));
//
//        XController xc=(XController)map.get("XController");
//        System.out.println(xc.getxService());
//
//        int sum=xc.add(3,5);
//        System.out.println(sum);
//        List<Field> fields=findFieldsInClassWithAnnotation(XController.class, MySign.class);
//        for(Field f:fields){
            //输出该字段的类型
//            System.out.println(f.getName());
//        }
//        System.out.println();
//        System.out.println(XController.class.getFields());

       XController xc=(XController)Application.getContext().get("XController");

        int sum=xc.add(3,5);
        System.out.println(sum);


    }

    private static Map<String,Object> getContext() {

        scanElements("test");
        for (Map.Entry<String ,Object> entry : map.entrySet()) {
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field f: fields) {
                f.setAccessible(true);
                if(f.isAnnotationPresent(MySign.class)){
                    int index = f.getType().getName().indexOf(".");
//                    System.out.println(index);
                    String str = f.getType().getName().substring(index+1);
                    Object oj= map.get(str);
//                    System.out.println(map.get(f.getName()));
                    try {
                        f.set(entry.getValue(),oj);
                        map.put(entry.getKey(),entry.getValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //TODO 4.如果有，则在map中找到同类型的bean，并且赋值给相应属性。

        return map;
    }

    /**
     * 扫描包下的所有类，并输出所有类上面的注解，如果需要使用，可以自行修改或调试
     * 注意，本方法只能扫描我们自己定义的类，而不能扫描jar里的类，如java.lang
     * @param packageName
     */
     static public void scanElements(String packageName ){
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
//            System.out.println("size = "+size);

            List<Class<?>> classList = new ArrayList<Class<?>>();

            for(int i=0;i<size;i++){
                //获取Component.class 点的位置 作用:截断字符串

                int index = classes[i].getName().indexOf(".");

//                System.out.println(classes[i].getName());
//                System.out.println(index);

                //这个包下所有的成员的SimpleName
                String className = classes[i].getName().substring(0, index);

//                System.out.println(className);

                String classNamePath = packageName+"."+className;

//                System.out.println(classNamePath+" ------------->classNamePath");
                Class<?> repoClass;

                //这个包下所有的成员全名
                repoClass = Class.forName(classNamePath);

                try {
                    //如果成员前面的名字为class则将他的SimpleName作为key 将他的实例化作为Object 存入到全局的map中
                    if(repoClass.toString().split(" ")[0].equals("class")) {
                        map.put(className, repoClass.newInstance());
//                        mapName.put(className,)
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//               System.out.println(repoClass);

                Annotation[] annotations = repoClass.getAnnotations();

//                System.out.println("annotations.l ength = "+ annotations.length);
                for(int j =0;j<annotations.length;j++){

//                    System.out.println(repoClass.getName());
//                    System.out.println("Annotation in class  "+repoClass.getName()+ "  is   "+annotations[j].annotationType().getName());
//                    System.out.println(" j = "+j);

                }
//                System.out.println("i = "+ i);
                classList.add(repoClass);
            }

//            System.out.println(classList);
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
    static public boolean findFieldsInClassWithAnnotation(Class targetClazz,Class annotationClazz){

        List<Field> fields=new ArrayList<>();
        for(Field field  : targetClazz.getDeclaredFields())
        {
            field.setAccessible(true);
            System.out.println(field);
            for (Annotation annotation : field.getAnnotations()) {
                System.out.println(annotation);
            }
            if (field.isAnnotationPresent(annotationClazz))
            {
//                fields.add(field);
                return  true;
//                System.out.println("add success");
            }
        }
        System.out.println(fields.size());
        return false;
    }
}
