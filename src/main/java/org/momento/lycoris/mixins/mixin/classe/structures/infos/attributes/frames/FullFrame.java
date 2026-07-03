package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames;

import java.nio.ByteBuffer;

public class FullFrame extends StackMapFrame {

    private final char offsetDelta;
    private final VerificationTypeInfo[] locals;
    private final VerificationTypeInfo[] stack;


    public FullFrame(final Type type, final char offsetDelta, final VerificationTypeInfo[] locals, final VerificationTypeInfo[] stack) {
        super(type);
        this.offsetDelta = offsetDelta;
        this.locals = locals;
        this.stack = stack;
    }

    public char getOffsetDelta() { return offsetDelta; }
    public VerificationTypeInfo[] getLocals() { return locals; }
    public VerificationTypeInfo[] getStack() { return stack; }

    public static FullFrame decode(Type type, ByteBuffer buffer) {
        char offsetDelta = buffer.getChar();
        VerificationTypeInfo[] locals = new VerificationTypeInfo[buffer.getChar()];
        for (int i = 0; i < locals.length; ++i)
            locals[i] = VerificationTypeInfo.decode(buffer);
        VerificationTypeInfo[] stack = new VerificationTypeInfo[buffer.getChar()];
        for (int i = 0; i < stack.length; ++i)
            stack[i] = VerificationTypeInfo.decode(buffer);
        return new FullFrame(type, offsetDelta, locals, stack);
    }

    @Override
    public int getSize() {
        int baseSize = super.getSize() + 6;
        for (VerificationTypeInfo local: locals)
            baseSize += local.getSize();
        for (VerificationTypeInfo stack: stack)
            baseSize += stack.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(offsetDelta);
        buffer.putChar((char) locals.length);
        for (VerificationTypeInfo local: locals)
            local.encode(buffer);
        buffer.putChar((char) stack.length);
        for (VerificationTypeInfo stack: stack)
            stack.encode(buffer);

    }
}
