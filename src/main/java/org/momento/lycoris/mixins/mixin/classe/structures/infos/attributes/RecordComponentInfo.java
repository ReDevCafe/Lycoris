package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.structures.ConstantPool;
import org.momento.lycoris.mixins.mixin.classe.structures.infos.AttributeInfo;

import java.nio.ByteBuffer;

public class RecordComponentInfo  implements SizedByteCodec {

    private char nameIndex;
    private char descriptorIndex;
    private AttributeInfo[] attributes;

    public RecordComponentInfo(char nameIndex, char descriptorIndex, AttributeInfo[] attributes) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    public char getNameIndex() { return nameIndex; }
    public char getDescriptorIndex() { return descriptorIndex; }
    public AttributeInfo[] getAttributes() { return attributes; }

    public static RecordComponentInfo decode(final ConstantPool[] constantPools, ByteBuffer buffer) {
        char nameIndex = buffer.getChar();
        char descriptorIndex = buffer.getChar();
        AttributeInfo[] attributes = new AttributeInfo[buffer.getChar()];
        for (int i = 0; i < attributes.length; i++)
            attributes[i] = AttributeInfo.decode(constantPools, buffer);
        return new RecordComponentInfo(nameIndex, descriptorIndex, attributes);
    }

    @Override
    public int getSize() {
        int baseSize = 6;
        for (AttributeInfo attribute : attributes)
            baseSize += attribute.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar(nameIndex);
        buffer.putChar(descriptorIndex);
        for (AttributeInfo attribute : attributes)
            attribute.encode(buffer);
    }
}
