package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class StringInfo extends ConstantInfo {

    private final char stringIndex;

    public StringInfo(final ConstantPool.Tag tag, final char stringIndex) {
        super(tag);
        this.stringIndex = stringIndex;
    }

    public char getStringIndex() { return stringIndex; }

    public static StringInfo decode(ByteBuffer buffer) {
        return new StringInfo(ConstantPool.Tag.String, buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(stringIndex);
    }
}
