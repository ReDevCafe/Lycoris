package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.counts;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class OpensCount implements SizedByteCodec {

    private final char opensIndex;
    private final char opensFlags;
    private final char[] opensToIndex;

    public OpensCount(char opensIndex, char opensFlags, char[] opensToIndex) {
        this.opensIndex = opensIndex;
        this.opensFlags = opensFlags;
        this.opensToIndex = opensToIndex;
    }

    public char getIndex() { return opensIndex; }
    public char getExportsFlags() { return opensFlags; }
    public char[] getExportsToIndex() { return opensToIndex; }

    public static OpensCount decode(ByteBuffer buffer) {
        char opensIndex = buffer.getChar();
        char opensFlags = buffer.getChar();
        char[] opensToIndex = new char[buffer.getChar()];
        for (int i = 0; i < opensToIndex.length; i++)
            opensToIndex[i] = buffer.getChar();
        return new OpensCount(opensIndex, opensFlags, opensToIndex);
    }

    @Override
    public int getSize() {
        return 6 + 2 * opensToIndex.length;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(opensIndex);
        buffer.putChar(opensFlags);
        buffer.putChar(opensIndex);
        buffer.putChar((char) opensToIndex.length);
        for (int i = 0; i < opensToIndex.length; i++)
            buffer.putChar(opensToIndex[i]);
    }
}
