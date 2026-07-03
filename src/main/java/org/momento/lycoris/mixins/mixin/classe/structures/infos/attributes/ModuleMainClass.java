package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class ModuleMainClass implements SizedByteCodec {

    private char mainClassIndex;

    public ModuleMainClass(char mainClassIndex) {
        this.mainClassIndex = mainClassIndex;
    }

    private char getMainClassIndex() {
        return mainClassIndex;
    }

    public static ModuleMainClass decode(ByteBuffer buffer) {
        return new ModuleMainClass(buffer.getChar());
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(mainClassIndex);
    }
}
