package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.counts;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public class RequiresCount implements SizedByteCodec {

    private final char requiresIndex;
    private final char requiresFlags;
    private final char requiresVersionIndex;

    public RequiresCount(char requiresIndex, char requiresFlags, char requiresVersionIndex) {
        this.requiresIndex = requiresIndex;
        this.requiresFlags = requiresFlags;
        this.requiresVersionIndex = requiresVersionIndex;
    }

    public char getRequiresIndex() { return requiresIndex; }
    public char getRequiresFlags() { return requiresFlags; }
    public char getRequiresVersionIndex() { return requiresVersionIndex; }

    @Override
    public int getSize() {
        return 6;
    }

    public static RequiresCount decode(ByteBuffer buffer) {
        return new RequiresCount(buffer.getChar(), buffer.getChar(), buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(requiresIndex);
        buffer.putChar(requiresFlags);
        buffer.putChar(requiresVersionIndex);
    }
}
