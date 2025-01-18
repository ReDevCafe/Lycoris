package org.momento.lycoris.mixins.mixin.classe.structures.constants;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class PackageInfo extends ConstantInfo {

    private char nameIndex;

    public PackageInfo(ConstantPool.Tag tag, char nameIndex) {
        super(tag);
    }

    public char getNameIndex() { return nameIndex; }

    public static PackageInfo decode(ByteBuffer buffer) {
        return new PackageInfo(ConstantPool.Tag.Package, buffer.getChar());
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar(nameIndex);
    }
}
