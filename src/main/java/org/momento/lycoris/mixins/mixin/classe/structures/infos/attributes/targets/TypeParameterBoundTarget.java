package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.lang.annotation.Target;
import java.nio.ByteBuffer;

public class TypeParameterBoundTarget extends EmptyTarget {

    private final byte typeParameterIndex;
    private final byte boundIndex;


    public TypeParameterBoundTarget(byte typeParameterIndex, byte boundIndex) {
        this.typeParameterIndex = typeParameterIndex;
        this.boundIndex = boundIndex;
    }

    public byte getTypeParameterIndex() { return typeParameterIndex; }
    public byte getBoundIndex() { return boundIndex; }

    public static TypeParameterBoundTarget decode(ByteBuffer buffer) {
        return new TypeParameterBoundTarget(buffer.get(), buffer.get());
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(typeParameterIndex);
        buffer.put(boundIndex);
    }
}
