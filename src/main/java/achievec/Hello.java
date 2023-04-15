package achievec;

public class Hello {

    private static String staticField = "staticField";

    private String instanceField = "instanceField";

    public static void main(String[] args) {
        staticMain();
    }

    public static void staticMain() {
        System.out.println(staticField);
        Hello hello = new Hello();
        hello.main();
    }

    static {
        staticMain();
    }

    public void main() {
        System.out.println(instanceField);
    }


//    public static void main(String[] args) {
//
//        System.out.println(staticField);
//
//        Hello hello = new Hello();
//        System.out.println(hello.instanceField);
//
//        Spy.fieldVisited("liyuhao/Hello", "staticField", staticField, "main", "([Ljava/lang/String;)V");
//        Spy.fieldVisited("liyuhao/Hello", "instanceField", hello.instanceField, "main", "([Ljava/lang/String;)V");
//
//    }

}
