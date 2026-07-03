package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.Mixin;

import java.nio.ByteBuffer;

public class PermittedSubclasses implements SizedByteCodec {

    private char[] classes;

    public PermittedSubclasses(char[] classes) {
        this.classes = classes;
    }

    public char[] getClasses() { return classes; }

    public static PermittedSubclasses decode(ByteBuffer buffer) {
        char[] classes = new char[buffer.getChar()];
        for (int i = 0; i < classes.length; i++)
            classes[i] = buffer.getChar();
        return new PermittedSubclasses(classes);
    }

    @Override
    public int getSize() {
        return 2 + 2 * classes.length;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) classes.length);
        for (int i = 0; i < classes.length; i++)
            buffer.putChar(classes[i]);
    }
}
