package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class SuperTypeTarget extends EmptyTarget {

    private final char superTypeIndex;


    public SuperTypeTarget(char superTypeIndex) {
        this.superTypeIndex = superTypeIndex;
    }

    public char getSuperTypeIndex() { return superTypeIndex; }

    public static SuperTypeTarget decode(ByteBuffer buffer) {
        return new SuperTypeTarget(buffer.getChar());
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(superTypeIndex);
    }
}
