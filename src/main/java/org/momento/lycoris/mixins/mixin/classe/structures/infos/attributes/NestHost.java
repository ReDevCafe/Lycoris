package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class NestHost implements SizedByteCodec {

    private char hostClassIndex;

    public NestHost(char hostClassIndex) {
        this.hostClassIndex = hostClassIndex;
    }

    public char getHostClassIndex() { return hostClassIndex; }

    public static NestHost decode(ByteBuffer buffer) {
        return new NestHost(buffer.getChar());
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(hostClassIndex);
    }
}
