package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.variables;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.VerificationTypeInfo;

import java.nio.ByteBuffer;

public class UninitializedVariableInfo extends VariableInfo {

    private final char offset;

    public UninitializedVariableInfo(final char offset) {
        super(VerificationTypeInfo.Tag.OBJECT);
        this.offset = offset;
    }

    public char getOffset() { return offset; }

    public static UninitializedVariableInfo decode(ByteBuffer buffer) {
        return new UninitializedVariableInfo(buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(offset);
    }

    @Override
    public int getSize() {
        return super.getSize() + 2;
    }
}
