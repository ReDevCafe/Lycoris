package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class DoubleInfo extends ConstantInfo {

    private final double bytes;

    public DoubleInfo(final ConstantPool.Tag tag, final double bytes) {
        super(tag);
        this.bytes = bytes;
    }

    public double getValue() { return bytes; }

    public static DoubleInfo decode(ByteBuffer buffer) {
        return new DoubleInfo(ConstantPool.Tag.Float, buffer.getDouble());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putDouble(bytes);
    }
}
