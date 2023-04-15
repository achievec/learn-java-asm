package achievec;

import com.google.common.collect.Maps;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.Map;

public class FieldAccessAdapter extends AdviceAdapter {
    private final String methodName;

    private String methodDescriptor;

    private String className;

    private Map<String, String> staticFieldSet;

    private Map<String, String> instanceFieldSet;

    public FieldAccessAdapter(int api, MethodVisitor mv, int access, String name, String methodDescriptor, String className) {
        super(api, mv, access, name, methodDescriptor);
        this.className = className;
        this.methodName = name;
        this.methodDescriptor = methodDescriptor;
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        if ((opcode == Opcodes.GETSTATIC || opcode == Opcodes.GETFIELD) && className.equals(owner)) {
            if (opcode == Opcodes.GETSTATIC) {
                if (staticFieldSet == null) {
                    staticFieldSet = Maps.newHashMap();
                }
                staticFieldSet.put(name, descriptor);
            } else {
                if (instanceFieldSet == null) {
                    instanceFieldSet = Maps.newHashMap();
                }
                instanceFieldSet.put(name, descriptor);
            }
            System.out.println("owner:" + owner + " name:" + name + " descriptor:" + descriptor);
        }
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (staticFieldSet != null) {
            System.out.println("staticFieldSet:" + staticFieldSet);
            for (Map.Entry<String, String> entry : staticFieldSet.entrySet()) {
                String fieldName = entry.getKey();
                String fieldDescriptor = entry.getValue();

                visitLdcInsn(className);
                visitLdcInsn(fieldName);
                visitFieldInsn(GETSTATIC, className, fieldName, fieldDescriptor);
                visitLdcInsn(methodName);
                visitLdcInsn(methodDesc);
                visitMethodInsn(INVOKESTATIC, "achievec/Spy", "fieldVisited", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V", false);
            }
        }

        if (instanceFieldSet != null && (methodAccess & Opcodes.ACC_STATIC) == 0) {
            System.out.println("instanceFieldSet:" + instanceFieldSet);
            for (Map.Entry<String, String> entry : instanceFieldSet.entrySet()) {
                String fieldName = entry.getKey();
                String fieldDescriptor = entry.getValue();
                visitLdcInsn(className);
                visitLdcInsn(fieldName);
                visitVarInsn(ALOAD, 0);
                visitFieldInsn(GETFIELD, className, fieldName, fieldDescriptor);
                visitLdcInsn(methodName);
                visitLdcInsn(methodDesc);
                visitMethodInsn(INVOKESTATIC, "achievec/Spy", "fieldVisited", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V", false);
            }
        }
    }

}
