package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class ModulePackages implements SizedByteCodec {

    private char[] packageIndex;

    public ModulePackages(char[] packageIndex) {
        this.packageIndex = packageIndex;
    }

    public char[] getPackageIndex() { return packageIndex; }

    public static ModulePackages decode(ByteBuffer buffer) {
        char[] packageIndex = new char[buffer.getChar()];
        return new ModulePackages(packageIndex);
    }

    @Override
    public int getSize() {
        return 2 + 2 * packageIndex.length;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) packageIndex.length);
        for (int i = 0; i < packageIndex.length; i++)
            buffer.putChar(packageIndex[i]);
    }
}
