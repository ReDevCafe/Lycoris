package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values.ElementValue;

import java.nio.ByteBuffer;

public class AnnotationDefault implements SizedByteCodec {

    private final ElementValue value;

    public AnnotationDefault(final ElementValue value) {
        this.value = value;
    }

    public ElementValue getValue() { return value; }

    public static AnnotationDefault decode(ByteBuffer buffer) {
        return new AnnotationDefault(ElementValue.decode(buffer));
    }

    @Override
    public int getSize() {
        return value.getSize();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        value.encode(buffer);
    }
}
