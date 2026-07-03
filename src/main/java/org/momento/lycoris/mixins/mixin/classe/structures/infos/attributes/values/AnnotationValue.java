package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.values;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes.Annotation;

import java.nio.ByteBuffer;

public class AnnotationValue extends ElementValue {

    private final Annotation value;

    public AnnotationValue(final Tag tag, final Annotation value) {
        super(tag);
        this.value = value;
    }

    public Annotation getValue() { return value; }

    public static AnnotationValue decode(final Tag tag, ByteBuffer buffer) {
        return new AnnotationValue(tag, Annotation.decode(buffer));
    }

    @Override
    public int getSize() {
        return super.getSize() + value.getSize();
    }

    @Override
    public void encode(ByteBuffer buffer) {
        super.encode(buffer);
        value.encode(buffer);
    }
}
