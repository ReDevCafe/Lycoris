package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class RuntimeAnnotations implements SizedByteCodec {

    private final Annotation[] annotations;

    public RuntimeAnnotations(final Annotation[] annotations) {
        this.annotations = annotations;
    }

    public Annotation[] getAnnotations() { return annotations; }

    public static RuntimeAnnotations decode(ByteBuffer buffer) {
        final Annotation[] annotations = new Annotation[buffer.getChar()];
        for (int i = 0; i < annotations.length; i++) {
            annotations[i] = Annotation.decode(buffer);
        }
        return new RuntimeAnnotations(annotations);
    }

    @Override
    public int getSize() {
        int baseSize = 2;
        for (Annotation annotation : annotations)
            baseSize += annotation.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) annotations.length);
        for (Annotation annotation : annotations)
            annotation.encode(buffer);
    }
}
