package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames;

import java.nio.ByteBuffer;

public class AppendFrame extends StackMapFrame {

    private final char offsetDelta;
    private final VerificationTypeInfo[] locals;

    public AppendFrame(final Type type, final char offsetDelta, final VerificationTypeInfo[] locals) {
        super(type);
        this.offsetDelta = offsetDelta;
        this.locals = locals;
    }

    public char getOffsetDelta() { return offsetDelta; }
    public VerificationTypeInfo[] getLocals() { return locals; }

    public static AppendFrame decode(final Type type, ByteBuffer buffer) {
        char offsetDelta = buffer.getChar();
        VerificationTypeInfo[] locals = new VerificationTypeInfo[type.getValue() - 251];
        for (int i = 0; i < locals.length; ++i)
            locals[i] = VerificationTypeInfo.decode(buffer);
        return new AppendFrame(type, offsetDelta, locals);
    }

    @Override
    public int getSize() {
        int baseSize = super.getSize() + 2;
        for (VerificationTypeInfo local: locals)
            baseSize += local.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(offsetDelta);
        for (VerificationTypeInfo local: locals)
            local.encode(buffer);
    }
}
