package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.SizedByteCodec;

import java.nio.ByteBuffer;

public abstract class StackMapFrame implements SizedByteCodec {

    public enum Type {
        SAME((char) 0),
        SAME_LOCAL_1_STACk_ITEM((char) 64),
        SAME_LOCAL_1_STACK_ITEM_EXTENDED((char) 247),
        CHOP((char) 248),
        SAME_FRAME_EXTENDED((char) 251),
        APPEND((char) 252),
        FULL_FRAME((char) 255);

        private char value;

        Type(final char value) {
            this.value = value;
        }

        public char getValue() { return value; }

        public static Type fromValue(final char value) {
            Type type;
            if (value < 64)
                type = SAME;
            else if (value < 128)
                type = SAME_LOCAL_1_STACk_ITEM;
            else if (value < 248)
                type = SAME_LOCAL_1_STACK_ITEM_EXTENDED;
            else if (value < 251)
                type = CHOP;
            else if (value == 251)
                type = SAME_FRAME_EXTENDED;
            else if (value < 255)
                type = APPEND;
            else
                type = FULL_FRAME;
            type.value = value;
            return type;
        }
    }

    private final Type type;

    public StackMapFrame(final Type type) {
        this.type = type;
    }

    public Type getType() { return type; }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put((byte) type.value);
    }
}
