package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class CatchTarget extends EmptyTarget {

    private final char exceptionTableIndex;

    public CatchTarget(char exceptionTableIndex) {
        this.exceptionTableIndex = exceptionTableIndex;
    }

    public char getExceptionTableIndex() { return exceptionTableIndex; }

    public static CatchTarget decode(ByteBuffer buffer) {
        return new CatchTarget(buffer.getChar());
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(exceptionTableIndex);
    }
}
