package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.counts;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class ExportsCount implements SizedByteCodec {

    private final char exportsIndex;
    private final char exportsFlags;
    private final char[] exportsToIndex;

    public ExportsCount(char exportsIndex, char exportsFlags, char[] exportsToIndex) {
        this.exportsIndex = exportsIndex;
        this.exportsFlags = exportsFlags;
        this.exportsToIndex = exportsToIndex;
    }

    public char getExportsIndex() { return exportsIndex; }
    public char getExportsFlags() { return exportsFlags; }
    public char[] getExportsToIndex() { return exportsToIndex; }

    public static ExportsCount decode(ByteBuffer buffer) {
        char exportsIndex = buffer.getChar();
        char exportsFlags = buffer.getChar();
        char[] exportsToIndex = new char[buffer.getChar()];
        for (int i = 0; i < exportsToIndex.length; i++)
            exportsToIndex[i] = buffer.getChar();
        return new ExportsCount(exportsIndex, exportsFlags, exportsToIndex);
    }

    @Override
    public int getSize() {
        return 6 + 2 * exportsToIndex.length;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(exportsIndex);
        buffer.putChar(exportsFlags);
        buffer.putChar(exportsIndex);
        buffer.putChar((char) exportsToIndex.length);
        for (int i = 0; i < exportsToIndex.length; i++)
            buffer.putChar(exportsToIndex[i]);
    }
}
