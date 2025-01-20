package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class TypeParameterTarget extends EmptyTarget {

    private final byte typeParameterIndex;


    public TypeParameterTarget(byte typeParameterIndex) {
        this.typeParameterIndex = typeParameterIndex;
    }

    public byte getTypeParameterIndex() { return typeParameterIndex; }

    public static TypeParameterTarget decode(ByteBuffer buffer) {
        return new TypeParameterTarget(buffer.get());
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(typeParameterIndex);
    }
}
