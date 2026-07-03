package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class SourceFile implements SizedByteCodec {

    private final char sourceFileIndex;

    public SourceFile(final char sourceFileIndex) {
        this.sourceFileIndex = sourceFileIndex;
    }

    public char getSourceFileIndex() { return sourceFileIndex; }

    public static SourceFile decode(ByteBuffer buffer) {
        return new SourceFile(buffer.getChar());
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(sourceFileIndex);
    }
}
