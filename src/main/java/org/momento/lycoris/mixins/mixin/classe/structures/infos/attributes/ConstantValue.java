package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class ConstantValue implements SizedByteCodec {

    private final char valueIndex;

    public ConstantValue(final char valueIndex) {
        this.valueIndex = valueIndex;
    }

    @Override
    public int getSize() { return 2; }

    public static ConstantValue decode(final ByteBuffer buffer) {
        return new ConstantValue(buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(valueIndex);
    }
}
