package org.momento.lycoris.mixins.mixin.classe.structures.infos;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MethodInfo implements SizedByteCodec {

    public enum AccessFlag {

        MASKED((char) 0x0),
        PUBLIC((char) 0x1),
        PRIVATE((char) 0x2),
        PROTECTED((char) 0x4),
        STATIC((char) 0x8),
        FINAL((char) 0x10),
        SYNCHRONIZED((char) 0x20),
        BRIDGE((char) 0x40),
        VARARGS((char) 0x80),
        NATIVE((char) 0x100),
        ABSTRACT((char) 0x400),
        STRICT((char) 0x800),
        SYNTHETIC((char) 0x1000);

        private char value;

        AccessFlag(char value) {
            this.value = value;
        }

        public char getValue() { return value; }
        public void setValue(char value) { this.value = value; }

        public static AccessFlag fromValue(final char value) {
            for (final AccessFlag flag : AccessFlag.values()) {
                if (flag.getValue() == value)
                    return flag;
            }
            AccessFlag flag = MASKED;
            MASKED.setValue(value);
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

    private final AccessFlag flags;
    private final char nameIndex;
    private final char descriptorIndex;
    private final AttributeInfo[] attributes;

    public MethodInfo(final AccessFlag flags, final char nameIndex, final char descriptorIndex, final AttributeInfo[] attributes) {
        this.flags = flags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    public AccessFlag getFlags() { return flags; }
    public char getNameIndex() { return nameIndex; }
    public char getDescriptorIndex() { return descriptorIndex; }
    public AttributeInfo[] getAttributes() { return attributes; }

    public static MethodInfo decode(final ConstantPool[] constantPool, ByteBuffer buffer) {
        AccessFlag flag = AccessFlag.fromValue(buffer.getChar());
        char nameIndex = buffer.getChar();
        char descriptorIndex = buffer.getChar();
        AttributeInfo[] attributes = new AttributeInfo[buffer.getChar()];
        for (int i = 0; i < attributes.length; ++i)
            attributes[i] = AttributeInfo.decode(constantPool, buffer);
        return new MethodInfo(flag, nameIndex, descriptorIndex, attributes);
    }

    @Override
    public int getSize() {
        int baseSize = 8;
        for (AttributeInfo attribute : attributes)
            baseSize += attribute.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(flags.getValue());
        buffer.putChar(nameIndex);
        buffer.putChar(descriptorIndex);
        buffer.putChar((char) attributes.length);
        for (AttributeInfo attribute : attributes)
            attribute.encode(buffer);
    }
}
