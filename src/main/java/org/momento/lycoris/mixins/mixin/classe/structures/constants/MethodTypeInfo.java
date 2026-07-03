package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class MethodTypeInfo extends ConstantInfo {

    private final char descriptorIndex;

    public MethodTypeInfo(final ConstantPool.Tag tag, final char descriptorIndex) {
        super(tag);
        this.descriptorIndex = descriptorIndex;
    }

    public char getDescriptorIndex() { return descriptorIndex; }

    public static MethodTypeInfo decode(ByteBuffer buffer) {
        return new MethodTypeInfo(ConstantPool.Tag.MethodType, buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(descriptorIndex);
    }
}
