package achievec;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class FieldAccessAwareVisitor extends ClassVisitor {

    String className;

    public FieldAccessAwareVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }


    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (mv != null) {
            mv = new FieldAccessAdapter(api, mv, access, name, descriptor, className);
        }
        return mv;
    }

}