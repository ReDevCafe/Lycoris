package org.momento.lycoris.mixins.mixin.classe;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.AttributeInfo;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.FieldInfo;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.MethodInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ClassWrapper implements ByteCodec {

    public enum AccessFlag {

        MASKED((char) 0x0),
        PUBLIC((char) 0x1),
        FINAL((char) 0x10),
        SUPER((char) 0x20),
        INTERFACE((char) 0x200),
        ABSTRACT((char) 0x400),
        SYNTHETIC((char) 0x1000),
        ANNOTATION((char) 0x2000),
        ENUM((char) 0x4000),
        MODULE((char) 0x8000);

        private char value;

        AccessFlag(char value) {
            this.value = value;
        }

        public char getValue() { return value; }
        public char setValue(char value) { this.value = value; return value; }


        public static AccessFlag fromValue(final char value) {
            for (final AccessFlag flag : AccessFlag.values()) {
                if (flag.getValue() == value)
                    return flag;
            }
            AccessFlag flag = MASKED;
            flag.setValue(value);
            return flag;
        }

        public AccessFlag[] getAccessFlags() {
            List<AccessFlag> flags = new ArrayList<>();
            for (AccessFlag flag : AccessFlag.values()) {
                if ((value & flag.getValue()) != 0)
                    flags.add(flag);
            }
            return flags.toArray(new AccessFlag[0]);
        }

        public void addAccessFlag(final AccessFlag flag) {
            this.value = (char) (value | flag.value);
        }

        public void removeAccessFlag(final AccessFlag flag) {
            this.value = (char) (value & ~flag.value);
        }
    }

    private final int magic;
    private final char minorVersion;
    private final char majorVersion;
    private final ConstantPool[] constantPool;
    private final AccessFlag flags;
    private final char thisClass;
    private final char superClass;
    private final char[] interfaces;
    private final FieldInfo[] fields;
    private final MethodInfo[] methods;
    private final AttributeInfo[] attributes;

    public ClassWrapper(final int magic, final char minorVersion, final char majorVersion,
                        final ConstantPool[] constantPool, final AccessFlag flags, final char thisClass,
                        final char superClass, final char[] interfaces, final FieldInfo[] fields,
                        final MethodInfo[] methods, final AttributeInfo[] attributes) {
        this.magic = magic;
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPool = constantPool;
        this.flags = flags;
        this.thisClass = thisClass;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
        this.attributes = attributes;
    }

    public int getMagic() { return magic; }
    public char getMinorVersion() { return minorVersion; }
    public char getMajorVersion() { return majorVersion; }
    public ConstantPool[] getConstantPool() { return constantPool; }
    public AccessFlag getFlags() { return flags; }
    public char getThisClass() { return thisClass; }
    public char getSuperClass() { return superClass; }
    public char[] getInterfaces() { return interfaces; }
    public FieldInfo[] getFields() { return fields; }
    public MethodInfo[] getMethods() { return methods; }
    public AttributeInfo[] getAttributes() { return attributes; }

    public static ClassWrapper decode(ByteBuffer buffer) {
        int magic = buffer.getInt();
        char minorVersion = buffer.getChar();
        char majorVersion = buffer.getChar();
        ConstantPool[] constantPool = new ConstantPool[buffer.getChar() - 1];
        for (int i = 0; i < constantPool.length; ++i)
            constantPool[i] = ConstantPool.decode(buffer);
        AccessFlag flag = AccessFlag.fromValue(buffer.getChar());
        char thisClass = buffer.getChar();
        char superClass = buffer.getChar();
        char[] interfaces = new char[buffer.getChar()];
        for (int i = 0; i < interfaces.length; ++i)
            interfaces[i] = buffer.getChar();
        FieldInfo[] fields = new FieldInfo[buffer.getChar()];
        for (int i = 0; i < fields.length; ++i)
            fields[i] = FieldInfo.decode(constantPool, buffer);
        MethodInfo[] methods = new MethodInfo[buffer.getChar()];
        for (int i = 0; i < methods.length; ++i)
            methods[i] = MethodInfo.decode(constantPool, buffer);
        AttributeInfo[] attributes = new AttributeInfo[buffer.getChar()];
        for (int i = 0; i < attributes.length; ++i)
            attributes[i] = AttributeInfo.decode(constantPool, buffer);
        return new ClassWrapper(magic, minorVersion, majorVersion, constantPool, flag, thisClass, superClass, interfaces, fields, methods, attributes);
    }

    public static ClassWrapper decode(Path path) throws IOException {
        byte[] bytes = Files.readAllBytes(path);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return decode(buffer);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putInt(magic);
        buffer.putChar(minorVersion);
        buffer.putChar(majorVersion);
        buffer.putChar((char) (constantPool.length + 1));
        for (ConstantPool cp : constantPool)
            cp.encode(buffer);
        buffer.putChar(flags.getValue());
        buffer.putChar(thisClass);
        buffer.putChar(superClass);
        buffer.putChar((char) interfaces.length);
        for (char c : interfaces)
            buffer.putChar(c);
        buffer.putChar((char) fields.length);
        for (FieldInfo f : fields)
            f.encode(buffer);
        buffer.putChar((char) methods.length);
        for (MethodInfo m : methods)
            m.encode(buffer);
        buffer.putChar((char) attributes.length);
        for (AttributeInfo a : attributes)
            a.encode(buffer);
    }
}