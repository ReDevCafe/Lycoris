package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class EnclosingMethod implements SizedByteCodec {

    private final char classIndex;
    private final char methodIndex;

    public EnclosingMethod(final char classIndex, final char methodIndex) {
        this.classIndex = classIndex;
        this.methodIndex = methodIndex;
    }

    public static EnclosingMethod decode(ByteBuffer buffer) {
        return new EnclosingMethod(buffer.getChar(), buffer.getChar());
    }

    public char getClassIndex() { return classIndex; }
    public char getMethodIndex() { return methodIndex; }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(classIndex);
        buffer.putChar(methodIndex);
    }
}
