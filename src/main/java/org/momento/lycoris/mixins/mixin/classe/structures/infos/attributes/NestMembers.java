package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class NestMembers implements SizedByteCodec {

    private final char[] classes;

    public NestMembers(char[] classes) {
        this.classes = classes;
    }

    public char[] getClasses() { return classes; }

    @Override
    public int getSize() {
        return 2 + classes.length * 2;
    }

    public static NestMembers decode(ByteBuffer buffer) {
        char[] classes = new char[buffer.getChar()];
        for (int i = 0; i < classes.length; i++)
            classes[i] = buffer.getChar();
        return new NestMembers(classes);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) classes.length);
        for (char classe : classes)
            buffer.putChar(classe);
    }
}
