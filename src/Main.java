import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Container container = new Container();
        container.init();
        String className = "Order";
        String fieldName = "customer";
        Class<?> clazz = Class.forName(className);
        Object obj = container.createNewInstance(clazz);
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Object filedValue = field.get(obj);
        Method[] methods = filedValue.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getDeclaredAnnotation(Printable.class) != null) {
                method.invoke(filedValue);
            }
        }

    }
}
