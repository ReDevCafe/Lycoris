package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets;

import java.nio.ByteBuffer;

public class ThrowsTarget extends EmptyTarget {

    private final char throwsTypeIndex;

    public ThrowsTarget(char throwsTypeIndex) {
        this.throwsTypeIndex = throwsTypeIndex;
    }

    public char getThrowsTypeIndex() { return throwsTypeIndex; }

    public static ThrowsTarget decode(ByteBuffer buffer) {
        return new ThrowsTarget(buffer.getChar());
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(throwsTypeIndex);
    }
}
