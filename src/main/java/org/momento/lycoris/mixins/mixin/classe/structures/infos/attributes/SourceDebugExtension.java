package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class SourceDebugExtension implements SizedByteCodec {

    private final byte[] debugExtension;

    public SourceDebugExtension(final byte[] debugExtension) {
        this.debugExtension = debugExtension;
    }

    public byte[] getDebugExtension() { return debugExtension; }

    public static SourceDebugExtension decode(final int length, ByteBuffer buffer) {
        byte[] debugExtension = new byte[length];
        buffer.get(debugExtension);
        return new SourceDebugExtension(debugExtension);
    }

    @Override
    public int getSize() {
        return debugExtension.length;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(debugExtension);
    }
}
