package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class FloatInfo extends ConstantInfo {

    private final float bytes;

    public FloatInfo(final ConstantPool.Tag tag, final float bytes) {
        super(tag);
        this.bytes = bytes;
    }

    public float getValue() { return bytes; }

    public static FloatInfo decode(ByteBuffer buffer) {
        return new FloatInfo(ConstantPool.Tag.Float, buffer.getFloat());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putFloat(bytes);
    }
}
