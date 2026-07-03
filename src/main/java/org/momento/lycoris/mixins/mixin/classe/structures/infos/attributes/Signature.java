package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class Signature implements SizedByteCodec {

    private final char signatureIndex;

    public Signature(final char signatureIndex) {
        this.signatureIndex = signatureIndex;
    }

    public char getSignatureIndex() { return signatureIndex; }

    public static Signature decode(ByteBuffer buffer) {
        return new Signature(buffer.getChar());
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(signatureIndex);
    }
}
