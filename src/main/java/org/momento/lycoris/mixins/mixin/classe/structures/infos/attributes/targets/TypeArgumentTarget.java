package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class TypeArgumentTarget extends EmptyTarget {

    private final char offset;
    private final byte typeArgumentIndex;

    public TypeArgumentTarget(char offset, byte typeArgumentIndex) {
        this.offset = offset;
        this.typeArgumentIndex = typeArgumentIndex;
    }

    public char getOffset() { return offset; }
    public byte getTypeArgumentIndex() { return typeArgumentIndex; }

    public static TypeArgumentTarget decode(ByteBuffer buffer) {
        return new TypeArgumentTarget(buffer.getChar(), buffer.get());
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(offset);
        buffer.put(typeArgumentIndex);
    }
}
