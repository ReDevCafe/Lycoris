package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class MethodHandleInfo extends ConstantInfo {

    private final byte referenceKind;
    private final char referenceIndex;

    public MethodHandleInfo(final ConstantPool.Tag tag, final byte referenceKind, final char referenceIndex) {
        super(tag);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    public byte getReferenceKind() { return referenceKind; }
    public char getReferenceIndex() { return referenceIndex; }

    public static MethodHandleInfo decode(ByteBuffer buffer) {
        return new MethodHandleInfo(ConstantPool.Tag.MethodType, buffer.get(), buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.put(referenceKind);
        buffer.putChar(referenceIndex);
    }
}
