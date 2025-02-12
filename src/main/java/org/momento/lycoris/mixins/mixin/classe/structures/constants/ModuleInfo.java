package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class ModuleInfo extends ConstantInfo {

    private char nameIndex;

    public ModuleInfo(ConstantPool.Tag tag, char nameIndex) {
        super(tag);
    }

    public char getNameIndex() { return nameIndex; }

    public static ModuleInfo decode(ByteBuffer buffer) {
        return new ModuleInfo(ConstantPool.Tag.Module, buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(nameIndex);
    }

    @Override
    public int getSize() {
        return super.getSize() + 2;
    }
}
