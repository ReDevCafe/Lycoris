package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class ClassInfo extends ConstantInfo {

    private final char nameIndex;

    public ClassInfo(final ConstantPool.Tag tag, final char nameIndex) {
        super(tag);
        this.nameIndex = nameIndex;
    }

    public char getNameIndex() { return nameIndex; }

    public static ClassInfo decode(ByteBuffer buffer) {
        return new ClassInfo(ConstantPool.Tag.Class, buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(nameIndex);
    }
}
