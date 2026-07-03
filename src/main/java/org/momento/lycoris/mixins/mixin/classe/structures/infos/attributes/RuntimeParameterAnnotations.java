package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import java.nio.ByteBuffer;

public class RuntimeParameterAnnotations implements SizedByteCodec {

    private final Annotation[][] parameters;

    public RuntimeParameterAnnotations(final Annotation[][] parameters) {
        this.parameters = parameters;
    }

    public Annotation[] getParameters() { return parameters[0]; }

    public static RuntimeParameterAnnotations decode(ByteBuffer buffer) {
        final Annotation[][] parameters = new Annotation[buffer.getChar()][];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = new Annotation[buffer.getChar()];
            for (int j = 0; j < parameters[i].length; j++)
                parameters[i][j] = Annotation.decode(buffer);
        }
        return new RuntimeParameterAnnotations(parameters);
    }

    @Override
    public int getSize() {
        int baseSize = 2;
        for (Annotation[] annotations : parameters) {
            baseSize += 2;
            for (Annotation annotation : annotations)
                baseSize += annotation.getSize();
        }
        return baseSize;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.putChar((char) parameters.length);
        for (Annotation[] annotations : parameters) {
            buffer.putChar((char) annotations.length);
            for (Annotation annotation : annotations)
                annotation.encode(buffer);
        }
    }
}
