package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values;

import java.nio.ByteBuffer;

public class ClassConstValue extends ElementValue {

    private final char infoIndex;

    public ClassConstValue(final Tag tag, final char infoIndex) {
        super(tag);
        this.infoIndex = infoIndex;
    }

    public char getInfoIndex() { return infoIndex; }

    public static ClassConstValue decode(final Tag tag, ByteBuffer buffer) {
        return new ClassConstValue(tag, buffer.getChar());
    }

    @Override
    public int getSize() {
        return super.getSize() + 2;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(infoIndex);
    }
}
