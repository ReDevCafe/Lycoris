package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class RefInfo extends ConstantInfo {

    private final char classeIndex;
    private final char nameTypeIndex;

    public RefInfo(final ConstantPool.Tag tag, final char classeIndex, final char nameTypeIndex) {
        super(tag);
        this.classeIndex = classeIndex;
        this.nameTypeIndex = nameTypeIndex;
    }

    public char getClasseIndex() { return classeIndex; }
    public char getNameTypeIndex() { return nameTypeIndex; }

    public static RefInfo decode(final ConstantPool.Tag tag, final ByteBuffer buffer) {
        return new RefInfo(tag, buffer.getChar(), buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(classeIndex);
        buffer.putChar(nameTypeIndex);
    }
}
