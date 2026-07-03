package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames;

import java.nio.ByteBuffer;

public class ChopFrame extends StackMapFrame {

    private final char offsetDelta;

    public ChopFrame(final Type type, final char offsetDelta) {
        super(type);
        this.offsetDelta = offsetDelta;
    }

    public char getOffsetDelta() { return offsetDelta; }

    public static ChopFrame decode(final Type type, ByteBuffer buffer) {
        return new ChopFrame(type, buffer.getChar());
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
