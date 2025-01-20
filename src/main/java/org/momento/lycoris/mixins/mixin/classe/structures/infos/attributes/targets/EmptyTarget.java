package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.targets;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class EmptyTarget implements SizedByteCodec {

    @Override
    public int getSize() {
        return 0;
    }

    public static EmptyTarget decode(ByteBuffer buffer) {
        return new EmptyTarget();
    }

    @Override
    public void encode(ByteBuffer buffer) {}
}
