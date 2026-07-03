package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values;

import java.nio.ByteBuffer;

public class EnumConstValue extends ElementValue {

    private final char typeNameIndex;
    private final char constNameIndex;

    public EnumConstValue(final Tag tag, final char typeNameIndex, final char constNameIndex) {
        super(tag);
        this.typeNameIndex = typeNameIndex;
        this.constNameIndex = constNameIndex;
    }

    public char getTypeNameIndex() { return typeNameIndex; }
    public char getConstNameIndex() { return constNameIndex; }

    public static EnumConstValue decode(final Tag tag, ByteBuffer buffer) {
        return new EnumConstValue(tag, buffer.getChar(), buffer.getChar());
    }

    @Override
    public int getSize() {
        return super.getSize() + 4;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(typeNameIndex);
        buffer.putChar(constNameIndex);
    }
}
