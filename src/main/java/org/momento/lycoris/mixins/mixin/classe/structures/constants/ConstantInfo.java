package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public abstract class ConstantInfo implements ByteCodec {

    private ConstantPool.Tag tag;

    public ConstantInfo(final ConstantPool.Tag tag) {
        this.tag = tag;
    }

    public ConstantPool.Tag getTag() { return tag; }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put(tag.getValue());
    }
}
