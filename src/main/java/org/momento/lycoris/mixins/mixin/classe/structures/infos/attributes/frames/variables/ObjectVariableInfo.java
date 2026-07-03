package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.variables;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.VerificationTypeInfo;

import java.nio.ByteBuffer;

public class ObjectVariableInfo extends VariableInfo {

    private final char cPoolIndex;

    public ObjectVariableInfo(final char cPoolIndex) {
        super(VerificationTypeInfo.Tag.OBJECT);
        this.cPoolIndex = cPoolIndex;
    }

    public char getPoolIndex() { return cPoolIndex; }

    public static ObjectVariableInfo decode(ByteBuffer buffer) {
        return new ObjectVariableInfo(buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(cPoolIndex);
    }

    @Override
    public int getSize() {
        return super.getSize() + 2;
    }
}
