package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class InvokeDynamicInfo extends ConstantInfo {

    private final char bootstrapMethodAttrIndex;
    private final char nameTypeIndex;

    public InvokeDynamicInfo(final ConstantPool.Tag tag, final char bootstrapMethodAttrIndex, final char nameTypeIndex) {
        super(tag);
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameTypeIndex = nameTypeIndex;
    }

    public char getBootstrapMethodAttrIndex() { return bootstrapMethodAttrIndex; }
    public char getNameTypeIndex() { return nameTypeIndex; }

    public static InvokeDynamicInfo decode(ByteBuffer buffer) {
        return new InvokeDynamicInfo(ConstantPool.Tag.InvokeDynamic, buffer.getChar(), buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(bootstrapMethodAttrIndex);
        buffer.putChar(nameTypeIndex);
    }
}
