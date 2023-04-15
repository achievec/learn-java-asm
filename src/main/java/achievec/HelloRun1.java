package achievec;

public class HelloRun1 {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("achievec.Hello");
        Object instance = clazz.newInstance();
        System.out.println(instance);
    }


}
