package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class FormalParameterTarget extends EmptyTarget {

    private byte formalParameterIndex;

    public FormalParameterTarget(byte formalParameterIndex) {
        this.formalParameterIndex = formalParameterIndex;
    }

    public byte getFormalParameterIndex() { return formalParameterIndex; }

    public static FormalParameterTarget decode(ByteBuffer buffer) {
        return new FormalParameterTarget(buffer.get());
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(formalParameterIndex);
    }
}
