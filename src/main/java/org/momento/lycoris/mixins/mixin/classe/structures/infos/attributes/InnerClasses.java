package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class InnerClasses implements SizedByteCodec {

    public static class Classes implements SizedByteCodec {

        final char innerClassInfoIndex;
        final char outerClassInfoIndex;
        final char innerNameIndex;
        final AccessFlag innerClassAccessFlags;

        public Classes(final char innerClassInfoIndex, final char outerClassInfoIndex, final char innerNameIndex, final AccessFlag innerClassAccessFlags) {
            this.innerClassInfoIndex = innerClassInfoIndex;
            this.outerClassInfoIndex = outerClassInfoIndex;
            this.innerNameIndex = innerNameIndex;
            this.innerClassAccessFlags = innerClassAccessFlags;
        }

        public static Classes decode(ByteBuffer buffer) {
            return new Classes(buffer.getChar(), buffer.getChar(), buffer.getChar(), AccessFlag.fromValue(buffer.getChar()));
        }


        @Override
        public int getSize() {
            return 8;
        }

        @Override
        public void encode(ByteBuffer buffer) {
            buffer.putChar(innerClassInfoIndex);
            buffer.putChar(outerClassInfoIndex);
            buffer.putChar(innerNameIndex);
            buffer.putChar(innerClassAccessFlags.getValue());
        }
    }

    private final Classes[] classes;

    public InnerClasses(final Classes[] classes) {
        this.classes = classes;
    }

    public static InnerClasses decode(ByteBuffer buffer) {
        Classes[] classes = new Classes[buffer.getChar()];
        for (int i = 0; i < classes.length; i++)
            classes[i] = Classes.decode(buffer);
        return new InnerClasses(classes);
    }

    @Override
    public int getSize() {
        int size = 0;
        for (Classes classe : classes)
            size += classe.getSize();
        return size;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) classes.length);
        for (Classes classe : classes)
            classe.encode(buffer);
    }
}
