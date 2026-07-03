package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values;

import java.nio.ByteBuffer;

public class ArrayValue extends ElementValue {

    private final ElementValue[] elements;

    public ArrayValue(final Tag tag, final ElementValue[] elements) {
        super(tag);
        this.elements = elements;
    }

    public ElementValue[] getElements() { return elements; }

    public static ArrayValue decode(final Tag tag, ByteBuffer buffer) {
        ElementValue[] elements = new ElementValue[buffer.getChar()];
        for (int i = 0; i < elements.length; i++)
            elements[i] = ElementValue.decode(buffer);
        return new ArrayValue(tag, elements);
    }

    @Override
    public int getSize() {
        return super.getSize() + 2 + elements.length;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        buffer.putChar((char) elements.length);
        for (ElementValue element : elements)
            element.encode(buffer);
    }
}
