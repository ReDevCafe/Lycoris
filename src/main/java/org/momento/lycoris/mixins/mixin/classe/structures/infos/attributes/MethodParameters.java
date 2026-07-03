package org.momento.lycoris.mixins.mixin.classe.structures.infos.attributes;

import org.momento.lycoris.mixins.mixin.classe.structures.infos.AttributeInfo;
import org.momento.lycoris.utils.Pair;

import java.nio.ByteBuffer;

public class MethodParameters implements SizedByteCodec {

    private final Pair<Character, Character>[] parameters;

    public MethodParameters(Pair<Character, Character>[] parameters) {
        this.parameters = parameters;
    }

    public Pair<Character, Character>[] getParameters() { return parameters; }

    public static MethodParameters decode(ByteBuffer buffer) {
        Pair<Character, Character>[] parameters = new Pair[buffer.get()];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = new Pair<>(buffer.getChar(), buffer.getChar());
        }
        return new MethodParameters(parameters);
    }

    @Override
    public int getSize() {
        return 1 + parameters.length * 4;
    }

    @Override
    public void encode(ByteBuffer buffer) {
        buffer.put((byte) parameters.length);
        for (Pair<Character, Character> parameter : parameters) {
            buffer.putChar(parameter.getFirst());
            buffer.putChar(parameter.getSecond());
        }
    }
}
