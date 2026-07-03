package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames;

import java.nio.ByteBuffer;

public class SameExFrame extends SameFrame {

    private final char offsetDelta;

    public SameExFrame(final Type type, final char offsetDelta) {
        super(type);
        this.offsetDelta = offsetDelta;
    }

    public char getOffsetDelta() { return offsetDelta; }

    public static SameExFrame decode(final Type type, ByteBuffer buffer) {
        return new SameExFrame(type, buffer.getChar());
    }

    @Override
    public int getSize() {
        return super.getSize() + 2;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(offsetDelta);
    }
}
