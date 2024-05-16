import java.util.List;

public class User extends Person {
    @MyAnnotation
    public String name;
    private final int age;
    private String email;
    private List<String> comments;
    public static int publicStaticField = 1;
    private static int privateStaticField = 10;

    static {
        System.out.println("UserClass in initialized");
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User() {
        this.age = 18;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void myPublicMethod() {
        System.out.println("This is a public method.");
    }

    private void myPrivateMethod() {
        System.out.println("This is a private method.");
    }

    private void myPrivateMethod(String content, String mark) {
        System.out.println("This is a private method with parameters. " + content + " " + mark);
    }

    public static void myPublicStaticMethod() {
        System.out.println("This is a static public method.");
    }

    private static void myPrivateStaticMethod() {
        System.out.println("This is a static private method.");
    }

    private static void myPrivateStaticMethod(String content) {
        System.out.println("This is a static private method with parameters. " + content);
    }
}
