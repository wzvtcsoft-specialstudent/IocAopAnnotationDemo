package test;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.TYPE})
// 如果不加这个注解 isAnnotationPresent() 方法不会认识该注解 但是Document注解只是在Java编译时形成Doc文档
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MySign {
}
