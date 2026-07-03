package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values;

import java.nio.ByteBuffer;

public class ConstValue extends ElementValue {

    private final char valueIndex;

    public ConstValue(final Tag tag, final char valueIndex) {
        super(tag);
        this.valueIndex = valueIndex;
    }

    public char getValueIndex() { return valueIndex; }

    public static ConstValue decode(final Tag tag, ByteBuffer buffer) {
        return new ConstValue(tag, buffer.getChar());
    }

    @Override
    public int getSize() {
        return super.getSize() + 2;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(valueIndex);
    }
}
