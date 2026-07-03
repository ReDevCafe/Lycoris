package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames;

import java.nio.ByteBuffer;

public class SameLocalStackItemExtFrame extends SameLocalStackItemFrame {

    private final char offsetDelta;

    public SameLocalStackItemExtFrame(final Type type, final char offsetDelta, final VerificationTypeInfo verificationTypeInfo) {
        super(type, verificationTypeInfo);
        this.offsetDelta = offsetDelta;
    }

    public char getOffsetDelta() { return offsetDelta; }

    @Override
    public int getSize() {
        return super.getSize() + 2;
    }

    public static SameLocalStackItemExtFrame decode(final Type type, ByteBuffer buffer) {
        return new SameLocalStackItemExtFrame(type, buffer.getChar(), VerificationTypeInfo.decode(buffer));
    }

    @Override
    public void encode(ByteBuffer buffer) {
        encodePrimitive(buffer);
        buffer.putChar(offsetDelta);
        verificationTypeInfo.encode(buffer);
    }
}
