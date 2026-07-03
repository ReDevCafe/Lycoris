package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class IntegerInfo extends ConstantInfo {

    private final int bytes;

    public IntegerInfo(final ConstantPool.Tag tag, final int bytes) {
        super(tag);
        this.bytes = bytes;
    }

    public int getValue() { return bytes; }

    public static IntegerInfo decode(ByteBuffer buffer) {
        return new IntegerInfo(ConstantPool.Tag.Integer, buffer.getInt());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putInt(bytes);
    }
}
