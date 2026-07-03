package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.ByteCodec;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.frames.*;

import java.nio.ByteBuffer;

public class StackMapTable implements SizedByteCodec {

    private final StackMapFrame[] entries;

    public StackMapTable(final StackMapFrame[] entries) {
        this.entries = entries;
    }

    @Override
    public int getSize() {
        int baseSize = 2;
        for (StackMapFrame entry : entries)
            baseSize += entry.getSize();
        return baseSize;
    }

    public StackMapFrame[] getEntries() { return entries; }

    public static StackMapTable decode(ByteBuffer buffer) {
        StackMapFrame[] entries = new StackMapFrame[buffer.getChar()];
        for (int i = 0; i < entries.length; ++i) {
            char frameType = (char) (buffer.get() & 0xFF);
            StackMapFrame.Type type = StackMapFrame.Type.fromValue(frameType);
            entries[i] = switch (type) {
                case SAME -> new SameFrame(type);
                case SAME_LOCAL_1_STACk_ITEM -> SameLocalStackItemFrame.decode(type, buffer);
                case SAME_LOCAL_1_STACK_ITEM_EXTENDED -> SameLocalStackItemExtFrame.decode(type, buffer);
                case CHOP -> ChopFrame.decode(type, buffer);
                case SAME_FRAME_EXTENDED -> SameExFrame.decode(type, buffer);
                case APPEND -> AppendFrame.decode(type, buffer);
                case FULL_FRAME -> FullFrame.decode(type, buffer);
            };
        }
        return new StackMapTable(entries);
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) entries.length);
        for (StackMapFrame entry : entries)
            entry.encode(buffer);
    }
}
