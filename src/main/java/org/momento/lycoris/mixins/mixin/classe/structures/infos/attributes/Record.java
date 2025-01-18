package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;

import java.nio.ByteBuffer;

public class Record implements SizedByteCodec {

    private RecordComponentInfo[] components;

    public Record(RecordComponentInfo[] components) {
        this.components = components;
    }

    public RecordComponentInfo[] getComponents() {
        return components;
    }

    public static Record decode(ConstantPool[] constantPools, ByteBuffer buffer) {
        RecordComponentInfo[] components = new RecordComponentInfo[buffer.getChar()];
        for (int i = 0; i < components.length; i++)
            components[i] = RecordComponentInfo.decode(constantPools, buffer);
        return new Record(components);
    }

    @Override
    public int getSize() {
        int baseSize = 2;
        for (RecordComponentInfo component : components)
            baseSize += component.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) components.length);
        for (RecordComponentInfo component : components)
            component.encode(buffer);
    }
}
