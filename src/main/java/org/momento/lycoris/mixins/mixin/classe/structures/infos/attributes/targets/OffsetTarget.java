package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class OffsetTarget extends EmptyTarget {

    private final char offset;

    public OffsetTarget(char offset) {
        this.offset = offset;
    }

    public char getOffset() { return offset; }

    public static OffsetTarget decode(ByteBuffer buffer) {
        return new OffsetTarget(buffer.getChar());
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(offset);
    }
}
