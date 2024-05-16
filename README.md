# 反射

## 获取class对象的方法

1. 类名称加 ".class" ，编译时就确定类型
2. 调用类的实例对象的getClass方法，运行时获取类型，编译阶段无法获取类型
3. 调用Class的forName静态方法，也是运行时获取类型

## 如何操作类

1. 以获取字段为例，**getDeclaredFields**获取类的所有字段，**getFields**获取类及其父类的公共字段，若要获取父类的所有字段可以先调用**getSuperClass**方法，再调用**getDeclaredFields**方法。同理，当获取类的其他信息时，**getDeclared**系列方法都是获取当前类中声明的所有成员，而**get**系列方法获取当前类及其父类的公共成员，同时**getDeclaredField**方法可以通过字符串型参数获取指定字段，然后可以通过**getType**方法获取该字段的类型（如果为泛型则返回泛型擦除后的类型），如果为泛型可以用**getGenericType**方法获取完整类型

   ```java
   public class Main {
       public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
           Class<?> clazz = Class.forName("test.User");
   
           //获取类的所有字段
           Field[] fields = clazz.getDeclaredFields();
           for (Field field : fields) {
               System.out.println(field.getName());
           }
   
           //获取父类的字段
           fields = clazz.getSuperclass().getDeclaredFields();
           for (Field field : fields) {
               System.out.println(field.getName());
           }
   
           //获取泛型字段的完整类型
           Field field = clazz.getDeclaredField("comments");
           System.out.println(field.getGenericType());
       }
   }
   ```

2. 类的操作：查看类的指定字段，先通过**getDeclaredField**方法获取要查看的字段，然后在调用**get**方法，**get**方法参数为类的实例化对象名，若为静态字段参数则为**null**，如果要查看的字段为私有字段，则需要先调用**setAccessible**方法，参数为**true**。修改类的字段，在获取字段后可以调用**set**方法，第一个参数为实例化对象名，第二个参数为修改后的值，同理静态字段则为**null**，如果要执行类的方法，可以先调用**getDeclaredMethod**方法获取要执行的方法，然后调用**method**的**invoke**方法

   ```java
   public class Main {
       public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
           Class<?> clazz = Class.forName("test.User");
   
           //查看类的私有字段
           Field field = clazz.getDeclaredField("privateStaticField");
           field.setAccessible(true);
           System.out.println(field.get(null));
   
           //修改类的字段
           field.set(null, 34);
           System.out.println(field.get(null));
   
           //调用类的方法(无参)
           Method method = clazz.getDeclaredMethod("myPrivateStaticMethod");
           method.setAccessible(true);
           method.invoke(null);
   
           //调用类的方法(有参)
           method = clazz.getDeclaredMethod("myPrivateStaticMethod", String.class);
           method.setAccessible(true);
           method.invoke(null, "Hello, it's me.");
   
       }
   }
   ```

3. 创造类的实例：先在目标类的Class对象中选择一个构造器，在通过其newInstance方法实例化对象，而newInstance实例化对象的对象类型是运行时得到的，因此返回值应该用Object接收，然后用强制类型转换，或者Class类自带的cast方法，但cast方法返回的是泛型，泛型仅在编译期有效，所以只能对使用".class"获取的Class对象有效

   ```java
   package test;
   
   import java.lang.reflect.Constructor;
   import java.lang.reflect.Field;
   import java.lang.reflect.InvocationTargetException;
   import java.lang.reflect.Method;
   
   public class Main {
       public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
           Class<?> clazz = Class.forName("test.User");
   
           
           Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
           Object obj = constructor.newInstance("xtc", 20);
           User user = (User) obj;
           System.out.println(user.name);
   
           //cast方法
   //        Class<User> clazz = User.class;
   //        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
   //        Object obj = constructor.newInstance("xtc", 20);
   //        User user = clazz.cast(obj);
   //        System.out.println(user.name);
       }
   }
   ```

4. 操作实例对象的字段值和方法与之前操作静态字段值和静态方法一致，只用将参数null改成相应的实例对象名

   ```java
   public class Main {
       public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
           Class<?> clazz = Class.forName("test.User");
   		
           //修改和查看字段值
           Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
           Object obj = constructor.newInstance("xtc", 20);
           Field field = clazz.getDeclaredField("age");
           field.setAccessible(true);
           field.set(obj, 33);
           System.out.println(field.get(obj));
           
           //调用私有方法
           Method method = clazz.getDeclaredMethod("myPrivateMethod", String.class, String.class);
           method.setAccessible(true);
           method.invoke(obj, "Hello,", "it's me.");
       }
   }
   ```

   由上述代码运行结果可知，反射可以访问和修改私有字段值，包括**final**字段

## 反射的应用实例
可参考[代码](https://github.com/divine1022/ReflectDemo/tree/master/src)，该代码利用反射实现了依赖注入(DI)
