package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class NameTypeInfo extends ConstantInfo {

    private final char nameIndex;
    private final char descriptorIndex;

    public NameTypeInfo(final ConstantPool.Tag tag, final char nameIndex, final char descriptorIndex) {
        super(tag);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public char getNameIndex() { return nameIndex; }
    public char getDescriptorIndex() { return descriptorIndex; }

    public static NameTypeInfo decode(ByteBuffer buffer) {
        return new NameTypeInfo(ConstantPool.Tag.NameAndType, buffer.getChar(), buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(nameIndex);
        buffer.putChar(descriptorIndex);
    }
}
