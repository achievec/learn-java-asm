package achievec;

public class Spy {

    private static SpyHandle spyHandle;

    public static void fieldVisited(String className,
                                    String fieldName,
                                    Object value,
                                    String methodName,
                                    String methodSignature) {
        if (spyHandle == null) {
            System.out.println("in spy className:" + className
                    + " fieldName:" + fieldName
                    + " value:" + value
                    + " methodName:" + methodName
                    + " methodSignature:" + methodSignature);
            return;
        }
        spyHandle.fieldVisited(className, fieldName, value, methodName, methodSignature);
    }

}
