package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class LongInfo extends ConstantInfo {

    private final long bytes;

    public LongInfo(final ConstantPool.Tag tag, final long bytes) {
        super(tag);
        this.bytes = bytes;
    }

    public long getValue() { return bytes; }

    public static LongInfo decode(ByteBuffer buffer) {
        return new LongInfo(ConstantPool.Tag.Integer, buffer.getLong());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putLong(bytes);
    }
}
