package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.counts;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class ProvidesCount implements SizedByteCodec {

    private char providesIndex;
    private char[] providesWithIndex;

    public ProvidesCount(char providesIndex, char[] providesWithIndex) {
        this.providesIndex = providesIndex;
        this.providesWithIndex = providesWithIndex;
    }

    public char getProvidesIndex() { return providesIndex; }
    public char[] getProvidesWithIndex() { return providesWithIndex; }

    public static ProvidesCount decode(ByteBuffer buffer) {
        char providesIndex = buffer.getChar();
        char[] providesWithIndex = new char[buffer.getChar()];
        for (int i = 0; i < providesWithIndex.length; i++)
            providesWithIndex[i] = buffer.getChar();
        return new ProvidesCount(providesIndex, providesWithIndex);
    }

    @Override
    public int getSize() {
        return 4 + 2 * providesWithIndex.length;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(providesIndex);
        buffer.putChar((char) providesWithIndex.length);
        for (int i = 0; i < providesWithIndex.length; i++)
            buffer.putChar(providesWithIndex[i]);
    }
}
