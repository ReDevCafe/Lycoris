package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class RuntimeTypeAnnotations implements SizedByteCodec {

    private final TypeAnnotation[] typeAnnotations;

    public RuntimeTypeAnnotations(TypeAnnotation[] typeAnnotations) {
        this.typeAnnotations = typeAnnotations;
    }

    public TypeAnnotation[] getTypeAnnotations() { return typeAnnotations; }

    public static RuntimeTypeAnnotations decode(ByteBuffer buffer) {
        TypeAnnotation[] typeAnnotations = new TypeAnnotation[buffer.getChar()];
        for (int i = 0; i < typeAnnotations.length; i++)
            typeAnnotations[i] = TypeAnnotation.decode(buffer);
        return new RuntimeTypeAnnotations(typeAnnotations);
    }

    @Override
    public int getSize() {
        int baseSize = 2;
        for (TypeAnnotation typeAnnotation : typeAnnotations)
            baseSize += typeAnnotation.getSize();
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) typeAnnotations.length);
        for (TypeAnnotation annotation : typeAnnotations)
            annotation.encode(buffer);
    }
}
