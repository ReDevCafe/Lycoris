package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class DynamicInfo extends ConstantInfo {

    private final char bootstrapMethodAttrIndex;
    private final char nameTypeIndex;

    public DynamicInfo(final ConstantPool.Tag tag, final char bootstrapMethodAttrIndex, final char nameTypeIndex) {
        super(tag);
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameTypeIndex = nameTypeIndex;
    }

    public char getBootstrapMethodAttrIndex() { return bootstrapMethodAttrIndex; }
    public char getNameTypeIndex() { return nameTypeIndex; }

    public static DynamicInfo decode(ByteBuffer buffer) {
        return new DynamicInfo(ConstantPool.Tag.Dynamic, buffer.getChar(), buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(bootstrapMethodAttrIndex);
        buffer.putChar(nameTypeIndex);
    }

    @Override
    public int getSize() {
        return super.getSize() + 4;
    }
}
